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
	private double translateY=0;
	private Rectangle2D marker = new Rectangle2D.Double();
	private Rectangle2D overviewRect = new Rectangle2D.Double();   

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
		
		g2D.scale(scale, scale); // for zoom in-out
		paintDiagram(g2D);
		
		// assignment 1-1 : Overview Frame
		int overviewH = 180;
		int overviewW = 278;
		g2D.scale(1/scale, 1/scale);	// scaling back
		g2D.translate(15, 15);	// shift the origin of the overview
		g2D.clearRect(0, 0, overviewW, overviewH);
		overviewRect.setRect(0, 0, overviewW, overviewH);
		g2D.draw(overviewRect);
		g2D.clip(overviewRect);	// overview frame doesn't intrude the main frame
		g2D.scale(0.25, 0.25);	// scaling the diagram of the overview
		paintDiagram(g2D);
		
		// assignment 1-2 : Overview Marker
		marker.setRect(0, 0, getWidth()/scale, getHeight()/scale);
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
		marker.setRect(x, y, 16, 10);
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
}
 