import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Blackjack {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Initialisierung des Spiels
        Croupier croupier = new Croupier(1);
        Spieler spieler = new Spieler("Alice", 1000);
        Kartenzaehler kartenzaehler = new Kartenzaehler();

        // Serialisierung eines Spielers
        try {
            String spielerJson = objectMapper.writeValueAsString(spieler);
            System.out.println("Spieler JSON: " + spielerJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Deserialisierung eines Spielers
        String spielerJsonString = "{\"name\":\"Alice\",\"kapital\":1000,\"hand\":[]}";
        try {
            Spieler deserializedSpieler = objectMapper.readValue(spielerJsonString, Spieler.class);
            System.out.println("Spieler Name: " + deserializedSpieler.getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Spiel starten
        croupier.spielStarten(spieler, kartenzaehler);
    }
}
