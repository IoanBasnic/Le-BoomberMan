package frontend.UI;

import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;

public class UiGameOverFrame extends JFrame {

    private UiGameOverComponent uiGameOverComponent;

    public UiGameOverFrame(final String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        uiGameOverComponent = new UiGameOverComponent();
        this.add(uiGameOverComponent);
        this.setSize(500, 300);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
