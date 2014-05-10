package testClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.implementations.MultiGraph;
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
		JPanel panel = new JPanel(new java.awt.GridLayout(2, 1));;
		  
		Graph graph = new MultiGraph("Test Graph");
		graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.quality");
		
		// DB als Singleton
		DBProvider db = DBProvider.getInstance();
		
		// DB Verbindung öffnen
		db.getConnection();
		
		System.out.println("connected");
		
		// Query ausf�hren
		//prepared
		ResultSet connections = db.getConnections(0);
		
		//ResultSet connections = db.executeQuery("SELECT person_to, person_from FROM wikihistory.connections WHERE year_from < 1000 AND year_to > 1000");
		
		System.out.println("executed");
		
		
		// Resultat ausgeben
		try {
			while (connections.next() ){
				try{
					
					graph.addEdge(connections.getString("person_from")+connections.getString("person_to"), 
							connections.getString("person_from"), connections.getString("person_to"));
					
				} catch (ElementNotFoundException e){
					
					try{
						
						graph.addNode(connections.getString("person_from")).addAttribute("ui.style", "size: 3px;");
						
					} catch (IdAlreadyInUseException f){
					}
					
					try{
						
						graph.addNode(connections.getString("person_to")).addAttribute("ui.style", "size: 3px; fill-color: rgb(0,100,255);");
						
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
						
		viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
		viewer.enableAutoLayout();
		view = viewer.addDefaultView(false);		
		
		viewer.setCloseFramePolicy(CloseFramePolicy.EXIT);
		
		panel = new JPanel(new java.awt.GridLayout(1, 1));
		panel.add(view);
		
		
		JFrame frame = new javax.swing.JFrame("GraphStream in Swing");
		frame.getContentPane().add(panel);
		frame.setSize(1000, 2000);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		//graph.display();
	    System.out.println("finished in : "+(System.currentTimeMillis()-start));

	    // DB Verbindung schliessen
	 	db.closeConnection();

	}
}
