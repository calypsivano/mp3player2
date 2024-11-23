package mp3player2.presentation.Views;

import mp3player2.business.MP3Player;

public class PlaylistViewController {

    private PlaylistView rootView;

    private MP3Player player;

     public PlaylistViewController(MP3Player player) {
        this.rootView = new PlaylistView();
        this.player = player;
     }

     public PlaylistView getRootView() {
        return rootView;
    }
    
}
