package testClasses;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.Viewer.CloseFramePolicy;
import org.graphstream.ui.swingViewer.Viewer.ThreadingModel;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;

import wikiBook.InternalKeyManager;
import wikiBook.InternalMouseManager;
import dbTest.DBProvider;



public class testGraph2 {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		long start = System.currentTimeMillis();
		Viewer viewer;
		View view;
		int year = -867;
		  
		Graph graph = new SingleGraph("Test Graph");
		graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.quality");        
		
        graph.addAttribute("ui.stylesheet", "url('file:///Users/Silvio/git/wikihistorybook/wikihistorybook/css/graph_style.css')");
        //graph.addAttribute("layout.force", 0.2);
        
        GraphicElement lastElement = null;
        
		// DB als Singleton
		DBProvider db = DBProvider.getInstance();
		
		// DB Verbindung ��ffnen
		db.getConnection();
		
		System.out.println("connected");
		
		// Query ausführen
		//prepared
		ResultSet people = db.getPeople(year);
		ArrayList<String> peopleList = new ArrayList<String>();
		
		System.out.println("executed");
		
		
		// Resultat ausgeben
		try {
			while(people.next()){
				try {
					Node n = graph.addNode(people.getString("id"));
					n.addAttribute("label", people.getString("name"));
					n.addAttribute("layout.weight", 0.005);
					peopleList.add(people.getString("id"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IdAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(peopleList.size()+" Nodes");
		
		ResultSet connections;
		double i = 0;
		int progress = -1;
		
		for(String id : peopleList) //use for-each loop
		{
			i++;
		    connections = db.getConnectionsFrom(id, year);
		    try {
				while(connections.next()){
					try {
						if(peopleList.contains(connections.getString("person_to"))){
							graph.addEdge(id+connections.getString("person_to"),
									id, connections.getString("person_to"));
						}
					}catch(ElementNotFoundException e){
					}catch(EdgeRejectedException e){
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    if(progress != (int)(100.0 * (i/peopleList.size()))){
		    	System.out.println((int)(100.0 * (i/peopleList.size()))+"%");
		    	progress = (int)(100.0 * (i/peopleList.size()));
		    }
		    
		}
								
		viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
		viewer.enableAutoLayout();
		view = viewer.addDefaultView(false);
		//view.getCamera().setViewPercent(0.5);
		//view.resizeFrame(800, 600);
		
		int FPS_MIN = -2000;
		int FPS_MAX = 2000;
		int FPS_INIT = 0;    //initial frames per second
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                FPS_MIN, FPS_MAX, FPS_INIT);
		
		framesPerSecond.setMajorTickSpacing(1000);
		framesPerSecond.setMinorTickSpacing(100);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		framesPerSecond.setSnapToTicks (true);
		
		view.getCamera().setViewPercent(0.5);
		view.setMouseManager(new InternalMouseManager(lastElement));
		view.setShortcutManager(new InternalKeyManager());
		viewer.setCloseFramePolicy(CloseFramePolicy.EXIT);
		
		JPanel panel = new JPanel(new java.awt.BorderLayout(2, 1));	
		panel.add(view,BorderLayout.CENTER);
//		panel.add(framesPerSecond,BorderLayout.SOUTH);
		
		
		JFrame frame = new javax.swing.JFrame("GraphStream in Swing");
		frame.getContentPane().add(panel);
		frame.setSize(1000, 2000);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	    System.out.println("finished in : "+(System.currentTimeMillis()-start));	
	    
	    // DB Verbindung schliessen
	 	db.closeConnection();

	}
}

