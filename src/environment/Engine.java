 package environment;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import control.CircleFish;
import control.FishAI;

public class Engine implements Runnable {
	private WorldState backState;
	private WorldState frontState;
	private Object stateLock;
	private ArrayList<FishAI> controllers;
	private Hashtable<FishAI, Thread> aiThreads;
	private ArrayList<Fish> reproducers;
	private Random rng;
	private int roundsUnderQuota = 0;
	private ArrayList<Class<? extends FishAI>> aiTypes;
	private int typeIndex = 0;
	private int spawnRequest = 0;
	private int numFish = 0;
	private int foodCount = 10;

	public Engine() {
		backState = new WorldState(0);
		stateLock = new Object();
		controllers = new ArrayList<FishAI>();
		aiThreads = new Hashtable<FishAI, Thread>();
		rng = new Random();
		reproducers = new ArrayList<Fish>();
		aiTypes = new ArrayList<Class<? extends FishAI>>();
		aiTypes.add(CircleFish.class);
		flipStates();
		generateFood(foodCount);
	}

	public WorldState getState(long ID) {
		WorldState rtn = null;

		//Busy-wait until a new state is available
		while(frontState.seqID <= ID){
			Thread.yield();
		}

		synchronized(stateLock) {
			rtn = frontState;
		}

		return rtn;
	}
	
	public int numFish () {
		return numFish;
	}
	
	public int numControllers() {
		return controllers.size();
	}
	
	public int maxFish() {
		int max = 0;
		for (FishAI ai : controllers) if (ai.myFish.size() > max)	max = ai.myFish.size();
		return max;
	}
	
	public double maxNutrients() {
		double max = 0;
		for (FishAI ai : controllers) {
			double nutSum = 0;
			for (Fish f : ai.myFish) {
				FishState fs = frontState.getState(f);
				if (fs.isAlive()) {
					nutSum += fs.getNutrients();
				}
			}
			if (nutSum > max) max = nutSum;
		}
		return max;
	}

	private void flipStates() {
		synchronized(stateLock) {
			frontState = backState;
		}
	}

	private void moveFish() {
		for (FishAI ai : controllers) {
			for (Fish f : ai.myFish) {
				synchronized (f.requested_state) {
					FishState old_fs = frontState.getState(f);
					FishState requested_fs = f.requested_state;
					Vector pos = old_fs.getPosition();
					Vector dir = requested_fs.getRudderVector();

					if (old_fs == null || requested_fs == null) {
						System.err.println("Oh noes!");
					}

					double speed = requested_fs.getSpeed();
					double x = pos.x + speed * dir.x;
					double y = pos.y + speed * dir.y;

					// TODO: better collision handling
					if (x > Rules.tankWidth || x < 0) {
						x = pos.x;
					}
					if (y > Rules.tankHeight || y < 0) {
						y = pos.y;
					}

					FishState new_fs = old_fs.clone();
					new_fs.position = new Vector(x, y);
					new_fs.heading = dir;
					new_fs.speed = speed;
					//System.out.println("Moving fish " + f.id + " to " + new_fs.getPosition() +
					//", old pos was " + pos + ", speed was " + speed + ", dir was " + dir);
					backState.fishStates.put(f, new_fs);
				}
			}
		}
	}
	
	private void generateFood(int n) {
		for (int i = 0; i < n; i++) {
			backState.food.add(new Food(new Vector(rng.nextDouble() * Rules.tankWidth, rng.nextDouble() * Rules.tankHeight), 100));
		}
	}
	
	private void eatFood() {
		for (FishState fishState : backState.fishStates.values()) {
			for (Food food : backState.food) {
				double dist = Math.sqrt(Math.pow((fishState.position.x - food.position.x), 2)
						+ Math.pow((fishState.position.y - food.position.y), 2));
				if(dist < fishState.getRadius() + food.getRadius()){
					fishState.nutrients += (food.nutrients * 0.8);
					food.position = new Vector(rng.nextDouble() * Rules.tankWidth, rng.nextDouble() * Rules.tankHeight);
				}
			}
		}
	}

	private void collideFish() {
		for (FishState f1 : backState.fishStates.values()) {
			for (FishState f2 : backState.fishStates.values()) {
				if(f1 == f2 || !f1.alive || !f2.alive) continue;

				double dist = Math.sqrt(Math.pow((f1.position.x - f2.position.x), 2)
						+ Math.pow((f1.position.y - f2.position.y), 2));
				if(dist < f1.getRadius() + f2.getRadius()){
					if(f1.nutrients > f2.nutrients){
						f2.alive = false;
						f1.nutrients += (f2.nutrients * 0.8);
					}else if(f2.nutrients > f1.nutrients){
						f1.alive = false;
						f2.nutrients += (f1.nutrients * 0.8);
					}else{
						if(Math.random() > 0.5){
							f2.alive = false;
							f1.nutrients += (f2.nutrients * 0.8);
						}else{
							f1.alive = false;
							f2.nutrients += (f1.nutrients * 0.8);
						}
					}
				}

			}
		}
	}

