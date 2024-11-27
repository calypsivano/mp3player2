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
import mp3player2.presentation.Views.PlaylistViewController;
import mp3player2.*;
import mp3player2.business.MP3Player;

import java.io.IOException;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class App extends Application {

    Stage primaryStage;
    Scene scene;
    boolean swichView = false;
    BorderPane rootPane;
    public PlayerViewController controllerPlayer;
    public PlaylistViewController controllerPlaylist;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //MP3Player Instanz erstellen
        MP3Player player = new MP3Player(new SimpleMinim());

        //Controller und View instanziieren
        controllerPlayer = new PlayerViewController(player);
        controllerPlaylist = new PlaylistViewController(player);

        //Root Erstellen
        rootPane = new BorderPane();
        rootPane.setTop(new TopPaneView(this));

        //Setzen als Default PLAYER als View:
        setView(controllerPlayer.getRootView());
        scene = new Scene(rootPane,600,750);      //Setzen unsere Scene erstmal auf den Player -> Später zu Willkommenscreen ändern!
        
        this.primaryStage = primaryStage;                       //merkt sich unser Fenster
        
        primaryStage.setScene(scene);                           //Scene der Stage zuweisen, wir haben eine Szene und eine Stage für die gesamte Anwendung. Roots/Panes ändern sich.
        
        //Style von controller
        rootPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

  

        //STAGE
        primaryStage.setTitle("Welcome to my Player.");
        //Gojo Icon für das Fenster, weil mich das happy macht.
        Image gojo = new Image(getClass().getResource("assets/gojo.jpg").toExternalForm());
        primaryStage.getIcons().add(gojo);

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }
    
    public void setView(Pane newView){
        rootPane.setCenter(newView);
    }

    public void switchRoot() {

        //Default ist Playerview, also beim ersten Switch kommt man zur Playlistview:
        if (swichView == false) {
            scene.setRoot(controllerPlayer.getRootView());

            //Nächster Abschnitt funktioniert nicht
            primaryStage.setTitle("This is the Playerview.");
            //Gojo Icon für das Fenster, weil mich das happy macht.
            Image gojo = new Image(getClass().getResource("assets/gojo.jpg").toExternalForm());
            primaryStage.getIcons().add(gojo);

            swichView = !swichView;

        } else {
            scene.setRoot(controllerPlaylist.getRootView());

            //Nächster Abschnitt funktioniert nicht
            primaryStage.setTitle("This is the Playlistview.");
            Image geto = new Image(getClass().getResource("assets/geto.jpg").toExternalForm());
            primaryStage.getIcons().add(geto);

            swichView = !swichView;
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