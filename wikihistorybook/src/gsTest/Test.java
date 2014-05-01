package gsTest;

import java.util.Iterator;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Test {
    public static void main(String args[]) {
    	
            Graph graph = new SingleGraph("Tutorial 1");
            graph.addNode("A" );
            graph.addNode("B" );
            graph.addNode("C" );
            graph.addEdge("AB", "A", "B");
            graph.addEdge("BC", "B", "C");
            graph.addEdge("CA", "C", "A");
            graph.display();
            
            //Node[] nodes = new Node[100];
           
            for (int i = 0; i < 100; i++) {
            	graph.addNode("Node"+i);
			}
            
            
          
            
            for(Node n:graph) {
                System.out.println(n.getId());
            }
            
            for(Edge e:graph.getEachEdge()) {
                System.out.println(e.getId());
            }
            
            //Collection<Node> nodes = graph.getNodeSet();
            
            Iterator<? extends Node> iterator = graph.getNodeIterator();
            
            while(iterator.hasNext()) {
                Node node = iterator.next();
                System.out.println(node.getId());
            }
    }
}