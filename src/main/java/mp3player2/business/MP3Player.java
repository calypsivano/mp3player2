/*Beinhaltet die Logik und verwendet zum eigentlichen Abspielen den Minim-Player
*/


package mp3player2.business;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
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

    public MP3Player(SimpleMinim minim) {
        this.isShuffle = false;
        this.minim = new SimpleMinim(true);
    }

    public void loadPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        if (!playlist.getTracks().isEmpty()) {
            this.currentTrack = playlist.getTracks().get(0);
            loadTrack(currentTrack); // Track laden und abspielen vorbereiten
        }
    }

    public void playCurrentTrack() {
        if (currentTrack != null) {
            audioPlayer.play();
            audioPlayer.play(currentPosition); // Springt zur gespeicherten Position
        }
    }

    public void pause() {
        if (audioPlayer != null) {
            audioPlayer.pause();
            currentPosition = audioPlayer.position(); // Speichert aktuelle Position
        }
    }

    public void setVolume(float value) {
        if (audioPlayer != null) {
            float valuedB = (float) (10*Math.log(value)); //Formel für dezibel i am told
            audioPlayer.setGain(valuedB); 
            System.out.println("Volume beträgt " + valuedB + "dB. Glaube ich.");
        }
    }

    public void nextTrack() {
        if (audioPlayer != null) {
            audioPlayer.pause();    //Damit nicht zwei Tracks gleichzeitig gespielt werden ._.'
        }
        if (isShuffle) {
            playRandomTrack();
        } else {
            int nextIndex = currentPlaylist.getTracks().indexOf(currentTrack) + 1;
            if (nextIndex < currentPlaylist.getTracks().size()) {
                currentTrack = currentPlaylist.getTracks().get(nextIndex);
                loadTrack(currentTrack);
                playCurrentTrack();
            }
        }
    }

    public void previousTrack() {
        if (audioPlayer != null) {
            audioPlayer.pause();    //Damit nicht zwei Tracks gleichzeitig gespielt werden ._.'
        }
        int previousIndex = currentPlaylist.getTracks().indexOf(currentTrack) - 1;
        if (previousIndex >= 0) {
            currentTrack = currentPlaylist.getTracks().get(previousIndex);
            loadTrack(currentTrack);
            playCurrentTrack();
        }
    }

    public void toggleShuffle() {
        isShuffle = !isShuffle;
    }

    private void playRandomTrack() {
        Random random = new Random();
        int randomIndex = random.nextInt(currentPlaylist.getTracks().size());
        currentTrack = currentPlaylist.getTracks().get(randomIndex);
        loadTrack(currentTrack);
        playCurrentTrack();
    }

    // Angepasste Methode zum Laden des aktuellen Tracks mit "minim.loadMP3File"
    private void loadTrack(Track track) {
        if (track != null) {
            this.audioPlayer = minim.loadMP3File(track.getFilePath()); // Lädt den Track über minim
            audioPlayer.setVolume(volume);
        }
    }

    public String getCurrentTrackInfo() {
        if (currentTrack != null) {
            return currentTrack.getArtist() + " - " + currentTrack.getTitle();
        }
        return "Kein Track geladen.";
    }

    public boolean isShuffle() {
        return isShuffle;
    }
}
