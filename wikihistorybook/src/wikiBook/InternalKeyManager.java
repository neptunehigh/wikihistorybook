package wikiBook;

import java.awt.event.KeyEvent;

import org.graphstream.ui.swingViewer.util.DefaultShortcutManager;

public class InternalKeyManager extends DefaultShortcutManager {
	public void keyTyped(KeyEvent e) {
		// Invoked when a key has been typed.
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		double p = view.getCamera().getViewPercent();
		switch (e.getKeyCode()) {

		case KeyEvent.VK_A:
			view.getCamera().setViewPercent(p + 0.1);
			break;

		case KeyEvent.VK_Q:
			view.getCamera().setViewPercent(p - 0.1);
			break;

		}
	}

	public void keyReleased(KeyEvent e) {
		// Invoked when a key has been released.
	}
}
