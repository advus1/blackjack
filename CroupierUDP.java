import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CroupierUDP {
    private int deckCount;
    private List<KarteUDP> deck;
    private List<KarteUDP> hand;
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;
    private ObjectMapper objectMapper;

    public CroupierUDP(int deckCount, int serverPort) {
        this.deckCount = deckCount;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.objectMapper = new ObjectMapper();

        initialisiereDeck();
        mischeDeck();

        try {
            this.socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void initialisiereDeck() {
        String[] typen = {"Pik", "Kreuz", "Herz", "Karo"};
        String[] rangen = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Bube", "Dame", "König", "Ass"};
        int[] werte = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int i = 0; i < deckCount; i++) {
            for (String typ : typen) {
                for (int j = 0; j < rangen.length; j++) {
                    deck.add(new KarteUDP(typ, rangen[j], werte[j]));
                }
            }
        }
    }

    private void mischeDeck() {
        Collections.shuffle(deck, new Random());
    }

    public void startGame() {
        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

        try {
            while (true) {
                // Receive request from client (SpielerUDP)
                socket.receive(receivePacket);
                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Deserialize request to SpielerUDP object
                SpielerUDP spieler = objectMapper.readValue(request, SpielerUDP.class);
                this.clientAddress = receivePacket.getAddress();
                this.clientPort = receivePacket.getPort();

                // Initialize game
                KartenzaehlerUDP kartenzaehler = new KartenzaehlerUDP();
                spieler.setKartenzaehler(kartenzaehler);

                // Game logic
                spieleRunde(spieler);

                // Serialize and send response back to SpielerUDP
                String response = objectMapper.writeValueAsString(spieler);
                DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spieleRunde(SpielerUDP spieler) {
        // Initial deal
        karteZiehen(hand);
        karteZiehen(hand);
        karteZiehen(spieler.getHand());
        karteZiehen(spieler.getHand());

        // Inform SpielerUDP about initial hands
        try {
            sendToSpielerUDP(spieler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // SpielerUDP spielt
        spielerZiehtKarten(spieler);

        // Croupier spielt
        while (berechneHandWert(hand) < 17) {
            karteZiehen(hand);
        }

        // Determine winner
        int spielerWert = berechneHandWert(spieler.getHand());
        int croupierWert = berechneHandWert(hand);

        if (spielerWert > 21 || (croupierWert <= 21 && croupierWert >= spielerWert)) {
            System.out.println("Croupier wins!");
        } else {
            System.out.println("Player wins!");
        }

        // Example of requesting statistics from KartenzaehlerUDP
        KartenzaehlerUDP kartenzaehler = spieler.getKartenzaehler();
        if (kartenzaehler != null && kartenzaehler.spielerGewinntZuOft(spieler)) {
            System.out.println("Player is being thrown out of the casino!");
        }
    }

    private void spielerZiehtKarten(SpielerUDP spieler) {
        try {
            while (true) {
                sendToSpielerUDP(spieler);
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String decision = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (decision.equalsIgnoreCase("Hit")) {
                    karteZiehen(spieler.getHand());
                    sendToSpielerUDP(spieler);
                    if (berechneHandWert(spieler.getHand()) > 21) {
                        sendToSpielerUDP("Bust");
                        break;
                    }
                } else if (decision.equalsIgnoreCase("Stand")) {
                    break;
                } else {
                    sendToSpielerUDP("Invalid decision");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToSpielerUDP(Object object) throws IOException {
        String response = objectMapper.writeValueAsString(object);
        DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, clientAddress, clientPort);
        socket.send(sendPacket);
    }

    private void karteZiehen(List<KarteUDP> hand) {
        KarteUDP karte = deck.remove(deck.size() - 1);
        hand.add(karte);
    }

    private int berechneHandWert(List<KarteUDP> hand) {
        int summe = 0;
        int anzahlAsse = 0;

        for (KarteUDP karte : hand) {
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

    public static void main(String[] args) {
        // Beispiel für die Verwendung des CroupierUDP
        int deckCount = 1; // Anzahl der Decks
        int serverPort = 9876; // UDP Server Port

        CroupierUDP croupier = new CroupierUDP(deckCount, serverPort);
        croupier.startGame();
    }
}
