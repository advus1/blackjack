import java.util.HashMap;
import java.util.Map;

public class Kartenzaehler {
    private Map<Spieler, Integer> spielerGewinne;

    public Kartenzaehler() {
        this.spielerGewinne = new HashMap<>();
    }

    public void registriereSpiel(Spieler spieler, boolean hatGewonnen) {
        spielerGewinne.put(spieler, spielerGewinne.getOrDefault(spieler, 0) + (hatGewonnen ? 1 : 0));
    }

    public boolean spielerGewinntZuOft(Spieler spieler) {
        return spielerGewinne.getOrDefault(spieler, 0) > 5;
    }
}
