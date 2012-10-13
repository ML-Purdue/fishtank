import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Visualizer extends JFrame implements Runnable {
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;
    private Engine fishtank;
    private Thread engineThread;
    private State state;
    private int width = 512, height = 512;
    private FishLocation tmpFishLoc = new FishLocation(new Fish(), new Vector(50, 50));
    private BufferedImage fishImage;

    public Visualizer() {
        //Set up the fishtank
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = (Graphics2D)buffer.getGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        fishtank = new Engine();
        engineThread = new Thread(fishtank);
        try {
			fishImage = ImageIO.read(new File("fish.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
    }

    /* Helper function to print the usage statement */
    private static void usage() {
        System.err.println("Usage: java Visualizer");
    }

    public void run() {
        //Set up visualization window
        this.setVisible(true);
        this.setSize(buffer.getWidth(), buffer.getHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Start the engine
        engineThread.start();

        //Repeatedly display the simulation
        while(engineThread.isAlive()) {
            state = fishtank.getState();
            repaint();
        }
    }

    //Push the visualization of the state to the window
    public void paint(Graphics g) {
    	//Draw the tank
        bufferGraphics.setColor(new Color(134, 177, 225));
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
   
        //Draw the fish

        // TODO draw fish features like rudder
        if (state == null) {
                return;
        }
        for (Fish fish : state.fishList) {
            bufferGraphics.setColor(new Color(255, 100, 0));
            bufferGraphics.fillOval((int)fish.getPosition().x - fish.getRadius(),
                                    (int)fish.getPosition().y - fish.getRadius(),
                                    2 * fish.getRadius(), 2 * fish.getRadius());
        }
           
        // TODO Draw the plants

        g.drawImage(buffer, 0, 0, this);

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

