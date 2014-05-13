package wikiBook;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SliderListener implements ChangeListener {

	private WikiBook wikiBook;

	public SliderListener(WikiBook wikiBook) {
		this.wikiBook = wikiBook;
	}

	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int time = (int)source.getValue();
            System.out.println(time);
            GraphFactory factory = new GraphFactory(wikiBook, time);
            factory.start(); 
        }    
    }
}