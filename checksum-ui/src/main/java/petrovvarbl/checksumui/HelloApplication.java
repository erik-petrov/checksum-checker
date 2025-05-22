package petrovvarbl.checksumui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class HelloApplication extends Application {
    private final Text filedescribe = new Text("Enter file path(or dragndrop):");
    private final Text checksumdescribe = new Text("Checksum:");
    private final TextArea filefield = new TextArea("");
    private final TextArea checksumfield = new TextArea("");
    private final Button checksumbutton = new Button("Retrieve checksum");
    private final Button controlbutton = new Button("Control checksum");
    @Override
    public void start(Stage stage) {
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
            String fileFieldText = filefield.getText();
            String checksumFieldText = checksumfield.getText();
            if (fileFieldText.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter a file path.");
                a.show();
            } else if (checksumFieldText.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter a checksum to compare with.");
                a.show();
            } else if (!Files.exists(Paths.get(fileFieldText))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("File not found. You may have inserted an invalid file path.");
                a.show();
            } else { // we control the checksum
                try {
                    File f = new File(fileFieldText);
                    if (f.verifyChecksum(checksumFieldText)) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Match! The checksum of the file matches the textbox.");
                        a.show();
                    } else {
                        Alert a = new Alert(Alert.AlertType.WARNING);
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

        //show the user that dragndrop event is possible
        vBox.setOnDragOver(x -> {
            if (x.getGestureSource() != filefield &&
                    x.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                x.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            x.consume();
        });

        //handle dragndrop
        vBox.setOnDragDropped((x) ->{
            Dragboard db = x.getDragboard();
            boolean success = false;

            if (db.hasFiles()) {
                File f;
                try {
                    f = new File(db.getFiles().getFirst().getPath());
                } catch (IOException | NoSuchAlgorithmException e) {
                    Alert r = new Alert(Alert.AlertType.ERROR);
                    r.setContentText("Error getting file.");
                    r.show();
                    x.consume();
                    return;
                }
                filefield.setText(f.getPath());
                if(checksumfield.getText().isEmpty()){
                    checksumfield.setText(f.getChecksumString());
                }
                success = true;
            }
            x.setDropCompleted(success);
            x.consume();
        });
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("Checksum checker");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}