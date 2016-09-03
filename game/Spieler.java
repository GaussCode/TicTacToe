package game;

/**
 * Listet mögliche Besetzungen eines Feldes bzw.
 * mögliche Gewinner.
 */
public enum Spieler {
    Kreuz, Kreis, Niemand;

    @Override
    public String toString() {
        switch (this) {
            case Kreuz: return "X";
            case Kreis: return "0";
            default: return "-";
        }
    }

    public Spieler getOpposite() {
        switch (this) {
            case Kreuz: return Kreis;
            case Kreis: return Kreuz;
            default: return Niemand;
        }
    }
}
