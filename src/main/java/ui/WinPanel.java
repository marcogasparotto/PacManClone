package br.com.marco.pacman.ui;

import java.awt.*;

public class WinPanel {

    public void render(Graphics g, int width, int height) {

        if (width <= 0 || height <= 0) return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(25, 25, 166));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(20, 20, width - 40, height - 40);

        int titleSize = (int) (height * 0.12);
        g2d.setFont(new Font("Monospaced", Font.BOLD, titleSize));
        FontMetrics fmTitle = g2d.getFontMetrics();

        String title = "YOU WON!!!!";
        int titleX = (width - fmTitle.stringWidth(title)) / 2;
        int titleY = height / 3;

        g2d.setColor(new Color(200, 0, 0));
        g2d.drawString(title, titleX + 4, titleY + 4);

        g2d.setColor(Color.YELLOW);
        g2d.drawString(title, titleX, titleY);

        int subSize = (int) (height * 0.05);
        g2d.setFont(new Font("Monospaced", Font.BOLD, subSize));
        FontMetrics fmSub = g2d.getFontMetrics();

        String sub = "PRESS ENTER TO CONTINUE";
        int subX = (width - fmSub.stringWidth(sub)) / 2;
        int subY = (int) (height * 0.65);

        g2d.setColor(Color.WHITE);
        g2d.drawString(sub, subX, subY);

        int pacSize = (int) (height * 0.1);
        int pacX = (width - pacSize) / 2;
        int pacY = subY + 40;

        g2d.setColor(Color.YELLOW);
        g2d.fillArc(pacX, pacY, pacSize, pacSize, 45, 270);
    }
}