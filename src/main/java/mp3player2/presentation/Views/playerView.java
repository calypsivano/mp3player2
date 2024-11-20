package mp3player2.presentation.Views;

import javafx.scene.layout.BorderPane;
import mp3player2.presentation.UIComponents.ControlView;
import mp3player2.presentation.UIComponents.TopPaneView;



public class PlayerView extends BorderPane{

    //PLAYERVIEW
        ControlView controlView;
        TopPaneView topPaneView = new TopPaneView();

        public PlayerView(){
            controlView = new ControlView();
            this.setTop(topPaneView);
            this.setBottom(controlView);
            this.getStyleClass().addAll("playerview");
            
    }


}
