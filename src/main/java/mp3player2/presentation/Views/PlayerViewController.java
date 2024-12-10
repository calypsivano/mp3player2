package mp3player2.presentation.Views;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import mp3player2.business.MP3Player;
import mp3player2.business.Track;
import mp3player2.presentation.UIComponents.ControlView;
import mp3player2.business.*;

public class PlayerViewController {
    private PlayerView rootView;
    private ControlView controlView;
    //private Button playButton;
    private ImageView coverImageView;
    private Slider progressSlider;
    private Label songInformation;
    //Buttons
    Button playButton;
    Button pauseButton;
    Button prevButton;
    Button nextButton;
    Button shuffleButton;

    //vielleicht gibt es hier eine schönere Lösung:
    private boolean isPlaying = false;

    private MP3Player player;

    public PlayerViewController(MP3Player player) {
        this.rootView = new PlayerView();
        this.player = player;
        controlView = rootView.controlView;
        playButton = rootView.controlView.getPlayButton();
        pauseButton = rootView.controlView.getPauseButton();
        prevButton = rootView.controlView.getPrevButton();
        nextButton = rootView.controlView.getNextButton();
        shuffleButton = rootView.controlView.getShuffleButton();

        playButton.setId("play-button");
        pauseButton.setId("pause-button");

        initialize();
    }

    public void initialize() {
        // Fortschritt-Slider
        progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setValue(0);
        progressSlider.maxWidth(500);
        progressSlider.setDisable(false);
        progressSlider.setStyle("-fx-padding: 20px 10px;");

        //Fortschritt
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressSlider.isValueChanging()) { // Benutzerinteraktion aktiv
                double newTime = (newValue.doubleValue() / 100) * player.getTrackLength(); // Neue Position berechnen
                player.setCurrentPosition((int) (newTime * 1000)); // Position in Millisekunden setzen
                player.monitorTrackProgress();
            }
        });          

        // Cover-Bild
        StackPane imageContainer = new StackPane();

        coverImageView = new ImageView("file:assets/geto.jpg");
        coverImageView.setFitWidth(400);
        coverImageView.setFitHeight(400);

        coverImageView.setPreserveRatio(true);
        coverImageView.setStyle( "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 4, 0, 0, 4), " + "dropshadow(gaussian, rgba(0, 0, 0, 0.19), 6, 0, 0, 6);");

        imageContainer.getChildren().add(coverImageView);

        songInformation = new Label("Press Play to enjoy Music <:");
        songInformation.setFont(new Font("Arial", 16));
        songInformation.setStyle("-fx-text-fill: white;");
        songInformation.setTextAlignment(TextAlignment.CENTER);
        VBox.setVgrow(songInformation, Priority.ALWAYS);
        VBox.setVgrow(coverImageView, Priority.ALWAYS);

        // Layout erweitern
        VBox infoBox = new VBox(10, songInformation, progressSlider);
        infoBox.setAlignment(Pos.CENTER);
        //Stylessss
        infoBox.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        VBox layout = new VBox(20, imageContainer, infoBox, rootView.controlView);
        layout.setAlignment(Pos.CENTER);
        rootView.setCenter(layout);


        // Play-Button Aktion + Pause
        playButton.setOnAction(event -> {
            if (isPlaying) {
                playButton.setId("play-button");
                pause();
                isPlaying = false;
            } else {
                playButton.setId("pause-button");
                play();
                isPlaying = true;
            }
        });

        // Next-Button Aktion
        nextButton.setOnAction(event -> nextTrack());
        rootView.controlView.getNextButton().setOnAction(event -> nextTrack());

        // Prev-Button Aktion
        prevButton.setOnAction(event -> previousTrack());
        rootView.controlView.getPrevButton().setOnAction(event -> previousTrack());

        // Shuffle-Button Aktion
        shuffleButton.setOnAction(event -> toggleShuffle());
        rootView.controlView.getShuffleButton().setOnAction(event -> toggleShuffle());

        // Update des Sliders
        updateProgressSlider();

        //Hört ob sich Lieder ändern, um entprechen View anzupassen
        registerTrackChangeListener();

        //Volumebar
        updateVolumeSlider();

    }

    private void play() {
        // Erster Track laden, falls keiner aktiv ist
        if (player.getCurrentTrack() == null) {
            Track track = player.getCurrentPlaylist().getTracks().get(0); // Ersten Track laden
            player.loadTrack(track);
            updateCoverImage(track);
            updateSongInformation(track);
            progressSlider.setValue(0); // Slider zurücksetzen
        }
    
        player.play();
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
        progressSlider.setValue(0);
    
        // Aktualisierung starten
        updateProgressSlider();
    }

    private void pause() {
        player.pause();
        isPlaying = false;
    }

    private void nextTrack() {
        player.nextTrack();
        if (!isPlaying) {
            playButton.setId("pause-button");
            isPlaying = true;
        }
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
        progressSlider.setValue(0);
        updateProgressSlider();
    }
    
    private void previousTrack() {
        player.previousTrack();
        if (!isPlaying) {
            playButton.setId("pause-button");
            isPlaying = true;
        }
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
        progressSlider.setValue(0);
        updateProgressSlider();
    }

    private void toggleShuffle() {
        player.toggleShuffle();
    }

    private void updateSongInformation(Track track) {
        songInformation.setText(track.getTitle() + " – " + track.getArtist());
    }

    private void updateCoverImage(Track track) {
        String coverPath = track.getCoverPath();
        System.out.println("CoverPath: " + coverPath);
        if (coverPath != null) {
            File coverFile = new File(coverPath);
            System.out.println("Existiert Datei? " + coverFile.exists());
            Image coverImage = new Image("file:" + coverPath);
            coverImageView.setImage(coverImage);
        } else {
            System.out.println("Kein Cover-Bild gefunden!");
            Image coverImage = new Image("file:assets/geto.jpg");
            coverImageView.setImage(coverImage);
        }
    }

    private void updateProgressSlider() {
        new Thread(() -> {
            while (player.isPlaying()) {
                if (!progressSlider.isValueChanging()) { // Nur aktualisieren, wenn Benutzer nicht interagiert
                    Platform.runLater(() -> {
                        double currentPosition = player.getCurrentPosition() / 1000.0; // Sekunden
                        double totalLength = player.getCurrentTrack().getLength(); // Sekunden
                        //System.out.println("currentPosition: " + currentPosition + "totalLength: " + totalLength);
                        if (totalLength > 0) {
                            progressSlider.setValue((currentPosition / totalLength) * 100); // Prozentualer Fortschritt
                        }
                    });
                }
    
                try {
                    Thread.sleep(500); // Aktualisierung alle 500ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Falls Track geändert wird, ändern wir die View:
    private void registerTrackChangeListener() {
        player.addTrackChangeListener(newTrack -> {
            Platform.runLater(() -> {
                updateCoverImage(newTrack);
                updateSongInformation(newTrack);
                progressSlider.setValue(0); // Slider zurücksetzen
                updateProgressSlider();
            });
        });
    }

     private void updateVolumeSlider() {
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    double currentVolume = controlView.volumeSlider.getValue(); // Slider-Wert abfragen
                    player.setVolume((float) (currentVolume / 100)); // Lautstärke anpassen
                });
    
                try {
                    Thread.sleep(500); // Aktualisierung alle 500ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    public Pane getRootView() {
        return rootView;
    }
}
