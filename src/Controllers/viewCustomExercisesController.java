package Controllers;

import application.Launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class viewCustomExercisesController implements Initializable {


    public ListView customExercises;
    public HBox topBar;
    public Label username;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        ObservableList<String> items = FXCollections.observableArrayList();

        //get all users custom foods from the database

        items.addAll();
        customExercises.setItems(items);

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.primary.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;

    }

}
