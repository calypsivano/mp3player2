package mp3player2.presentation.Views;

import javafx.scene.layout.BorderPane;
import mp3player2.presentation.UIComponents.TopPaneView;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import mp3player2.business.Track;

public class PlaylistView extends VBox {

    private ListView<Track> playlistListView;

    public PlaylistView(){
        this.getStyleClass().addAll("view");
        playlistListView = new ListView<>();
        this.getChildren().add(playlistListView);

        this.setAlignment(Pos.TOP_CENTER); 
        this.setSpacing(10); 
        this.setStyle("-fx-padding: 20;");
    }

    public ListView<Track> getPlaylistListView() {
        return playlistListView;
    }
    
}

