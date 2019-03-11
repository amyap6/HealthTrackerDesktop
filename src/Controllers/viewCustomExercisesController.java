package Controllers;

import DBClasses.DBAccess;
import DBClasses.DBAdd;
import Model.Exercise;
import application.Launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class viewCustomExercisesController implements Initializable {


    public ListView customExercises;
    public HBox topBar;
    public Label username;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        ObservableList<String> items = FXCollections.observableArrayList ();

        DBAccess.getConnection();

        ArrayList<String> exercises = new ArrayList<>();
        exercises.addAll(DBAdd.getExercises(Launch.getCurrentUser().getUserName(), Exercise.Type.CARDIO));
        exercises.addAll(DBAdd.getExercises(Launch.getCurrentUser().getUserName(), Exercise.Type.STRENGTH));

        DBAccess.closeConnection();

        //custom exercises not supported yet so at the moment this shows all exercises in the database

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
