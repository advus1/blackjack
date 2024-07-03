import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Spieler {
    private String name;
    private int kapital;
    private List<Karte> hand;

    public Spieler() { }

    public Spieler(String name, int kapital) {
        this.name = name;
        this.kapital = kapital;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKapital() {
        return kapital;
    }

    public void setKapital(int kapital) {
        this.kapital = kapital;
    }

    public List<Karte> getHand() {
        return hand;
    }

    public void setHand(List<Karte> hand) {
        this.hand = hand;
    }

    public void spielZug(Croupier croupier) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Ihre Hand: " + handToString() + " (Wert: " + croupier.berechneHandWert(hand) + ")");
            System.out.println("Möchten Sie 'Hit' oder 'Stand'?");
            String entscheidung = scanner.nextLine();

            if (entscheidung.equalsIgnoreCase("Hit")) {
                croupier.karteZiehen(hand);
                if (croupier.berechneHandWert(hand) > 21) {
                    System.out.println("Sie haben über 21! Ihr Wert ist: " + croupier.berechneHandWert(hand));
                    break;
                }
            } else if (entscheidung.equalsIgnoreCase("Stand")) {
                break;
            } else {
                System.out.println("Ungültige Eingabe. Bitte 'Hit' oder 'Stand' eingeben.");
            }
        }
    }

    private String handToString() {
        StringBuilder sb = new StringBuilder();
        for (Karte karte : hand) {
            sb.append(karte.toString()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
