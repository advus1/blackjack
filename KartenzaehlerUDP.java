import com.fasterxml.jackson.annotation.JsonIgnore;

public class KartenzaehlerUDP {
    @JsonIgnore
    private int gesamtKartenwert;

    public KartenzaehlerUDP() {
        this.gesamtKartenwert = 0;
    }

    public int getGesamtKartenwert() {
        return gesamtKartenwert;
    }

    public void setGesamtKartenwert(int gesamtKartenwert) {
        this.gesamtKartenwert = gesamtKartenwert;
    }

    public void karteGezogen(int wert) {
        gesamtKartenwert += wert;
    }

    public boolean spielerGewinntZuOft(SpielerUDP spieler) {
        return spieler.getKapital() < 0;
    }
}
