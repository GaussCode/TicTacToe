package game;

import java.util.Arrays;

/**
 * Hilfsklasse zum Sammeln der Informationen zur Bewertung.
 */
class Bewertung {

    private final int[] feld;
    private final Spieler winner;
    private final int iter;

    public Bewertung(Spieler winner, int row, int col, int iter) {
        this.winner = winner;
        feld = new int[]{row, col};
        this.iter = iter;
    }

    public int[] getFeld() {
        return feld;
    }

    public Spieler getWinner() {
        return winner;
    }

    public int getIter() {
        return iter;
    }

    public Bewertung root(int row, int col) {
        return new Bewertung(winner, row, col, iter);
    }

    @Override
    public String toString() {
        return  "[" + winner + ", " + Arrays.toString(feld) + ", " + iter + "]";
    }
}
