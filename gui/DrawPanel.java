package gui;

import game.Game;
import game.Spieler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel auf das gezeichnet wird.
 */
class DrawPanel extends JPanel {

    private Game game;
    private final int raster;

    DrawPanel(final int size) {
        setPreferredSize(new Dimension(size, size));
        raster = size/3;

        game = new Game(Spieler.Kreis);
        setFocusable(true);
        requestFocus();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game == null || game.isEnded())
                    return;

                int row = e.getY() / raster;
                int col = e.getX() / raster;
                if (!game.setField(game.getHuman(), row, col)) return;
                repaint();

                if (game.isEnded()) return;

                game.computerZug(6);
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    startGame();
                    repaint();
                }
            }
        });
    }

    private void startGame() {
        game.restart(game.getHuman());

        if (game.getComputer() == Spieler.Kreuz) {
            game.computerZug(6);
            repaint();
        }
    }

    private void drawSpieler(Spieler s, int row, int col, Graphics g) {
        if (s == Spieler.Kreuz) {
            g.setColor(Color.red);
            g.drawLine(col * raster + 10, row * raster + 10, (col+1) * raster - 10, (row+1) * raster - 10);
            g.drawLine(col * raster + 10, (row+1) * raster - 10, (col+1) * raster - 10, row * raster + 10);
        } else if (s == Spieler.Kreis) {
            g.setColor(Color.blue);
            g.drawOval(col * raster + 10, row * raster + 10, raster - 20, raster - 20);
        }
    }

    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.black);
        g.drawLine(0, raster, getWidth(), raster);
        g.drawLine(0, 2*raster, getWidth(), 2*raster);
        g.drawLine(raster, 0, raster, getHeight());
        g.drawLine(2*raster, 0, 2*raster, getHeight());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game != null)
                drawSpieler(game.getField(i, j), i, j, g);
            }
        }
    }
}
