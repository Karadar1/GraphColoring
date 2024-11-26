package graphfx.graphcoloring;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML

    public void onFileClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("file-graph.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("File Graph");
        stage.show();

    }
    @FXML
    public void onRandomClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("random-graph.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Random Graph");
        stage.show();
    }
}
