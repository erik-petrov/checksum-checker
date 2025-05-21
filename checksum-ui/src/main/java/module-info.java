module petrovvarbl.checksumui {
    requires javafx.controls;
    requires javafx.fxml;


    opens petrovvarbl.checksumui to javafx.fxml;
    exports petrovvarbl.checksumui;
}