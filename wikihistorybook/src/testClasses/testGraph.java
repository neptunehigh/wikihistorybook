package testClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.Viewer.CloseFramePolicy;
import org.graphstream.ui.swingViewer.Viewer.ThreadingModel;

import dbTest.DBProvider;

public class testGraph{
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		Viewer viewer;
		View view;
		  
		Graph graph = new SingleGraph("Test Graph");
		graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.quality");
		//graph.addAttribute("ui.stylesheet", "graph { fill-color: red; size: 1px; }");
		
		// DB als Singleton
		DBProvider db = DBProvider.getInstance();
		
		// DB Verbindung öffnen
		db.getConnection();
		
		//String query="SELECT id, name FROM wikihistory.people WHERE year_from > 850 AND year_to < 900 AND year_to IS NOT NULL";
		String query="SELECT person_to, person_from FROM wikihistory.connections WHERE year_to > 0 AND year_from < 5 ";
		
		// Query ausf�hren
		ResultSet connections = db.executeQuery(query);
		
		// Resultat ausgeben
				
		try {
			while (connections.next() ){
				try{
					
					graph.addEdge(connections.getString("person_from")+connections.getString("person_to"), 
							connections.getString("person_from"), connections.getString("person_to"));
					
				} catch (ElementNotFoundException e){
					
					try{
						
						graph.addNode(connections.getString("person_from")).addAttribute("ui.style", "size: 10px;");
						
					} catch (IdAlreadyInUseException f){
					}
					
					try{
						
						graph.addNode(connections.getString("person_to")).addAttribute("ui.style", "size: 10px; fill-color: rgb(0,100,255);");
						
					} catch (IdAlreadyInUseException f){
					}

					graph.addEdge(connections.getString("person_from")+connections.getString("person_to"),
							connections.getString("person_from"), connections.getString("person_to"));
					
				} catch (EdgeRejectedException e){
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// DB Verbindung schliessen
		db.closeConnection();
		
		viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
		//viewer.enableAutoLayout();
		viewer.enableAutoLayout(new SpringBox(false));
		view = viewer.addDefaultView(false);		
 
		viewer.setCloseFramePolicy(CloseFramePolicy.EXIT);
		
		JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
		panel.add(view);
		JFrame frame = new javax.swing.JFrame("GraphStream in Swing");
		frame.getContentPane().add(panel);
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//graph.display();
	    System.out.println("finished in : "+(System.currentTimeMillis()-start));

	}
}