    private void decayFish() {
    	for (FishState fs : backState.fishStates.values()) {
			// -1 per fish per round
    		// In the future, we might want to make this dependent on fish size
    		fs.nutrients -= Rules.decay(fs);

    		if (fs.nutrients <= 0) {
    			fs.alive = false;
    		}
    	}
    	
    	// TODO: remove dead FishAIs.
    }
    
    protected void requestRepro (Fish f) {
    	synchronized(reproducers) {
    		reproducers.add(f);
    	}
    }

    private void spawnFish() {
        // Handle reproduction requests
    	synchronized(reproducers){
            for (Fish parent : reproducers) {
				System.out.println("Reproducing fish " + parent.getID());
            	FishState ps = backState.getState(parent);
            	if (!ps.isAlive()) {
            		continue;
            	}
            	backState.getState(parent).nutrients *= .4;		// Each baby fish gets 40% of the original food
            	
            	// Child fish should spawn behind the parent fish,
            	// facing the opposite direction (to prevent accidental eating)
            	FishState cs = ps.clone();
            	cs.position = ps.position.plus(ps.heading.times(-2 * ps.getRadius()));
            	cs.heading = ps.heading.times(-1);
            	
            	Fish child = new Fish(parent.controller);
            	child.requested_state = cs.clone();
                
                parent.controller.myFish.add(child);
                backState.fishStates.put(child, cs);
            }
            reproducers.clear();
        }
        
        // Decide whether we need to spawn a new AI
    	int numFish = 0;
    	for (FishAI ai : controllers) {
    		numFish += ai.myFish.size();
    	}
    	if (numFish < Rules.minFish) {
    		roundsUnderQuota++;
    	} else {
    		roundsUnderQuota = 0;
    	}
    	this.numFish = numFish;
    	
    	synchronized (this) {  // Make sure span request isn't set while spawning 
    		if (roundsUnderQuota > 5 || spawnRequest > 0) {
    			if (spawnRequest > 0) spawnRequest--;
    			
    			// Create a new FishAI
    			try {
    				// Figure out which controller type is "on deck"
    				FishAI ai = aiTypes.get(typeIndex).getConstructor(Engine.class).newInstance(this);
    				typeIndex++;
    				typeIndex %= aiTypes.size();
    				
    				ai.color = new Color(rng.nextInt(156) + 100, rng.nextInt(156) + 100, rng.nextInt(156) + 100);

    				// Create all the fish, add to the controller and to the backstate
    				double nutrients = Rules.startingNutrients / ai.startFish;
    				for (int i = 0; i < ai.startFish; i++) {
    					Fish f = new Fish(ai);
    					FishState fs = new FishState();
    					fs.nutrients = nutrients;
    					fs.heading = new Vector(rng.nextDouble(), rng.nextDouble()).normalize();
    					fs.position = new Vector(rng.nextInt(Rules.tankWidth - 150)+75,
    							rng.nextInt(Rules.tankHeight - 150) + 75);
    					f.requested_state = fs.clone();
    					ai.myFish.add(f);
    					backState.fishStates.put(f, fs);
    				}

    				// Start the AI
    				controllers.add(ai);
    				Thread ai_thread = new Thread(ai);
    				aiThreads.put(ai, ai_thread);
    				ai_thread.start();

    			} catch (IllegalArgumentException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (SecurityException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InstantiationException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IllegalAccessException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InvocationTargetException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (NoSuchMethodException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    }

    /* Temporary function - will need to remove later */
    public synchronized void add () {
    	spawnRequest++;
    }

    public void run() {
        long iter_time = 0;
        long min_iter_time = 100000000L; //100ms

        long numStates = 0;

        //Add an initial fish
        add();

        while(true) {
            iter_time = System.nanoTime();

        	System.out.println("iteration - state id is " + numStates);
			backState = new WorldState(numStates++);
			backState.food = frontState.food;

			//Calculate the next state from frontState into the backState
			moveFish();
			
			eatFood();

            collideFish();

            decayFish();

			spawnFish();

			//Push backState to be the new frontState
			flipStates();

            iter_time = System.nanoTime() - iter_time;
            if(iter_time < min_iter_time){
                try {
                    Thread.sleep((min_iter_time - iter_time) / 1000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}

