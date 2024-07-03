import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Croupier {
    private int deckCount;
    private List<Karte> deck;
    private List<Karte> hand;
    private Kartenzaehler kartenzaehler;

    public Croupier() { }

    public Croupier(int deckCount) {
        this.deckCount = deckCount;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        initialisiereDeck();
        mischeDeck();
    }

    public int getDeckCount() {
        return deckCount;
    }

    public void setDeckCount(int deckCount) {
        this.deckCount = deckCount;
    }

    public List<Karte> getDeck() {
        return deck;
    }

    public void setDeck(List<Karte> deck) {
        this.deck = deck;
    }

    public List<Karte> getHand() {
        return hand;
    }

    public void setHand(List<Karte> hand) {
        this.hand = hand;
    }

    private void initialisiereDeck() {
        String[] typen = {"Pik", "Kreuz", "Herz", "Karo"};
        String[] rangen = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Bube", "Dame", "König", "Ass"};
        int[] werte = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int i = 0; i < deckCount; i++) {
            for (String typ : typen) {
                for (int j = 0; j < rangen.length; j++) {
                    deck.add(new Karte(typ, rangen[j], werte[j]));
                }
            }
        }
    }

    private void mischeDeck() {
        Collections.shuffle(deck, new Random());
    }

    public Karte zieheKarte() {
        return deck.remove(deck.size() - 1);
    }

    public void karteZiehen(List<Karte> hand) {
        hand.add(zieheKarte());
    }

    public int berechneHandWert(List<Karte> hand) {
        int summe = 0;
        int anzahlAsse = 0;

        for (Karte karte : hand) {
            summe += karte.getWert();
            if (karte.getRang().equals("Ass")) {
                anzahlAsse++;
            }
        }

        while (summe > 21 && anzahlAsse > 0) {
            summe -= 10;
            anzahlAsse--;
        }

        return summe;
    }

    public void spielStarten(Spieler spieler, Kartenzaehler kartenzaehler) {
        this.kartenzaehler = kartenzaehler;
        hand.clear();
        spieler.getHand().clear();

        // Initiale Karten austeilen
        karteZiehen(hand);
        karteZiehen(hand);
        karteZiehen(spieler.getHand());
        karteZiehen(spieler.getHand());

        // Spieler spielt
        spieler.spielZug(this);

        // Croupier spielt
        while (berechneHandWert(hand) < 17) {
            karteZiehen(hand);
        }

        // Gewinner berechnen
        int spielerWert = berechneHandWert(spieler.getHand());
        int croupierWert = berechneHandWert(hand);

        if (spielerWert > 21 || (croupierWert <= 21 && croupierWert >= spielerWert)) {
            System.out.println("Croupier gewinnt!");
        } else {
            System.out.println("Spieler gewinnt!");
        }

        // Beispiel für das Anfordern von Statistiken vom Kartenzaehler
        if (kartenzaehler.spielerGewinntZuOft(spieler)) {
            System.out.println("Spieler wird aus dem Casino geworfen!");
        }
    }
}
