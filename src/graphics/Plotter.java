package graphics;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class Plotter extends JFrame implements Runnable {
	
	private class Point {
		public double x;
		public double y;
		public Point(double x, double y){
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args){
		if(args.length != 0){
			System.err.println("Usage: java Plotter");
			System.exit(-1);
		}
		
		Thread plotter = new Thread(new Plotter());
		plotter.start();
	}

	public void run() {
		Hashtable<String, ArrayList<Point>> datasets = new Hashtable<String, ArrayList<Point>>();
		setVisible(true);
		
		Scanner in = new Scanner(System.in);
		while(in.hasNextLine()){
			//Read in the data
			String[] numParts = in.nextLine().split(" ");
			int numAIs = Integer.parseInt(numParts[0]);
			int tickTime = Integer.parseInt(numParts[1]);
			for(int i = 0; i < numAIs; i++){
				String[] parts = in.nextLine().split(" ");
				double avgFish = Double.parseDouble(parts[1]);
				
				ArrayList<Point> dset = datasets.get(parts[0]);
				if(dset == null){
					dset = new ArrayList<Point>();
					datasets.put(parts[0], dset);
				}
				dset.add(new Point(tickTime, avgFish));
			}
			
			//Construct the jfreechart dataset
			DefaultXYDataset dataset = new DefaultXYDataset();
			for(String k : datasets.keySet()){
				ArrayList<Point> data = datasets.get(k);
				double[][] series = new double[2][data.size()];
				for(int i = 0; i < series[0].length; i++){
					series[0][i] = data.get(i).x;
					series[1][i] = data.get(i).y;
				}
				dataset.addSeries(k, series);
			}
	        
	        // based on the dataset we create the chart
	        JFreeChart chart = ChartFactory.createXYLineChart("Average Fish",// chart title
	        		"Time",
	        		"Num Fish",
	                (XYDataset)dataset,                // data
	                PlotOrientation.VERTICAL,
	                true,                   // include legend
	                true,
	                false);

	        XYPlot plot = (XYPlot) chart.getPlot();
	        plot.setForegroundAlpha(0.5f);
	        // we put the chart into a panel
	        ChartPanel chartPanel = new ChartPanel(chart);
	        // default size
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        // add it to our application
	        setContentPane(chartPanel);
	        pack();
		}
	}
}
