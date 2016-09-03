package console;

import game.Game;
import game.Spieler;

import java.util.Scanner;

/**
 * Spiel als Konsolenversion
 */
public class TTTConsole {

    public static void main(String args[]) {
        Game game = new Game(Spieler.Kreis);
        Scanner s = new Scanner(System.in);

        for (int i = 0; i < 5; i++) {
            int r, c;
            do {
                r = Integer.parseInt(s.next());
                c = Integer.parseInt(s.next());
            } while (!game.setField(game.getHuman(), r, c));
            game.print();

            if (game.isWinning() != Spieler.Niemand)
                return;

            if (i < 4) {
                game.computerZug(6);
                game.print();
            }

            if (game.isWinning() != Spieler.Niemand)
                return;
        }
    }
}
