package br.com.marco.pacman.ui;

import java.awt.*;

public class GameOverPanel {

    private final GamePanel gamePanel;
    private int selectedOption = 0;
    private final String[] options = {"RETRY", "EXIT"};

    public GameOverPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void moveUp() {
        selectedOption = (selectedOption - 1 + options.length) % options.length;
    }

    public void moveDown() {
        selectedOption = (selectedOption + 1) % options.length;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void render(Graphics g) {

        int width = gamePanel.getWidth();
        int height = gamePanel.getHeight();

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

        String title = "GAME OVER";
        int titleX = (width - fmTitle.stringWidth(title)) / 2;
        int titleY = height / 3;

        g2d.setColor(new Color(200, 0, 0));
        g2d.drawString(title, titleX + 4, titleY + 4);

        g2d.setColor(Color.RED);
        g2d.drawString(title, titleX, titleY);

        int optionSize = (int) (height * 0.06);
        g2d.setFont(new Font("Monospaced", Font.BOLD, optionSize));
        FontMetrics fmOpt = g2d.getFontMetrics();

        int startY = (int) (height * 0.6);
        int spacing = (int) (optionSize * 2);

        for (int i = 0; i < options.length; i++) {

            String text = options[i];
            int textX = (width - fmOpt.stringWidth(text)) / 2;
            int textY = startY + (i * spacing);

            if (i == selectedOption) {

                g2d.setColor(Color.YELLOW);

                int pacSize = optionSize;
                int pacX = textX - pacSize - 15;
                int pacY = textY - pacSize + 5;

                g2d.fillArc(pacX, pacY, pacSize, pacSize, 35, 290);

                g2d.setColor(Color.WHITE);

            } else {
                g2d.setColor(Color.DARK_GRAY);
            }

            g2d.drawString(text, textX, textY);
        }
    }
}