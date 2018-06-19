package infovis.paracoords;

import infovis.scatterplot.Model;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	private double mouseOffset;
	private double Yvalue1;
	private double Yvalue2;
	boolean lineMoved = false;
	int movedLine = -1;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() - 30;
		int y = e.getY() - 40;
		
		lineMoved = false;
		movedLine = -1;
		
		for(Double value: view.getPositions()) {
			if(x < value + 3 && x > value -3) {
				lineMoved = true;
				movedLine = view.getPositions().indexOf(value);
				mouseOffset = x - value;
			}
		}
		
		if(!lineMoved)
			view.getMarkerRectangle().setRect(x, y, 0, 0);
        
		view.repaint();

	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
	 	int x = e.getX() - 30;
		int y = e.getY() - 40;
		
		if(lineMoved) {
			view.getPositions().set(movedLine, x - mouseOffset);
			//view.getLine().get(movedLine).setLine(x - mouseOffset, Yvalue1, x - mouseOffset, Yvalue2);
		} else {
			double markerX = view.getMarkerRectangle().getX();
			double markerY = view.getMarkerRectangle().getY();
	    
			if (markerX == 0 && markerY == 0) {
				return;
			}
			view.getMarkerRectangle().setRect(markerX, markerY, x - markerX , y - markerY);
		}
		
	    view.repaint();
	}

	public void mouseMoved(MouseEvent e) {}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
