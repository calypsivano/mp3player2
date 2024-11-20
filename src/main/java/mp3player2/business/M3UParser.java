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

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("#EXTINF")) {
                    String[] info = line.split(",", 2);
                    if (info.length > 1) {
                        String[] details = info[1].split(" - ");
                        artist = details.length > 1 ? details[0].trim() : "Unknown Artist";
                        title = details[details.length - 1].trim();
                    }
                } else if (!line.startsWith("#") && !line.isEmpty()) {
                    // Verwende den vollständigen Pfad relativ zur M3U-Datei
                    File trackFile = new File(parentDir, line);
                    Track track = new Track(title != null ? title : trackFile.getName(), artist, trackFile.getAbsolutePath());
                    playlist.addTrack(track);
                    title = null;
                    artist = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playlist;
    }
}
