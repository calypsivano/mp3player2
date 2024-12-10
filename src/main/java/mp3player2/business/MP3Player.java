/*Beinhaltet die Logik und verwendet zum eigentlichen Abspielen den Minim-Player
*/


package mp3player2.business;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MP3Player {
    private Playlist currentPlaylist;
    private Track currentTrack;
    private SimpleAudioPlayer audioPlayer;
    private SimpleMinim minim;
    private boolean isShuffle;
    private float volume = 0.5f; // Standardlautstärke von 50%
    private int currentPosition; // Merkt sich die Wiedergabeposition in Millisekunden
    private List<TrackChangeListener> listeners = new ArrayList<>(); //Um zu Tracken wenn Lieder gewechselt werden
    private boolean isMonitoring = false;

    public MP3Player(SimpleMinim minim) {

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

        this.isShuffle = false;
        this.minim = new SimpleMinim(true);

        // Playlist in den Player laden
        this.loadPlaylist(playlist);
    }

    // Startet die Wiedergabe des nächsten Tracks
    public void playNext() {
        if (currentPlaylist != null && currentTrack != null) {
            int currentIndex = currentPlaylist.getTracks().indexOf(currentTrack);
            if (currentIndex + 1 < currentPlaylist.getTracks().size()) {
                Track nextTrack = currentPlaylist.getTracks().get(currentIndex + 1);
                loadTrack(nextTrack);
                notifyTrackChange(nextTrack);
                currentPosition = 0;
                play();
            }
        }
    }

    public void loadPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        if (!playlist.getTracks().isEmpty()) {
            this.currentTrack = playlist.getTracks().get(0);
            loadTrack(currentTrack); // Track laden und abspielen vorbereiten
        }
    }

    public void play() {
        if (currentTrack != null) {
            audioPlayer.play();
            audioPlayer.play(currentPosition); // Springt zur gespeicherten Position
            
        }
        // Überwacht die Position des Tracks alle 500ms
        new Thread(this::monitorTrackProgress).start();  // Hintergrund-Thread starten
    }

    public void pause() {
        if (audioPlayer != null) {
            audioPlayer.pause();
            currentPosition = audioPlayer.position(); // Speichert aktuelle Position
        }
    }

    public void setVolume(float value) {
        if (audioPlayer != null) {
            if (value <= 0) {
                audioPlayer.mute();
            } else {
                audioPlayer.unmute();
                float valuedB = (float) (10*Math.log(value)); //Formel für dezibel i am told
                audioPlayer.setGain(valuedB); 
            }
        }
    }

    public void nextTrack() {
        if (audioPlayer != null) {
            audioPlayer.pause();    //Damit nicht zwei Tracks gleichzeitig gespielt werden ._.'
            currentPosition = 0;
        }
        if (isShuffle) {
            playRandomTrack();
        } else {
            int nextIndex = currentPlaylist.getTracks().indexOf(currentTrack) + 1;
            if (nextIndex < currentPlaylist.getTracks().size()) {
                currentTrack = currentPlaylist.getTracks().get(nextIndex);
                loadTrack(currentTrack);
                play();
            }
        }
    }

    public void previousTrack() {
        if (audioPlayer != null) {
            audioPlayer.pause();    //Damit nicht zwei Tracks gleichzeitig gespielt werden ._.'
            currentPosition = 0;
        }
        int previousIndex = currentPlaylist.getTracks().indexOf(currentTrack) - 1;
        if (previousIndex >= 0) {
            currentTrack = currentPlaylist.getTracks().get(previousIndex);
            loadTrack(currentTrack);
            play();
        }
    }

    public void toggleShuffle() {
        isShuffle = !isShuffle;
    }

    //Random Funktion ist noch nicht optimal, könnte ein Array setzen mit den Tracks und jedem eine random Zahl geben ohne Dopplungen
    private void playRandomTrack() {
        Random random = new Random();
        int randomIndex = random.nextInt(currentPlaylist.getTracks().size());
        currentTrack = currentPlaylist.getTracks().get(randomIndex);
        loadTrack(currentTrack);
        play();
    }

    // Angepasste Methode zum Laden des aktuellen Tracks mit "minim.loadMP3File"
    public void loadTrack(Track track) {
        if (track != null) {
            this.audioPlayer = minim.loadMP3File(track.getFilePath());
            audioPlayer.setVolume(volume);
            currentTrack = track;
            monitorTrackProgress(); 
            notifyTrackChange(track);
        }
    }
    

    public String getCurrentTrackInfo() {
        if (currentTrack != null) {
            return currentTrack.getArtist() + " - " + currentTrack.getTitle();
        }
        return "Kein Track geladen.";
    }

    public float getCurrentVolume() {
        return audioPlayer != null ? audioPlayer.getGain() / 100 : 0.5f; // Standardwert 50%
    }    

    // Überwacht den Fortschritt des Tracks und spielt den nächsten ab, wenn der aktuelle zu Ende ist
    public void monitorTrackProgress() {
        if (isMonitoring) return;

        isMonitoring = true;
        new Thread(() -> {
            while (audioPlayer.isPlaying()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(getCurrentPosition() + " // " + currentTrack.getLength());
                if ((getCurrentPosition()/1000) >= currentTrack.getLength()) {
                    playNext();
                    break; // Thread beenden
                }
            }
            isMonitoring = false;
        }).start();
    }    
    

    //Bei Änderungen des Songs soll Präsentationsebene informiert werden
    public void addTrackChangeListener(TrackChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyTrackChange(Track newTrack) {
        for (TrackChangeListener listener : listeners) {
            listener.onTrackChanged(newTrack);
        }
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public int getTrackLength() {
        return currentTrack != null ? currentTrack.getLength() : 0;
    }
    

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public int getCurrentPosition() {
        return audioPlayer != null ? audioPlayer.position() : 0;
    }

    public void setCurrentPosition(int position) {
        if (audioPlayer != null) {
            audioPlayer.cue(position); // Setzt die Wiedergabe an die neue Position
        }
    }
    
    public boolean isPlaying() {
        return audioPlayer != null && audioPlayer.isPlaying();
    }

}
