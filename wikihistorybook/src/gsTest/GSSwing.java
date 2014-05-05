package gsTest;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.Viewer.CloseFramePolicy;
import org.graphstream.ui.swingViewer.Viewer.ThreadingModel;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;
 
public class GSSwing implements Runnable {
  public static void main(String... args) {
		SwingUtilities.invokeLater(new GSSwing());
	}
 
	Graph g;
	Viewer viewer;
	View view;
 
	public void run() {
		if (!SwingUtilities.isEventDispatchThread())
			throw new RuntimeException("Not in SWING thread");
 
		init();
	}
 
	private void init() {
		g = new AdjacencyListGraph("g");
		BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator();
 
		/*
		 * Generate an initial graph.
		 */
		gen.addSink(g);
		gen.begin();
		for (int i = 0; i < 100; i++)
			gen.nextEvents();
		gen.end();
		gen.removeSink(g);
 
		/*
		 * Initialize the GUI part.
		 */
		viewer = new Viewer(g, ThreadingModel.GRAPH_IN_SWING_THREAD);
		viewer.enableAutoLayout();
		view = viewer.addDefaultView(false);
 
		viewer.setCloseFramePolicy(CloseFramePolicy.EXIT);
		view.setMouseManager(new InternalMouseManager());
		view.setShortcutManager(new InternalShortcutManager());
 
		// Create GUI frame:  panel with a button and the GraphStream view
		buttonAndGraphGui();
	}
 
	private void buttonAndGraphGui() {
		JPanel panel = new JPanel(new java.awt.GridLayout(3, 1));
		JToggleButton button = new ToggleGreen(g).getButton();
		  int FPS_MIN = -2000;
		 int FPS_MAX = 2000;
		int FPS_INIT = 0;    //initial frames per second
		
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
		                                      FPS_MIN, FPS_MAX, FPS_INIT);
		panel.add(view);
		panel.add(button);
		panel.add(framesPerSecond);
		
		framesPerSecond.setMajorTickSpacing(1000);
		framesPerSecond.setMinorTickSpacing(100);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		framesPerSecond.setSnapToTicks (true);
		
		JFrame frame = new javax.swing.JFrame("GraphStream in Swing");
		frame.getContentPane().add(panel);
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
 
	class InternalMouseManager extends DefaultMouseManager {
		protected void mouseButtonPressOnElement(GraphicElement element,
				MouseEvent event) {
			super.mouseButtonPressOnElement(element, event);
 
			JOptionPane.showMessageDialog(view,
					"You click on " + element.getId());
		}
	}
 
	class InternalShortcutManager extends DefaultShortcutManager {
		public void keyTyped(KeyEvent event) {
			super.keyTyped(event);
 
			switch (event.getKeyChar()) {
			case 'S':
			case 's':
				//
				// Do something amazing.
				//
				String c = String.format("#%02X%02X%02X",
						(int) (Math.random() * 255),
						(int) (Math.random() * 255),
						(int) (Math.random() * 255));
 
				g.addAttribute("ui.stylesheet",
						String.format("node {fill-color: %s;}", c));
 
				break;
			default:
				// Nothing
			}
		}
	}
 
	class ToggleGreen {
		javax.swing.JToggleButton button;
		public javax.swing.JToggleButton getButton() {return button;}
		public void setButton(javax.swing.JToggleButton button) {this.button = button;}
 
		private Graph graph = null;
		public Graph getGraph() {return graph;}
		public void setGraph(Graph _graph) {graph = _graph;}
 
 
		ToggleGreen(Graph graph) {
			setGraph(graph);
 
			button = new javax.swing.JToggleButton("Green",  false);
			button.addItemListener (new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent event) {
					if (button.isSelected()) {
						System.out.println("Green ON");
						for (Node node: getGraph().getEachNode()) {
							node.addAttribute("ui.style", "fill-color:green;");
						}
					}else if(!button.isSelected()){
						System.out.println("Green OFF");
						for (Node node: getGraph().getEachNode()) {
							node.addAttribute("ui.style", "fill-color:black;");
						}
					}
				}
			});
		}
	}
}