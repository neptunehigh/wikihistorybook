package testClasses;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

import wikiBook.InternalKeyManager;
import wikiBook.InternalMouseManager;
import dbTest.DBProvider;



public class testGraph3 {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		JPanel panel = new JPanel(new java.awt.BorderLayout(2, 1));	
		long start = System.currentTimeMillis();
		Viewer viewer;
			  
		        
        GraphicElement lastElement = null;
        
		DBProvider db = DBProvider.getInstance();
		db.getConnection();
		
		System.out.println("connected");

		Graph graph = getGraph(-867, db);
								
		viewer = new Viewer(graph, ThreadingModel.GRAPH_IN_SWING_THREAD);
		viewer.enableAutoLayout();
		viewer.setCloseFramePolicy(CloseFramePolicy.EXIT);
		View view;	
		
		view = viewer.addDefaultView(false);
		view.setMouseManager(new InternalMouseManager(lastElement));
		view.setShortcutManager(new InternalKeyManager());
		
		
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
		framesPerSecond.addChangeListener(new SliderListener(db,panel,view));
		
		panel.add(view, BorderLayout.CENTER);
		panel.add(framesPerSecond,BorderLayout.SOUTH);
		
		JFrame frame = new javax.swing.JFrame("GraphStream in Swing");
		frame.getContentPane().add(panel);
		frame.setSize(1000, 2000);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	    System.out.println("finished in : "+(System.currentTimeMillis()-start));
		
	    // DB Verbindung schliessen

	}
	
	public static Graph getGraph(int year, DBProvider db){
		ResultSet people = db.getPeople(year);
		ArrayList<String> peopleList = new ArrayList<String>();
		Graph graph = new SingleGraph("Test Graph");
		
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
				graph.addAttribute("ui.antialias");
		        graph.addAttribute("ui.quality");   
				
		        graph.addAttribute("ui.stylesheet", "url('file:///Users/Silvio/git/wikihistorybook/wikihistorybook/css/graph_style.css')");
		return graph;
	}
}

class SliderListener implements ChangeListener {
	
	DBProvider db;
	JPanel panel;
	View view;
	
	
	public SliderListener(DBProvider db, JPanel panel, View view){
		this.db = db;
		this.panel = panel;
		this.view = view;
	}
	
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            System.out.println(fps);
            Graph graph2 = testGraph3.getGraph(fps, db);
            View view2;
    	    Viewer viewer = new Viewer(graph2, ThreadingModel.GRAPH_IN_SWING_THREAD);
    		viewer.enableAutoLayout();
    		view2= viewer.addDefaultView(false);
    		view2.setMouseManager(new InternalMouseManager());
    		view2.setShortcutManager(new InternalKeyManager());
    		
    		panel.remove(view);
    		panel.add(view2, BorderLayout.CENTER);
    		panel.updateUI();
    		this.view = view2;
        }    
    }
}




