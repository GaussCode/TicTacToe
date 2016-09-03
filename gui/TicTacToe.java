package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Hauptfenster
 */
public class TicTacToe extends JFrame {

    private DrawPanel drawPanel;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        drawPanel = new DrawPanel(300);
        setContentPane(drawPanel);
        pack();

        setResizable(false);
        int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2;
        int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2;
        setBounds(x, y, getWidth(), getHeight());

        setVisible(true);
    }

    public static void main(String args[]) {
        new TicTacToe();
    }
}
