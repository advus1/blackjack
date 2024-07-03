import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class SpielerUDP {
    private String name;
    private int kapital;
    private List<KarteUDP> hand;
    private KartenzaehlerUDP kartenzaehler;

    public SpielerUDP() {
        // Standardkonstruktor für Deserialisierung
    }

    public SpielerUDP(String name, int kapital) {
        this.name = name;
        this.kapital = kapital;
        this.hand = new ArrayList<>();
        this.kartenzaehler = new KartenzaehlerUDP(); // Initialisierung des Kartenzaehlers
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

    public List<KarteUDP> getHand() {
        return hand;
    }

    public void setHand(List<KarteUDP> hand) {
        this.hand = hand;
    }

    @JsonIgnore
    public KartenzaehlerUDP getKartenzaehler() {
        return kartenzaehler;
    }

    public void setKartenzaehler(KartenzaehlerUDP kartenzaehler) {
        this.kartenzaehler = kartenzaehler;
    }
}
