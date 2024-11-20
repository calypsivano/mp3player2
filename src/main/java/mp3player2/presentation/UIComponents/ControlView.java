package mp3player2.presentation.UIComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ControlView extends HBox{
    
    Button playButton;
    Button nextButton;
    Button prevButton;
    Button shuffleButton;
    Button repeatButton;

    public ControlView() {
        
        playButton = new Button();
        playButton.getStyleClass().addAll("icon-button");
        playButton.setId("play-button");

        nextButton = new Button();
        nextButton.getStyleClass().addAll("icon-button");
        nextButton.setId("next-button");

        prevButton = new Button();
        prevButton.getStyleClass().addAll("icon-button");
        prevButton.setId("prev-button");

        shuffleButton = new Button();
        shuffleButton.getStyleClass().addAll("icon-button");
        shuffleButton.setId("shuffle-button");

        repeatButton = new Button();
        repeatButton.getStyleClass().addAll("icon-button");
        repeatButton.setId("repeat-button");

        prevButton.getStyleClass().addAll("button");
        nextButton.getStyleClass().addAll("button");
        shuffleButton.getStyleClass().addAll("button");
        repeatButton.getStyleClass().addAll("button");

        this.getChildren().addAll(shuffleButton, prevButton, playButton, nextButton, repeatButton);

        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,20, 20, 20));
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    public Button getPlayButton() {
        return playButton;
    }
}
