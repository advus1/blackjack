import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlackjackUDP {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Initialisierung des Spiels
        int deckCount = 1; // Anzahl der Decks
        int serverPort = 9876; // UDP Server Port

        CroupierUDP croupier = new CroupierUDP(deckCount, serverPort);
        SpielerUDP spieler = new SpielerUDP("Alice", 1000);
        KartenzaehlerUDP kartenzaehler = new KartenzaehlerUDP();

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
            SpielerUDP deserializedSpieler = objectMapper.readValue(spielerJsonString, SpielerUDP.class);
            System.out.println("Spieler Name: " + deserializedSpieler.getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Spiel starten
        croupier.startGame();
    }
}
