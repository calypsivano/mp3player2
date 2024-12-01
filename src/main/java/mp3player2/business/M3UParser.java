package mp3player2.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class M3UParser {

    public static Playlist loadPlaylistFromM3U(String filePath) {
        Playlist playlist = new Playlist("Loaded Playlist");
        File m3uFile = new File(filePath);
        String parentDir = m3uFile.getParent(); // Verzeichnis der M3U-Datei
    
        try (BufferedReader br = new BufferedReader(new FileReader(m3uFile))) {
            String line;
            String title = null;
            String artist = null;
            String coverPath = null;
            int trackLength = 0;
    
            while ((line = br.readLine()) != null) {
                line = line.trim();
    
                if (line.startsWith("#EXTINF")) {
                    String[] info = line.split(",", 2);
                    if (info.length > 1) {
                        // Extrahiere die Länge und den Titel
                        String[] details = info[1].split(" - ");
                        artist = details.length > 1 ? details[0].trim() : "Unknown Artist";
                        title = details[details.length - 1].trim();

                        // Extrahiere die Länge des Tracks
                        trackLength = Integer.parseInt(info[0].substring(8)); // Die Zahl nach #EXTINF:
                    }
                } else if (line.startsWith("#COVER:")) {
                    coverPath = line.substring(7).trim(); // Relativer Pfad aus der M3U-Datei
                } else if (!line.startsWith("#") && !line.isEmpty()) {
                    // Verarbeite den Track
                    File trackFile = new File(parentDir, line);
                    String relativeCoverPath = coverPath; // Relativ belassen, direkt verwendbar
    
                    // Track initialisieren und hinzufügen
                    Track track = new Track(
                        title != null ? title : trackFile.getName(),
                        artist,
                        trackFile.getAbsolutePath(),
                        relativeCoverPath, // Relativer Pfad wird direkt gespeichert
                        trackLength
                    );
                    
                    //TEST
                    System.out.println("Track erstellt: " + track.getTitle() + ", Cover: " + track.getCoverPath()+ ", Länge: " + track.getLength());
    
                    playlist.addTrack(track);
    
                    // Variablen zurücksetzen
                    title = null;
                    artist = null;
                    coverPath = null;
                    trackLength = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playlist;
    }      
    
}
