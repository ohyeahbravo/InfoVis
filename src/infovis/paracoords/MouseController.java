package infovis.paracoords;

import infovis.scatterplot.Model;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
        view.getMarkerRectangle().setRect(x, y, 0, 0);
        view.repaint();

	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
	 	int x = e.getX();
		int y = e.getY();
			
	    double markerX = view.getMarkerRectangle().getX();
	    double markerY = view.getMarkerRectangle().getY();

	    if (markerX == 0 && markerY == 0) {
	    	return;
	    }
	    view.getMarkerRectangle().setRect(markerX, markerY, x - markerX , y - markerY);
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
