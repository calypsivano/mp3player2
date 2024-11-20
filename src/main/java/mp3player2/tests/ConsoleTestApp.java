package mp3player2.tests;

import mp3player2.business.*;

import java.util.Scanner;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class ConsoleTestApp {

    public static void main(String[] args) {
        // Playlist aus einer M3U-Datei laden (Pfad zur Datei anpassen)
        Playlist playlist = M3UParser.loadPlaylistFromM3U("/Users/lauramonacolorente/Desktop/studium/EIA/mp3player2/src/main/java/mp3player2/playlist.m3u");

        if (playlist.getTracks().isEmpty()) {
            System.out.println("Fehler: Keine Tracks in der Playlist geladen.");
        } else {
            System.out.println("Tracks in der Playlist geladen:");
            for (Track track : playlist.getTracks()) {
                System.out.println(track.getTitle() + " - " + track.getFilePath());
            }
        }
    
        
        // MinimWrapper und MP3Player initialisieren
        SimpleMinim minim = new SimpleMinim();
        MP3Player player = new MP3Player(minim);
        
        // Playlist in den Player laden
        player.loadPlaylist(playlist);
        
        // Konsole-Eingaben für Benutzersteuerung
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("MP3-Player Konsole Test");
        System.out.println("Verfügbare Befehle: play, pause, next, prev, shuffle, volume [0.0-1.0], quit");

        while (true) {
            System.out.print("Befehl eingeben: ");
            command = scanner.nextLine().trim();

            switch (command.toLowerCase()) {
                case "play":
                    player.playCurrentTrack();
                    System.out.println("Wiedergabe gestartet: " + player.getCurrentTrackInfo());
                    break;

                case "pause":
                    player.pause();
                    System.out.println("Wiedergabe pausiert.");
                    break;

                case "next":
                    player.nextTrack();
                    System.out.println("Nächster Track: " + player.getCurrentTrackInfo());
                    break;

                case "prev":
                    player.previousTrack();
                    System.out.println("Vorheriger Track: " + player.getCurrentTrackInfo());
                    break;

                case "shuffle":
                    player.toggleShuffle();
                    System.out.println("Shuffle-Modus: " + (player.isShuffle() ? "Aktiviert" : "Deaktiviert"));
                    break;

                case "volume":
                    System.out.print("Lautstärke eingeben (0.0 - 1.0): ");
                    try {
                        float volume = Float.parseFloat(scanner.nextLine().trim());
                        if (volume < 0.0 || volume > 1.0) {
                            System.out.println("Ungültiger Wert. Bitte einen Wert zwischen 0.0 und 1.0 eingeben.");
                        } else {
                            player.setVolume(volume);
                            System.out.println("Lautstärke gesetzt auf: " + volume);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen 0.0 und 1.0 eingeben.");
                    }
                    break;

                case "quit":
                    System.out.println("Beende den MP3-Player.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Unbekannter Befehl. Verfügbare Befehle: play, pause, next, prev, shuffle, volume, quit");
                    break;
            }
        }
    }
}
