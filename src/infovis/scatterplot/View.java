package infovis.scatterplot;

import infovis.debug.Debug;
import infovis.diagram.Model;

import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	    private infovis.scatterplot.Model model = null;
	     
	    private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);    
	    private Color color = Color.WHITE;
	    private Color markerColor = Color.RED;
		 
		public void setModel(infovis.scatterplot.Model model) {
			this.model = model;
		}				
		public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}		
		public boolean overviewContain(double x, double y) {
			return new Rectangle2D.Double((getWidth() - getHeight()) / 2, 0, getWidth(), getHeight()).contains(x,y);
		}

		@Override
		public void paint(Graphics g) {
			
			// the number of scatterplots in each axis
			int number = model.getLabels().size();
			
			// size of labels and overview plot
			int xLabel = (int) (getWidth() * 0.08);
			int yLabel = (int) (getHeight() * 0.08);
			int plotSize = Math.min((getWidth()/number), (getHeight() - yLabel)/number);
			
						
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());
			g2D.translate((getWidth() - getHeight()) / 2, 0);
			
			//Setting the labels of the scatter plots
			ArrayList<String> labels = model.getLabels();
			for(int i = 0; i < number; i++) {
				String label = labels.get(i);
				
				g2D.setFont(new Font("default", Font.PLAIN, 10));
				g2D.drawString(label, xLabel + (i * plotSize), (int) (yLabel * 0.8));
				g2D.drawString(label, (int) (xLabel * 0.08), (int) (yLabel + ((i + 0.5) * plotSize)));			
			}
			
						
			//Setting the title of the visualization
			g2D.setFont(new Font("default", Font.BOLD, 16));
			g2D.setColor(Color.BLACK);
			g2D.drawString("Scatter Plot", (int) (getWidth() * 0.3), (int) (getHeight() * 0.04));
			
			//Setting up the scatter plots
			for(int x = 0; x < number; x++) {
				for(int y = 0; y < number; y++) {
					g2D.setColor(color);					
					g2D.fillRect(xLabel + (x * plotSize), yLabel + (y * plotSize), plotSize, plotSize);
					g2D.setColor(Color.BLACK);
					g2D.drawRect(xLabel + x * plotSize, yLabel + y * plotSize, plotSize, plotSize);
				}
			}
			
			ArrayList<Data> markerData = new ArrayList<>();
			
			//Putting the data in the scatter plot
			for(Data d : model.getList()) {
				for(int x = 0; x < number; x++) {
					Range XoffsetRange = model.getRanges().get(x);
					for(int y = 0; y < number; y++) {
						Range YoffsetRange = model.getRanges().get(y);
						
						double Xrange = (XoffsetRange.getMax() - XoffsetRange.getMin()) * 2;
						double Yrange = (YoffsetRange.getMax() - YoffsetRange.getMin()) * 2;						
						
						double x1 = d.getValues() [x];
						double y1 = d.getValues() [y];
												
						double xVal = (xLabel + (x * plotSize)) + (((x1 - XoffsetRange.getMin() + (0.2 * Xrange)) / Xrange) * plotSize);
						double yVal = (yLabel + ((y + 1) * plotSize)) - (((y1 - YoffsetRange.getMin() + (0.2 * Yrange)) / Yrange) * plotSize);
						
						if(markerRectangle.contains(xVal, yVal)) {
							markerData.add(d);
						} else {
							g2D.setColor(Color.BLACK);
							g2D.fill(new Rectangle2D.Double(xVal, yVal, 4, 4));
						}												
					}
				}
			}
			
			for(Data d : markerData) {
				for(int x = 0; x < number; x++) {
					Range XoffsetRange = model.getRanges().get(x);					
					for(int y = 0; y < number; y++) {
						Range YoffsetRange = model.getRanges().get(y);
						
						double Xrange = (XoffsetRange.getMax() - XoffsetRange.getMin()) * 2;
						double Yrange = (YoffsetRange.getMax() - YoffsetRange.getMin()) * 2;
						
						double x1 = d.getValues() [x];
						double y1 = d.getValues() [y];
						
						double xVal = (xLabel + (x * plotSize)) + (((x1 - XoffsetRange.getMin() + (0.2 * Xrange)) / Xrange) * plotSize);
						double yVal = (yLabel + ((y + 1) * plotSize)) - (((y1 - YoffsetRange.getMin() + (0.2 * Yrange)) / Yrange) * plotSize);
						
						g2D.setColor(markerColor);
						g2D.fill(new Rectangle2D.Double(xVal,yVal, 4, 4));
					}
				}
			}
						
	        /*for (String l : model.getLabels()) {
				Debug.print(l);
				Debug.print(",  ");
				Debug.println("");
			}
			for (Range range : model.getRanges()) {
				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}	*/
			
			g2D.setStroke(new BasicStroke(1));
			g2D.setColor(markerColor);
			g2D.draw(markerRectangle);
		}
}
