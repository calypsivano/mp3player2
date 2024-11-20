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
import mp3player2.presentation.Views.PlayerView;
import mp3player2.presentation.Views.PlayerViewController;
import mp3player2.*;
import mp3player2.business.MP3Player;

import java.io.IOException;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

/**
 * JavaFX App
 */
public class App extends Application {

    Stage primaryStage;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //MP3Player Instanz erstellen
        MP3Player player = new MP3Player(new SimpleMinim());

        //Controller und View instanziieren
        PlayerViewController controllerPlayer = new PlayerViewController(player);
        scene = new Scene(controllerPlayer.getRootView(),600,750);      //Setzen unsere Scene erstmal auf den Player -> Später zu Willkommenscreen ändern!
        
        this.primaryStage = primaryStage;               //merkt sich unser Fenster

        
        primaryStage.setScene(scene);                //Scene der Stage zuweisen, wir haben eine Szene und eine Stage für die gesamte Anwendung. Roots/Panes ändern sich.
        
        //switchRoot("PLAYER");
        //PLAYER
        controllerPlayer.getRootView().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
  

        //STAGE
        primaryStage.setTitle("This is the Playerview.");
        //Gojo Icon für das Fenster, weil mich das happy macht.
        Image gojo = new Image(getClass().getResource("assets/gojo.jpg").toExternalForm());
        primaryStage.getIcons().add(gojo);

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    /*
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

     */

    public void init() {

    }

    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);
    }

}