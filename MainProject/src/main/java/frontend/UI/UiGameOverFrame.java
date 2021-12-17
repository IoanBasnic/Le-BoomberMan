package frontend.UI;

import frontend.UI.DrawObject.DrawScoreboard;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;

public class UiGameOverFrame extends JFrame {

    private UiGameOverComponent uiGameOverComponent;

    public UiGameOverFrame(final String title, DrawScoreboard scoreboard) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        uiGameOverComponent = new UiGameOverComponent(scoreboard);
        this.add(uiGameOverComponent);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
