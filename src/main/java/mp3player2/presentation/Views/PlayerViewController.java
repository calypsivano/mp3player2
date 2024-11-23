package mp3player2.presentation.Views;

import java.io.File;

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

    private MP3Player player;

    public PlayerViewController(MP3Player player) {
        this.rootView = new PlayerView();
        this.player = player;

        //this.controlView = rootView.controlView;

        initialize();
    }

    public void initialize() {
        // Fortschritt-Slider
        progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setValue(0);
        progressSlider.setDisable(false);
        progressSlider.setStyle("-fx-padding: 20px 10px;");

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
        VBox layout = new VBox(20, imageContainer, infoBox, rootView.controlView);
        layout.setAlignment(Pos.CENTER);
        rootView.setCenter(layout);


        // Play-Button Aktion
        Button playButton = rootView.controlView.getPlayButton();
        playButton.setOnAction(event -> play());
        rootView.controlView.getPlayButton().setOnAction(event -> play());

        // Next-Button Aktion
        Button nextButton = rootView.controlView.getNextButton();
        nextButton.setOnAction(event -> nextTrack());
        rootView.controlView.getNextButton().setOnAction(event -> nextTrack());

        // Prev-Button Aktion
        Button prevButton = rootView.controlView.getPrevButton();
        prevButton.setOnAction(event -> previousTrack());
        rootView.controlView.getPrevButton().setOnAction(event -> previousTrack());

        // Shuffle-Button Aktion
        Button shuffleButton = rootView.controlView.getShuffleButton();
        shuffleButton.setOnAction(event -> toggleShuffle());
        rootView.controlView.getShuffleButton().setOnAction(event -> toggleShuffle());

        // Update des Sliders
        updateProgressSlider();
    }

    private void play() {
        //Hier sollte er aber nicht reinspringen, das nur als Default, falls keine Playliste!
        if (player.getCurrentTrack() == null) {
            Track track = player.getCurrentPlaylist().getTracks().get(0); // Ersten Track laden
            player.loadTrack(track);
            updateCoverImage(track);
            updateSongInformation(track);
        }
        player.play();
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
    }

    private void nextTrack() {
        player.nextTrack();
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
    }
    
    private void previousTrack() {
        player.previousTrack();
        updateCoverImage(player.getCurrentTrack());
        updateSongInformation(player.getCurrentTrack());
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
                double progress = (double) player.getCurrentPosition() / player.getTrackLength() * 100;
                progressSlider.setValue(progress);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Pane getRootView() {
        return rootView;
    }
}
