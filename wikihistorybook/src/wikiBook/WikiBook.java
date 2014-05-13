package wikiBook;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.Viewer.CloseFramePolicy;
import org.graphstream.ui.swingViewer.Viewer.ThreadingModel;

import dbTest.DBProvider;

public class WikiBook extends JFrame{
	
	private JPanel panel;
	private JProgressBar progressBar;
	private DBProvider db;
	private Viewer viewer;
	private View view;
    private GraphicElement lastElement;
    private JSlider tLine; 
    private GraphFactory graphFactory;
    
    private final int SLIDER_MIN = -2000;
	private final int SLIDER_MAX = 2000;
	private final int SLIDER_INIT = 0;
	
	private final int YEAR_INIT = 0;
		
	public WikiBook(){
		super("The-Wiki-History-Book");
		
		db = DBProvider.getInstance();
		db.getConnection();
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		panel = new JPanel(new java.awt.BorderLayout());
		
		tLine = new JSlider(JSlider.HORIZONTAL,
				SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		tLine.setMajorTickSpacing(1000);
		tLine.setMinorTickSpacing(100);
		tLine.setPaintTicks(true);
		tLine.setPaintLabels(true);
		tLine.addChangeListener(new SliderListener(this));
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		graphFactory = new GraphFactory(this, 0);
		graphFactory.start();

		panel.add(tLine,BorderLayout.SOUTH);
		panel.add(progressBar, BorderLayout.NORTH);

		getContentPane().add(panel);
		setSize(1000, 2000);
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	
	public void showGraph(Graph graph){
		View newView;
 	    viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
 		viewer.enableAutoLayout();
 		newView = viewer.addDefaultView(false);
 		newView.setMouseManager(new InternalMouseManager(lastElement));
 		newView.setShortcutManager(new InternalKeyManager());
 		
 		try{
 			panel.remove(view);
 		}catch(NullPointerException e){
 			
 		}
 		panel.add(newView, BorderLayout.CENTER);
 		panel.updateUI();
 		view = newView;
	}
	
	public DBProvider getDB(){
		return db;
	}
	
	public JProgressBar getPG(){
		return progressBar;
	}
	
}






