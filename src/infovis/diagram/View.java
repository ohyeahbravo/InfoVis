package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX= 0;
	private double translateY= 0;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double();
	private double ratio = 0.25; // overview is one fourth of the real objects
	private double overviewOffset = 15;	// overview origin is (15, 15)

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		int overviewH = 180;
		int overviewW = 278;
		double markerH = getHeight()/scale;
		double markerW = getWidth()/scale;
		
		// adjusting the marker position not to escape the overview frame
		if(translateX < 0.0)
			translateX = 0.0;
		if(translateY < 0.0)
			translateY = 0.0;
		if(translateX > overviewW/ratio - markerW)
			translateX = overviewW/ratio - markerW;
		if(translateY > overviewH/ratio - markerH)
			translateY = overviewH/ratio - markerH;
		
		g2D.scale(scale, scale); // for zoom in-out
		g2D.translate(-translateX, -translateY);	// screen should match the marker area
		paintDiagram(g2D);	// drawing the objects
		g2D.translate(translateX, translateY);	// translate back for the overview frame
		
		// assignment 1-1 : Overview Frame
		g2D.scale(1/scale, 1/scale);	// scaling back
		g2D.translate(overviewOffset, overviewOffset);	// shift the origin of the overview
		g2D.clearRect(0, 0, overviewW, overviewH);
		overviewRect.setRect(0, 0, overviewW, overviewH);
		g2D.draw(overviewRect);
		g2D.clip(overviewRect);	// overview frame doesn't intrude the main frame
		g2D.scale(ratio, ratio);	// scaling the diagram of the overview
		paintDiagram(g2D);
		
		// assignment 1-2 : Overview Marker
		marker.setRect(this.translateX, this.translateY, markerW, markerH);
		g2D.setStroke(new BasicStroke(1));
		g2D.setColor(Color.RED);
		g2D.draw(marker);
	}
	
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getRatio() {
		return ratio;
	}
	public double getOverviewOffset() {
		return overviewOffset;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	
	public void updateMarker(int x, int y){
		marker.setFrame(x, y, 16, 10);
	}
	
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
}
 