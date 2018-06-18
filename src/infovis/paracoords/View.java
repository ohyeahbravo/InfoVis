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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class View extends JPanel {
	private Model model = null;
	
	private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	private Color color = Color.WHITE;
	private Color markerColor = Color.RED;
	
	public Rectangle2D getMarkerRectangle() {
		return markerRectangle;
	}
	public boolean overviewContain(double x, double y) {
		return new Rectangle2D.Double((getWidth() - getHeight()) / 4, 0, getWidth(), getHeight()).contains(x,y);
	}

	@Override
	public void paint(Graphics g) {
		
		int number = model.getLabels().size();
		
		int plotSize = Math.min(getWidth()/number, getHeight()/number);
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, (int) (getWidth()), getHeight());
		g2D.translate(30, 40);
		
		int height = getHeight() - 70;
		
		ArrayList<Data> markerData = new ArrayList<>();
		
		for(int x = 0; x < number; x++) {
			//Setting the title of the visualization
			g2D.setFont(new Font("default", Font.BOLD, 14));
			g2D.setColor(Color.BLACK);
			g2D.drawString("Paracoords Visualization", 160, -15);

			//Setting the labels of the visualization
			g2D.setColor(Color.BLACK);
			g2D.setFont(new Font("default", Font.PLAIN, 10));
			g2D.drawString(model.getLabels().get(x), (int) (x * plotSize), height + 20);
			g2D.drawLine(x * plotSize, 0, (int) (x * plotSize), height);
			
			ArrayList<Data> point = model.getList();
			
			//Putting the data into the visualization
			for(Data p : point) {
				Range pointRange = model.getRanges().get(x);
				
				int xVal = x * plotSize;
				int yVal = (int) ((p.getValue(x) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height);
				
				//Check if the data are inside the marker
				if(markerRectangle.contains(xVal, yVal)) {
					markerData.add(p);
				}else {
					g2D.setColor(color);
					g2D.fill(new Rectangle2D.Double(getWidth(), height, plotSize, plotSize));
					
					g2D.setColor(Color.BLACK);
					g2D.fill(new Rectangle2D.Double(xVal, yVal, 4, 4));
					
					g2D.setColor(Color.BLACK);
					g2D.fill(new Rectangle2D.Double(xVal + 1, yVal + 1, 4, 4));
					
					g2D.setColor(Color.BLACK);
					if(x + 1 < number) {
						pointRange = model.getRanges().get(x + 1);
						g2D.drawLine(xVal, yVal, ((x + 1) * plotSize), (int) ((p.getValue(x + 1) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height));
						
					}
				}
			}
		}
		
		//For the repaint of the points that are inside the marker
		for(int x = 0; x < number; x++) {
			for(Data p : markerData) {
				Range pointRange = model.getRanges().get(x);
				
				int xVal = x * plotSize;
				int yVal = (int) ((p.getValue(x) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height);
				
				g2D.setColor(markerColor);
				g2D.fill(new Rectangle2D.Double(xVal, yVal, 4, 4));
				
				g2D.setColor(markerColor);
				g2D.fill(new Rectangle2D.Double(xVal + 1, yVal + 1, 4, 4));
					
				g2D.setColor(markerColor);
				if(x + 1 < number) {
					pointRange = model.getRanges().get(x + 1);
					g2D.drawLine(xVal, yVal, ((x + 1) * plotSize), (int) ((p.getValue(x + 1) - pointRange.getMin()) / (pointRange.getMax() - pointRange.getMin()) * height));
						
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
