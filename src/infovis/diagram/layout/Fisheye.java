package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;

import java.awt.Point;
import java.util.Iterator;

/*
 * 
 */

public class Fisheye implements Layout{

	int d = 2;	// distortion factor
	
	int focusX;
	int focusY;
	
	int PfishX;
	int PfishY;
	
	double DmaxX;
	double DmaxY;
	double DnormX;
	double DnormY;
	
	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
		
		focusX = view.getMousePosition().x;
		focusY = view.getMousePosition().y;

		DnormX = x - focusX;
		DnormY = y - focusY;
		
		DmaxX = (DnormX >= 0 ? view.getWidth() - focusX : 0 - focusX);
		DmaxY = (DnormY >= 0 ? view.getHeight() - focusY : 0 - focusY);
		
		PfishX = (int) (focusX + G(DnormX/DmaxX)*DmaxX);
		PfishY = (int) (focusY + G(DnormY/DmaxY)*DmaxY);
	}

	public Model transform(Model model, View view) {
		// TODO Auto-generated method stub
		for (Element element: model.getElements()){
			setMouseCoords((int) element.getX(), (int) element.getY(), view);
			element.setX(PfishX);
			element.setY(PfishY);
		}
		
		return null;
	}
	
	public double G(double value) {
		return ((d+1) * value ) / (d*value + 1);
	}
	
}
