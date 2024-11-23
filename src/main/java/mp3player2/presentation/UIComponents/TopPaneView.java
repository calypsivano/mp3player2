package mp3player2.presentation.UIComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mp3player2.App;

public class TopPaneView extends VBox {

    Button switchButton;

    public TopPaneView(App app) {

        // navigation Pane - switchButton + centered title
        StackPane navigationPane = new StackPane();
        navigationPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        navigationPane.setAlignment(Pos.BASELINE_CENTER);
        navigationPane.setPadding(new Insets(10, 10, 5, 10));

        //Switch Button, Ansicht wechseln Align left
        HBox switchPane = new HBox();
        switchPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        switchPane.setAlignment(Pos.BASELINE_LEFT);

        switchButton = new Button();
        switchButton.setOnAction(event -> {
            if(app.controllerPlayer.getRootView().getParent() != null) {
                app.setView(app.controllerPlaylist.getRootView());
            } else {
                app.setView(app.controllerPlayer.getRootView());
            }
        });

        //Hintergrund transparent
        this.getStyleClass().addAll("background");
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        switchButton.getStyleClass().addAll("icon-button");
        switchButton.setId("switch-button");
        switchPane.getChildren().addAll(switchButton);
        
        navigationPane.getChildren().addAll(switchPane);

        this.getChildren().addAll(navigationPane);
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }

    public Button getSwitchButton(){
        return switchButton;
    }

}
