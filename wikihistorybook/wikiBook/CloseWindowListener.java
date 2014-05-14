package wikiBook;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseWindowListener extends WindowAdapter {

	WikiBook wikibook;
	
    public CloseWindowListener(WikiBook wikibook) {
    	super();
    	this.wikibook = wikibook;
    }

    public void windowClosing(WindowEvent e) {
        wikibook.getDB().closeConnection();
    }
}