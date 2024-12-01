package mp3player2.presentation.UIComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ControlView extends HBox{
    
    Button playButton;
    Button pauseButton;
    Button nextButton;
    Button prevButton;
    Button shuffleButton;
    Button volumeButton;
    Slider volumeSlider;

    public ControlView() {
        
        playButton = new Button();
        playButton.getStyleClass().addAll("icon-button");

        pauseButton = new Button();
        pauseButton.getStyleClass().addAll("icon-button");

        nextButton = new Button();
        nextButton.getStyleClass().addAll("icon-button");
        nextButton.setId("next-button");

        prevButton = new Button();
        prevButton.getStyleClass().addAll("icon-button");
        prevButton.setId("prev-button");

        shuffleButton = new Button();
        shuffleButton.getStyleClass().addAll("icon-button");
        shuffleButton.setId("shuffle-button");

        volumeButton = new Button();
        volumeButton.getStyleClass().addAll("icon-button");
        volumeButton.setId("volume-button");

        volumeSlider = new Slider(0, 100, 50); 
        volumeSlider.setMajorTickUnit(25); 
        volumeSlider.setMinorTickCount(0); 
        volumeSlider.setShowTickLabels(true); 
        volumeSlider.setShowTickMarks(true); 
        volumeSlider.setPrefWidth(160); 

        prevButton.getStyleClass().addAll("button");
        nextButton.getStyleClass().addAll("button");
        shuffleButton.getStyleClass().addAll("button");
        volumeButton.getStyleClass().addAll("button");

        this.getChildren().addAll(shuffleButton, prevButton, playButton, nextButton, volumeButton,volumeSlider);

        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,20, 20, 20));
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    public Button getPlayButton() {
        return playButton;
    }
    public Button getPauseButton() {
        return pauseButton;
    }
    public Button getPrevButton() {
        return prevButton;
    }
    public Button getShuffleButton() {
        return shuffleButton;
    }
    public Button getNextButton() {
        return nextButton;
    }
    public Button getVolumeButton() {
        return volumeButton;
    }
    public Slider getVolumeSlider() {
        return volumeSlider;
    }
}
