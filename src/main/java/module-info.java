module mp3player2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires eia.simpleminim.v2;
    requires javafx.graphics;

    opens mp3player2 to javafx.fxml;
    exports mp3player2;
}
