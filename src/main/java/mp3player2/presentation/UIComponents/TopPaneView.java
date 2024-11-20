package mp3player2.presentation.UIComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TopPaneView extends VBox {

    public TopPaneView() {

        // navigation Pane - switchButton + centered title
        StackPane navigationPane = new StackPane();
        navigationPane.setAlignment(Pos.BASELINE_CENTER);
        navigationPane.setPadding(new Insets(5, 10, 5, 10));

        //Switch Button, Ansicht wechseln Align left
        HBox switchPane = new HBox();
        switchPane.setAlignment(Pos.BASELINE_LEFT);
        Button switchButton = new Button();
        switchButton.getStyleClass().addAll("icon-button");
        switchButton.setId("switch-button");
        switchPane.getChildren().addAll(switchButton);
        
        navigationPane.getChildren().addAll(switchPane);

        this.getChildren().addAll(navigationPane);
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }

}
