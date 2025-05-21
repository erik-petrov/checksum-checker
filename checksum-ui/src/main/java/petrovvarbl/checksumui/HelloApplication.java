package petrovvarbl.checksumui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class HelloApplication extends Application {
    private Text filedescribe = new Text("Enter file path:");
    private Text checksumdescribe = new Text("Checksum:");
    private TextArea filefield = new TextArea("");
    private TextArea checksumfield = new TextArea("");
    private Button checksumbutton = new Button("Retrieve checksum");
    private Button controlbutton = new Button("Control checksum");
    @Override
    public void start(Stage stage) throws IOException, NoSuchAlgorithmException {
        VBox vBox = new VBox();
        filefield.setPrefWidth(450); // first text area
        filefield.setPrefHeight(50);
        filefield.setWrapText(true);
        vBox.getChildren().add(filedescribe);
        vBox.getChildren().add(filefield);

        GridPane buttons = new GridPane();
        buttons.setHgap(5);
        checksumbutton.setOnMouseClicked(_ -> { // first button
            String p = filefield.getText();
            if (p.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter a file path.");
                a.show();
            } else if (!Files.exists(Paths.get(p))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("File not found. You may have inserted an invalid file path.");
                a.show();
            } else { // we take the checksum
                try {
                    File f = new File(p);
                    checksumfield.setText(f.getChecksumString());
                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        buttons.add(checksumbutton, 0, 0);

        controlbutton.setOnMouseClicked(_ -> { // second button
            String p = filefield.getText();
            String c = checksumfield.getText();
            if (p.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter a file path.");
                a.show();
            } else if (c.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter a checksum to compare with.");
                a.show();
            } else if (!Files.exists(Paths.get(p))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("File not found. You may have inserted an invalid file path.");
                a.show();
            } else { // we control the checksum
                try {
                    File f = new File(p);
                    if (f.verifyChecksum(c)) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Match! The checksum of the file matches the textbox.");
                        a.show();
                    } else {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("No match. The checksum of the file does not match the textbox.");
                        a.show();
                    }
                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        buttons.add(controlbutton, 1, 0);
        vBox.getChildren().add(buttons);

        checksumfield.setPrefHeight(50); // bottom text area
        checksumfield.setWrapText(true);
        vBox.getChildren().add(checksumdescribe);
        vBox.getChildren().add(checksumfield);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("Checksum checker");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}