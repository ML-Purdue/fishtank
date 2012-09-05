import javax.swing.JFrame;
import java.util.Scanner;

public class Visualizer implements Runnable {
    private JFrame jFrame;
    private Engine fishtank;
    private Thread engineThread;

    public Visualizer() {
        //Set up the fishtank
        jFrame = new JFrame("Fish Tank");
        fishtank = new Engine();
        engineThread = new Thread(fishtank);
    }

    /* Helper function to print the usage statement */
    private static void usage() {
        System.err.println("Usage: java Visualizer");
    }

    public void run() {
        //Set up visualization window
        jFrame.setVisible(true);
        jFrame.setSize(512, 512);

        //Start the engine
        engineThread.start();

        //Repeatedly display the simulation
        while(engineThread.isAlive()) {
            draw(fishtank.getState());
        }
    }

    //Push the visualization of the state to the window
    private void draw(State s) {
        //Draw the tank

        //Draw the plants

        //Draw the fish
    }

    public static void main(String[] argv) {
        //Parse command-line args
        if(argv.length != 0){
            usage();
            System.exit(-1);
        }

        Thread visThread = new Thread(new Visualizer());
        visThread.start();

        //Poll for the user to end the simulation
        Scanner in = new Scanner(System.in);
        while(visThread.isAlive()) {
            String input = in.nextLine();
            if(input.equals("exit")) {
                System.exit(0);
            }
        }
    }
}

