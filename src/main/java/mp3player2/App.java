package mp3player2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mp3player2.presentation.UIComponents.ControlView;
import mp3player2.presentation.UIComponents.TopPaneView;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage primaryStage;
    Scene scene;
    BorderPane playerView;
    BorderPane playlistView;

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        this.playerView = new BorderPane();       //Hauptview Player
        this.playlistView = new BorderPane();           //Hauptview Playlist

        this.primaryStage = primaryStage;   //merkt sich unser Fenster

        scene = new Scene(playerView,600,750);      //Setzen unsere Scene erstmal auf den Player -> Später zu Willkommenscreen ändern!
        primaryStage.setScene(scene);                //Scene der Stage zuweisen, wir haben eine Szene und eine Stage für die gesamte Anwendung. Roots/Panes ändern sich.
        
        switchRoot("PLAYER");
        //PLAYERVIEW
        ControlView controlView = new ControlView();
        TopPaneView topPaneView = new TopPaneView();

        playerView.setTop(topPaneView);
        playerView.setBottom(controlView);
        playerView.getStyleClass().addAll("playerview");
        playerView.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        //PLAYLISTVIEW

        //STAGE
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);

        primaryStage.show();
    }

    public void switchRoot(String name) {
        switch(name) {

            case "PLAYER":
            scene.setRoot(playerView);
            primaryStage.setTitle("This is the Playerview.");
            //Gojo Icon für das Fenster, weil mich das happy macht.
            Image gojo = new Image(getClass().getResource("assets/gojo.jpg").toExternalForm());
            primaryStage.getIcons().add(gojo);
            break;

            case "PLAYLIST":
            scene.setRoot(playlistView);
            primaryStage.setTitle("This is the Playlistview.");
            Image geto = new Image(getClass().getResource("assets/geto.jpg").toExternalForm());
            primaryStage.getIcons().add(geto);
            break;

            default: 
            break;
        }
    }

    public void init() {

    }

    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);
    }

}