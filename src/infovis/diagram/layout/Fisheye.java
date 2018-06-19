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

	int d = 5;	// distortion factor
		
	int PfishX;
	int PfishY;
	
	double DnormX;
	double DnormY;
	
	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
		
		double focusX = view.getMousePosition().x;
		double focusY = view.getMousePosition().y;

		DnormX = x - focusX;
		DnormY = y - focusY;
		
		double DmaxX = (DnormX >= 0 ? view.getWidth() - focusX : 0 - focusX);
		double DmaxY = (DnormY >= 0 ? view.getHeight() - focusY : 0 - focusY);
		
		PfishX = (int) (focusX + G(DnormX/DmaxX)*DmaxX);
		PfishY = (int) (focusY + G(DnormY/DmaxY)*DmaxY);
	}

	public Model transform(Model model, View view) {
		// TODO Auto-generated method stub
		
		Model fishModel = new Model();
		
		double ratio;
		double PnormX, PnormY;
		double SnormX, SnormY;
		double QnormX, QnormY;
		double SgeomX, SgeomY;
		
		for (Vertex vertex: model.getVertices()){
			
			// center of the vertex
			PnormX = vertex.getCenterX();
			PnormY = vertex.getCenterY();

			// width & height of a vertex bounding box
			SnormX = vertex.getWidth();
			SnormY = vertex.getHeight();
			ratio = SnormX / SnormY;
			
			setMouseCoords((int) PnormX, (int) PnormY, view);
			Vertex fishVertex = new Vertex(PfishX + SnormX/2, PfishY + SnormY/2);
			
			// point on the box in the direction of the focus point
			QnormX = (DnormX >= 0 ? PnormX + SnormX/2 : PnormX - SnormX/2);
			QnormY = (DnormY >= 0 ? PnormY + SnormY/2 : PnormY - SnormY/2);
			
			// new geometric size
			setMouseCoords((int) QnormX, (int) QnormY, view);
			SgeomX = 2 * Math.abs(PfishX - fishVertex.getCenterX());
			SgeomY = 2 * Math.abs(PfishY - fishVertex.getCenterY());
			
			if(SgeomX < SgeomY)
				SgeomY = SgeomX / ratio;
			else SgeomX = SgeomY * ratio;

			fishVertex.setWidth(SgeomX);
			fishVertex.setHeight(SgeomY);
			
			fishModel.addVertex(fishVertex);
		}
		
		return fishModel;
	}
	
	public double G(double value) {
		return ((d+1) * value ) / (d*value + 1);
	}
	
}
