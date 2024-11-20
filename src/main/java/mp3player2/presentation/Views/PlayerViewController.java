package mp3player2.presentation.Views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        coverImageView = new ImageView();
        coverImageView.setFitWidth(400);
        coverImageView.setFitHeight(400);
        coverImageView.setPreserveRatio(true);
        coverImageView.setStyle( "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 4, 0, 0, 4), " + "dropshadow(gaussian, rgba(0, 0, 0, 0.19), 6, 0, 0, 6);");
        //SetDefault
        Image coverImage = new Image("file:assets/geto.jpg");
        coverImageView.setImage(coverImage);

        imageContainer.getChildren().add(coverImageView);

        // Layout erweitern
        VBox layout = new VBox(10, imageContainer, progressSlider, rootView.controlView);
        layout.setAlignment(Pos.CENTER);
        rootView.setCenter(layout);


        // Play-Button Aktion
        Button playButton = rootView.controlView.getPlayButton();
        playButton.setOnAction(event -> play());
        rootView.controlView.getPlayButton().setOnAction(event -> play());

        // Update des Sliders
        updateProgressSlider();
    }

    private void play() {
        if (player.getCurrentTrack() == null) {
            Track track = player.getCurrentPlaylist().getTracks().get(0); // Ersten Track laden
            player.loadTrack(track);
            /*updateCoverImage(track);*/
        }
        player.play();
    }

    private void updateCoverImage(Track track) {
        String coverPath = track.getCoverPath();
        if (coverPath != null) {
            Image coverImage = new Image("file:" + coverPath);
            coverImageView.setImage(coverImage);
        } else {
            coverImageView.setImage(null);
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
