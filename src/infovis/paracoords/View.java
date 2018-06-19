package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;
import infovis.scatterplot.Range;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	
	private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	private Color color = Color.WHITE;
	private Color markerColor = Color.RED;
	private ArrayList<Line2D> lines = new ArrayList<Line2D>();
	private ArrayList<Double> positions = new ArrayList<Double>();
	
	public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}
	public boolean overviewContain(double x, double y) {
		return new Rectangle2D.Double((getWidth() - getHeight()) / 4, 0, getWidth(), getHeight()).contains(x,y);
	}
	public ArrayList<Line2D> getLine() {
		return lines;
	}
	public ArrayList<Double> getPositions() {
		return positions;
	}
	
	@Override
	public void paint(Graphics g) {
		
		int number = model.getLabels().size();
		
		int plotSize = Math.min(getWidth()/number, getHeight()/number);
		boolean firstRun = false;
		
		if(positions.isEmpty()) {
			firstRun = true;
		}
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, (int) (getWidth()), getHeight());
		g2D.translate(30, 40);
		
		int height = getHeight() - 70;
		double pos;
		ArrayList<Data> markerData = new ArrayList<>();
		
		for(int x = 0; x < number; x++) {
			
			if(firstRun) {
				positions.add(x, (double) (x * plotSize));
			}
			pos = positions.get(x);
			
			//Setting the title of the visualization
			g2D.setFont(new Font("default", Font.BOLD, 14));
			g2D.setColor(Color.BLACK);
			g2D.drawString("Paracoords Visualization", 160, -15);

			//Setting the labels of the visualization
			g2D.setColor(Color.BLACK);
			g2D.setFont(new Font("default", Font.PLAIN, 10));
			g2D.drawString(model.getLabels().get(x), (int) pos, height + 20);
			
			// Draw Lines
			g2D.setStroke(new BasicStroke(3));

			Line2D line = new Line2D.Double(pos, 0, pos, height);
			
			if(firstRun) {
				lines.add(line);
			} else {
				lines.get(x).setLine(line);
			}

			g2D.draw(line);
			g2D.setStroke(new BasicStroke(1));			
			
			ArrayList<Data> point = model.getList();
			
			//Putting the data into the visualization
			for(Data p : point) {
				Range pointRange = model.getRanges().get(x);
				
				int xVal = (int) pos;
				int yVal = (int) ((p.getValue(x) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height);
				
				//Check if the data are inside the marker
				if(markerRectangle.contains(xVal, yVal)) {
					markerData.add(p);
				}else {
					// Data Points
					g2D.setColor(Color.BLUE);
					g2D.fill(new Rectangle2D.Double(xVal-3, yVal-3, 6, 6));
					
					// Connecting Lines
					g2D.setColor(Color.BLACK);
					if(x > 0 && x < number) {
						pointRange = model.getRanges().get(x - 1);
						double previous = positions.get(x-1);
						int previousInt = (int) previous;
						g2D.drawLine(xVal, yVal, previousInt, (int) ((p.getValue(x - 1) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height));
						
					}
				}
			}
		}

		
		//For the repaint of the points that are inside the marker
		for(int x = 0; x < number; x++) {
			
			pos = positions.get(x);
			
			for(Data p : markerData) {
				Range pointRange = model.getRanges().get(x);
				
				int xVal = (int) pos;
				int yVal = (int) ((p.getValue(x) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height);
				
				g2D.setColor(markerColor);
				g2D.fill(new Rectangle2D.Double(xVal, yVal, 4, 4));
					
				g2D.setColor(markerColor);
				if(x > 0 && x < number) {
					pointRange = model.getRanges().get(x - 1);
					double previous = positions.get(x-1);
					int previousInt = (int) previous;
					g2D.drawLine(xVal, yVal, previousInt, (int) ((p.getValue(x - 1) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height));
					
				}
			}
		}
		
		g2D.setStroke(new BasicStroke(1));
		g2D.setColor(markerColor);
		g2D.draw(markerRectangle);
		

		
	}
	

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
