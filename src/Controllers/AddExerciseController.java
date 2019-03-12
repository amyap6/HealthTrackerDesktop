package Controllers;

import DBClasses.DBAdd;
import Model.Calculator;
import Model.Exercise;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddExerciseController implements Initializable {

    public HBox topBar;
    public ComboBox selection;
    public Label label;
    public TextField duration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makePopupDraggable(topBar);

        if (DailyLogController.getExercise() == Exercise.Type.CARDIO) {
            label.setText("Add Cardio");
        } else {
            label.setText("Add Strength");
        }

        selection.getItems().addAll(DBAdd.getExercises(Launch.getCurrentUser().getUserName()
                ,DailyLogController.getExercise()));
    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }

    public void addCustom(MouseEvent mouseEvent) throws IOException {
        Launch.stage.close();
        Launch.stage = Launch.primary;
        Parent root = FXMLLoader.load(getClass().getResource("/View/addCustomExercise.fxml"));
        Launch.newWindow(root, new Stage());
        Launch.stage.setAlwaysOnTop(true);
    }

    public void submit(MouseEvent mouseEvent) throws IOException {
        int dur = 0;

        try {
            dur = Integer.parseInt(duration.getText());
        } catch (Exception e) {
            duration.setText("");
            duration.setPromptText("Input Duration");
            duration.setStyle("-fx-prompt-text-fill: red; -fx-background-color: transparent; -fx-text-fill:  rgba(0,0,0,0.8)");
        }

        int calories = DBAdd.getExerciseCalories(selection.getSelectionModel().getSelectedItem().toString(),
                Launch.getCurrentUser().getWeight(),dur);

        System.out.println(selection.getSelectionModel().getSelectedItem().toString());

        DBAdd.addCalories(Launch.getCurrentUser().getUserName(),
                new Date(Calendar.getInstance().getTime().getTime()),calories,"Exercise");

        Launch.stage.close();
        Launch.stage = Launch.primary;
        Parent root = FXMLLoader.load(getClass().getResource("/View/dailyLog.fxml"));
        Launch.stage.getScene().setRoot(root);
    }
}
