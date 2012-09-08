import javax.swing.JFrame;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.Color;

public class Visualizer extends JFrame implements Runnable {
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;
    private Engine fishtank;
    private Thread engineThread;
    private State state;
    private int width = 512, height = 512;

    public Visualizer() {
        //Set up the fishtank
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = (Graphics2D)buffer.getGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        fishtank = new Engine();
        engineThread = new Thread(fishtank);
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
        bufferGraphics.setColor(Color.BLUE);
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        
        //Draw the fish
        bufferGraphics.setColor(Color.ORANGE);
        bufferG
        
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

