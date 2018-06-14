package infovis.scatterplot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
	
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
        if (!view.overviewContain(x, y)) {
            return;
        }

        double dx = (view.getWidth() - view.getHeight()) / 2;
        view.getMarkerRectangle().setRect(x - dx, y, 0, 0);
        view.repaint();
    }

    public void mouseReleased(MouseEvent arg0) {
    	view.repaint();
    }

    public void mouseDragged(MouseEvent arg0) {
    	int x = arg0.getX();
		int y = arg0.getY();
		
        double markerX = view.getMarkerRectangle().getX();
        double markerY = view.getMarkerRectangle().getY();
        double dx = (view.getWidth() - view.getHeight()) / 2;
        
        view.getMarkerRectangle().setRect(markerX, markerY, x - markerX - dx, y - markerY);
        view.repaint();
    }
    
	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
