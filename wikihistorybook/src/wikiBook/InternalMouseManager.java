package wikiBook;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;

public class InternalMouseManager extends DefaultMouseManager {
	
	GraphicElement ge = null;
	
	public InternalMouseManager(GraphicElement e){
		super();
		this.ge = e;
	}
	
	public InternalMouseManager(){
		super();
	}
	
	protected void mouseButtonPressOnElement(GraphicElement element,
			MouseEvent event) {
		super.mouseButtonPressOnElement(element, event);

		JOptionPane.showMessageDialog(view, element.getLabel());
	}
	
	public void mouseClicked(MouseEvent e){
		//super.mouseClicked(e);
		
	}
	
	public void mouseMoved(MouseEvent e){
		super.mouseMoved(e);
		ArrayList<GraphicElement> a = view.allNodesOrSpritesIn(e.getX()-6, e.getY()-6, e.getX()+6, e.getY()+6);
		if(!a.isEmpty()){
			if(ge!= null){
				ge.changeAttribute("ui.style", "text-mode: hidden;");
				ge.changeAttribute("ui.style", "fill-color: #8C2; ");
			}
			a.get(0).changeAttribute("ui.style", "text-mode: normal;");
			a.get(0).changeAttribute("ui.style", "fill-color: red; ");
			ge = a.get(0);
		}else{
			if(ge!= null){
				ge.changeAttribute("ui.style", "text-mode: hidden;");
				ge.changeAttribute("ui.style", "fill-color: #8C2; ");
			}
		}
		
	}
}



