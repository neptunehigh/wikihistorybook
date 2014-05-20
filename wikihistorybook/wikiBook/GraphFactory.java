package wikiBook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import dbTest.DBProvider;

public class GraphFactory extends Thread{
	
	private Graph graph;
	private WikiBook wikibook;
	private DBProvider db;
	private int prg_stat;
	private int cur_year;
	private ArrayList<String> peopleList;
	private ResultSet connections;
	private ResultSet people;	
	private double prg;
	private JProgressBar progressBar;

	
	public GraphFactory(WikiBook wikibook, int year){
		super();
		this.wikibook = wikibook;
		db = DBProvider.getInstance();
		cur_year = year;
		db = wikibook.getDB();
		peopleList = new ArrayList<String>();
		graph = new SingleGraph("Wiki");
		prg = 0;
		progressBar = wikibook.getPG();
		
	}
	
	public void run() {
		if (progressBar != null) {
			progressBar.setIndeterminate(true);
		}
		people = db.getPeople(cur_year);
		try {
			while (people.next()) {
				try {
					Node n = graph.addNode(people.getString("id"));
					n.addAttribute("label", people.getString("name"));
					n.addAttribute("layout.weight", 0.5);
					peopleList.add(people.getString("id"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (IdAlreadyInUseException f) {
			f.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (progressBar != null) {
			progressBar.setIndeterminate(false);
		}
		
		for (String id : peopleList) {
			prg++;
			connections = db.getConnectionsFrom(id, cur_year);
			try {
				while (connections.next()) {
					try {
						if (peopleList.contains(connections
								.getString("person_to"))) {
							graph.addEdge(
									id + connections.getString("person_to"),
									id, connections.getString("person_to"));
						}
					} catch (ElementNotFoundException e) {
					} catch (EdgeRejectedException e) {
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (prg_stat != (int) (100.0 * (prg / peopleList.size()))) {
				prg_stat = (int) (100.0 * (prg / peopleList.size()));
				if (progressBar != null) {
					progressBar.setValue(prg_stat);
				}
			}
		}

		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.quality");

		graph.addAttribute(
				"ui.stylesheet",
				"url('./css/graph_style.css')");
		wikibook.showGraph(graph);
	}

}
