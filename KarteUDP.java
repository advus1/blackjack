public class KarteUDP {
    private String typ;
    private String rang;
    private int wert;

    public KarteUDP() { }

    public KarteUDP(String typ, String rang, int wert) {
        this.typ = typ;
        this.rang = rang;
        this.wert = wert;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

    @Override
    public String toString() {
        return rang + " of " + typ;
    }
}
