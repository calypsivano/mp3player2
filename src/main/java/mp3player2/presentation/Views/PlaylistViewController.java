package mp3player2.presentation.Views;

import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mp3player2.business.MP3Player;
import mp3player2.business.Track;
import mp3player2.presentation.Views.PlaylistView;

public class PlaylistViewController {

    private PlaylistView rootView;
    private MP3Player player;
    private ListView<Track> listView;

     public PlaylistViewController(MP3Player player) {
        this.rootView = new PlaylistView();
        this.player = player;

        initialize();
     }

    private void initialize() {
        listView = rootView.getPlaylistListView();

        listView.getItems().addAll(player.getCurrentPlaylist().getTracks());

        //Darstellung + Aktuell gespieltes Lied soll in Bold dargestellt werden (vllt später zu einem Icon ändern?)
        listView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
            @Override
            public ListCell<Track> call(ListView<Track> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Track item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTitle() + " - " + item.getArtist());
                            if (item.equals(player.getCurrentTrack())) {
                                setStyle("-fx-font-weight: bold;");
                            } else {
                                setStyle(""); // Zurücksetzen Bold
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        //Songwechsel bei Klick
        listView.setOnMouseClicked(event -> {
            Track currentTrack = listView.getSelectionModel().getSelectedItem();
            if (currentTrack != null) {
                player.pause();
                player.setPossition(0);
                player.loadTrack(currentTrack);
                player.play();
                Platform.runLater(() -> listView.refresh()); //Ansicht aktualisieren
            }
        });

        //Stylesss
        listView.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        listView.setMaxWidth(1000);

        updateListView();
    }

    private void updateListView() {
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    listView.refresh();
                });
    
                try {
                    Thread.sleep(500); // Aktualisierung alle 500ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

     public PlaylistView getRootView() {
        return rootView;
    }
    
}
