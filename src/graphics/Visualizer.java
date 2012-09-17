package graphics;
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

import environment.Engine;
import environment.Fish;
import environment.FishLocation;
import environment.State;
import environment.Vector;

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
        tmpFishLoc.position.x += Math.cos(tmpFishLoc.fish.getRudderDirection()) * 0.1;
        tmpFishLoc.position.x %= buffer.getWidth();
        tmpFishLoc.position.y += Math.sin(tmpFishLoc.fish.getRudderDirection()) * 0.1;
        tmpFishLoc.position.y %= buffer.getHeight();
        if (tmpFishLoc.position.x < 0) tmpFishLoc.position.x += buffer.getWidth();
        if (tmpFishLoc.position.y < 0) tmpFishLoc.position.y += buffer.getHeight();
        tmpFishLoc.fish.setRudderDirection(tmpFishLoc.fish.getRudderDirection() + 0.001);
        bufferGraphics.setColor(new Color(255, 255, 0));
        //bufferGraphics.fillOval((int)tmpFishLoc.position.x, (int)tmpFishLoc.position.y, tmpFishLoc.fish.radius * 2, tmpFishLoc.fish.radius * 2);
        bufferGraphics.setColor(new Color(255, 0, 0));
        int centerX = (int)tmpFishLoc.position.x + tmpFishLoc.fish.radius;
        int centerY = (int)tmpFishLoc.position.y + tmpFishLoc.fish.radius;
        int rudderX = (int)(centerX + -2 * tmpFishLoc.fish.radius * Math.cos(tmpFishLoc.fish.getRudderDirection()));
        int rudderY = (int)(centerY + -2 * tmpFishLoc.fish.radius * Math.sin(tmpFishLoc.fish.getRudderDirection()));
        //bufferGraphics.drawLine(centerX, centerY, rudderX, rudderY);
        bufferGraphics.rotate(tmpFishLoc.fish.getRudderDirection(), centerX, centerY);
        bufferGraphics.drawImage(fishImage, (int)tmpFishLoc.position.x, (int)tmpFishLoc.position.y, null);
        bufferGraphics.rotate(-tmpFishLoc.fish.getRudderDirection(), centerX, centerY);
        /*
        for (FishLocation fishLocation : state.fishLocs) {
            bufferGraphics.setColor(Color.ORANGE);
            bufferGraphics.fillOval((int)fishLocation.position.x, (int)fishLocation.position.y, 50, 50);
        }
        */
           
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

