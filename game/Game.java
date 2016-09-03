package game;

import java.util.*;

/**
 * Beinhaltet die AI-Algorithmen
 */
public class Game {

    private Spieler brett[][];
    private Spieler computer;

    public Game(Spieler computer) {
        restart(computer);
    }

    public boolean isEnded() {
        if (isWinning() != Spieler.Niemand)
            return true;

        boolean res = true;
        for (Spieler row[] : brett)
            for (Spieler s : row)
                if (s == Spieler.Niemand)
                    res = false;

        return res;
    }

    public void restart(Spieler computer) {
        brett = new Spieler[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                brett[i][j] = Spieler.Niemand;
            }
        }

        this.computer = (computer == Spieler.Niemand) ? Spieler.Kreis : computer;
    }

    public Spieler getComputer() {
        return computer;
    }

    public Spieler getHuman() {
        return computer.getOpposite();
    }

    public boolean setField(Spieler s, int i, int j) {
        if (i >= 3 || j >= 3 || i < 0 || j < 0
                || brett[i][j] != Spieler.Niemand)
            return false;

        brett[i][j] = s;
        return true;
    }

    public void unsetField(int i, int j) {
        brett[i][j] = Spieler.Niemand;
    }

    public Spieler getField(int i, int j) {
        return brett[i][j];
    }

    public Spieler[][] getBrett() {
        return brett;
    }

    public void computerZug(int maxIter) {
        Bewertung b = getBestField(computer, maxIter, 0);
        setField(computer, b.getFeld()[0], b.getFeld()[1]);
    }

    private Bewertung getBestField(Spieler spieler, int maxIter, int iter) {
        List<Bewertung> bewertungen = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (brett[i][j] == Spieler.Niemand) {
                    setField(spieler, i, j);
                    Spieler winner = isWinning();
                    Bewertung b = new Bewertung(winner, i, j, iter);

                    if (b.getWinner() == spieler) {
                        unsetField(i, j);
                        return b;
                    }
                    else if (b.getWinner() == Spieler.Niemand)
                        if (iter == maxIter-1) bewertungen.add(b);
                        else bewertungen.add(getBestField(spieler.getOpposite(), maxIter, iter+1).root(i, j));
                    else bewertungen.add(b);

                    unsetField(i, j);
                }
            }
        }

        if (bewertungen.size() == 0) return new Bewertung(Spieler.Niemand, -1, -1, iter);
        final Bewertung tempMax = bewertungen.stream().max(new SpielerComparator(spieler)).get();
        Bewertung max;
        if (tempMax.getWinner() == Spieler.Niemand) {
            max = tempMax;
        } else if (tempMax.getWinner() == spieler) {
            max = bewertungen.stream().filter(b -> b.getWinner() == tempMax.getWinner())
                    .min((a, b) -> a.getIter() - b.getIter()).get();
        } else {
            max = bewertungen.stream().max((a, b) -> a.getIter() - b.getIter()).get();
        }
        if (iter > 0) return max;

        Object gleich[] = bewertungen.stream()
                .filter(b -> b.getWinner() == max.getWinner() && b.getIter() == max.getIter()).toArray();
        return (Bewertung) gleich[(int) (gleich.length * Math.random())];
    }

    public Spieler isWinning() {
        for (int i = 0; i < 3; i++ ) {
            Spieler s = brett[i][i];
            if (s == Spieler.Niemand) continue;

            int j;
            // Reihe überprüfen.
            for (j = 0; j < 3; j++)
                if (brett[i][j] != s) break;
            if (j == 3) return s;

            // Spalte überprüfen.
            for (j = 0; j < 3; j++)
                if (brett[j][i] != s) break;
            if (j == 3) return s;

            // Diagonalen überprüfen
            if (i == 1) {
                for (j = 0; j < 3; j++)
                    if (brett[j][j] != s) break;
                if (j == 3) return s;

                for (j = 0; j < 3; j++)
                    if (brett[2-j][j] != s) break;
                if (j == 3) return s;
            }
        }

        return Spieler.Niemand;
    }

    class SpielerComparator implements Comparator<Bewertung> {

        private final Spieler reference;

        public SpielerComparator(Spieler reference) {
            this.reference = reference;
        }

        private int calc(Bewertung b) {
            if (b.getWinner() == Spieler.Niemand)
                return 0;
            else if (b.getWinner() == reference)
                return 1;
            else
                return -1;
        }

        @Override
        public int compare(Bewertung o1, Bewertung o2) {
            return calc(o1) - calc(o2);
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(brett[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
