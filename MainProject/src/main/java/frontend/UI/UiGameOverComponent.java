package frontend.UI;

import javax.swing.*;
import java.awt.*;
import frontend.UI.DrawObject.DrawScoreboard;
import map_tracker.GameMapInitializer;

public class UiGameOverComponent extends JComponent{

    DrawScoreboard scoreboard;

    public UiGameOverComponent(DrawScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("GAME OVER!",312, 66);

        scoreboard.drawScoreboard(g2d);

        Font button_font = new Font("Serif", Font.PLAIN, 30);
//        JButton butt1 = new JButton();
//        butt1.setLocation(150, 90);
//        butt1.setSize(200, 50);
//        butt1.setBackground(Color.GRAY);
//        butt1.setBorderPainted(true);
//        butt1.setOpaque(true);
//        butt1.setFont(button_font);
//        butt1.setText("NEW GAME");
//        this.add(butt1);

        JButton butt2 = new JButton();
        butt2.setLocation(400, 500);
        butt2.setSize(200, 50);
        butt2.setBackground(Color.GRAY);
        butt2.setBorderPainted(true);
        butt2.setOpaque(true);
        butt2.setFont(button_font);
        butt2.setText("QUIT!");
        this.add(butt2);
        this.repaint();
    }
}
