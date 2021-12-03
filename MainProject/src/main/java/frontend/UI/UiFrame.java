package frontend.UI;


import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class UiFrame extends JFrame
{
    private UiComponent uiComponent;

    public UiFrame(final String title, GameMapInitializer gameMapInitializer) throws HeadlessException {
	super(title);
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	uiComponent = new UiComponent(gameMapInitializer);
	gameMapInitializer.createPlayer(uiComponent);

	this.setLayout(new BorderLayout());
	this.add(uiComponent, BorderLayout.CENTER);
	this.pack();
	this.setVisible(true);
    }

    public UiComponent getUiComponent() {
		return uiComponent;
    }
}

