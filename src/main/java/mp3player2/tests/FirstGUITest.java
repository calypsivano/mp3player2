package mp3player2.tests;

import javafx.application.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FirstGUITest extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        Group root = new Group();
        Scene scene = new Scene(root, 700, 900, Color.BEIGE);

        Text text = new Text();
        text.setText("HELLO THERE!");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Times New Roman", 30));
        text.setFill(Color.GRAY);

        root.getChildren().add(text);

        //Image gojo = new Image(getClass().getResource("/mp3player2/assets/gojo.jpg").toExternalForm());
        //stage.getIcons().add(gojo);

        //stage.setWidth(720);
        //stage.setHeight(1080);
        //stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    } 

    public void init() {

    }

    public void stop() {

    }

    public static void main(String[] args) {
        launch(args);      //Wirft unsere args in die launch methode, dort wird dann unter anderem die start methode aufgerufen und unser programm startet.
    }
}
