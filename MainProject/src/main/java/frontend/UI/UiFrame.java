package frontend.UI;


import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class UiFrame extends JFrame
{
    private GameMapInitializer floor;
    private UiComponent uiComponent;

    public UiFrame(final String title, GameMapInitializer floor) throws HeadlessException {
	super(title);
	this.floor = floor;
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	uiComponent = new UiComponent(floor);
	floor.createPlayer(uiComponent, floor);
	setKeyStrokes();

	this.setLayout(new BorderLayout());
	this.add(uiComponent, BorderLayout.CENTER);
	this.pack();
	this.setVisible(true);
    }

    public UiComponent getUiComponent() {
	return uiComponent;
    }

    private void setKeyStrokes() {

	KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	uiComponent.getInputMap().put(stroke, "q");
	uiComponent.getActionMap().put("q", quit);
    }

    private final Action quit = new AbstractAction()
    {
	public void actionPerformed(ActionEvent e) {
		dispose();
	    
	}
    };
}

