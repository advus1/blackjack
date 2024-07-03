# Übung 5 - David Ritter

## Aufgabe 1: Blackjack

Stand: Funktionalität hergestellt
To-Do: „Split“, „Double Down“, „Surrender“ implementieren

## Aufgabe 2: UDP

_Auf welche Dinge müssen Sie achten, um sicherzustellen, dass die gesendeten Nachrichten beim Empfänger ankommen?_

1. Empfänger muss empfangsbereit sein, also über eine aktive UDP-Socketverbindung verfügen
2. Eventuell muss auch auf MTU geachtet werden damit durch Fragmentierung keine Paketverluste entstehen
3. Da UDP verwendet wird, wäre ein stabiles Netzwerk wünschenswert, also im Zweifel ggf auf WLAN verzichten.
4. Ein Bestätigungsmechanismus, also ein Art selbst implementierter Handshake bzw Acknowledgement könnte helfen um den Datenfluss zu gewährleisten
5. Timeout könnte auch von Interesse sein, falls man Pakete so lange senden will, bis die Ankunft des Paketes bestätigt wurde
6. Durchzählen der Nachrichten könnte ebenfalls ein Mechanismus sein um Verluste zu bemerken
7. Je nach Netz, bspw Uni-WLAN, könnten die Nachrichten blockieren.

_Wie können Sie Fehlkommunikation vermeiden (wenn z.B. ein Paket falsch
interpretiert wird)._

1. Klare Nachrichtenformate definieren könnte helfen
2. Metadaten könnten in den Header oder in Präambeln transportiert werden
3. Checksum zur Fehlererkennung
4. Empfangsbestätigung könnte hilfreich sein
5. Fehler Exceptions und klare Fehlercodes erstellen

_Wie können Sie sicherstellen, dass Ihr Programm mit den 2 anderen Programmen
kommunizieren kann und die Pakete so interpretiert, wie Sie das geplant haben?_

1. Verwendung von einheitlicher Nachrichten und Dokumentation der Vereinheitlichung
2. Versionen klar protokollieren und Kompatibilität zu älteren Versionen gewährleisten

## Aufgabe 3: Implementierung

Hier bin ich leider auf größere Probleme gestoßen.

Was aber gelungen ist:
1. Anlegung von /lib für Verwendung des Serialisierers Jackson
2. Erstellen der CroupierUDP
3. Erstellen einer Fatjar um ein eigenständiges Programm zu erhalten

Was nicht gelungen ist:
1. Die CroupierUDP.java mit der BlackjackUDP.java kommunizieren zu lassen.
2. Die FatJar lauffähig hinzubekommen

Ich habe verschiedene Ansätze gewählt, habe versucht den Spieler und den Kartenzähler noch zu implementieren um zu testen, ob es damit dann funktioniert, aber auch das ist leider fehlgeschlagen. Außerdem habe ich alles nochmals from scratch aufgebaut, bin aber wieder in die gleichen Fehler gelaufen, obwohl ich aktiv versucht habe einen anderen Ansatz zu erzeugen.

## Aufgabe 4: Kommunikation

Hier habe ich versucht mir die Abgaben meiner Kommilitonen genauer anzuschauen um zu verstehen, was bei mir falsch war, bin aber ehrlich gesagt als Mathestudent mit meinem Java-Latein am Ende. Die Funktionalität konnte ich also leider auch nicht wirklich testen.   