package Controllers;

import DBClasses.DBAdd;
import Model.Food;
import Model.Goal;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import sun.misc.Launcher;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddCustomExerciseController implements Initializable {

    public HBox topBar;
    public TextField name;
    public TextField duration;
    public TextField calories;
    public CheckBox save;
    public Label validationText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makePopupDraggable(topBar);

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }

    public void addExercise(MouseEvent mouseEvent) throws IOException {
        if (validate()) {
            if (save.isSelected()) {
                DBAdd.addCustomExercise(name.getText(), DailyLogController.getExercise(),
                        Integer.parseInt(duration.getText()), Integer.parseInt(calories.getText()), Launch.getCurrentUser());
            }
            DBAdd.addCalories(Launch.getCurrentUser().getUserName(), new Date(Calendar.getInstance().getTime().getTime()),
                        -Integer.parseInt(calories.getText()),"Exercise");
        }

        Launch.stage.close();
        Launch.stage = Launch.primary;
        Parent root = FXMLLoader.load(getClass().getResource("/View/dailyLog.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    private Boolean validate() {
        if (name.getText().equals("")) {
            validationText.setText("Name cannot be blank.");
            return false;
        }
        if (duration.getText().equals("")) {
            validationText.setText("Duration cannot be blank.");
            return false;
        } else {
            try {
                Integer.parseInt(duration.getText());
            } catch (Exception e) {
                validationText.setText("Duration must be a number.");
                return false;
            }
        }
        if (calories.getText().equals("")) {
            validationText.setText("Calories cannot be blank.");
            return false;
        } else {
            try {
                Integer.parseInt(calories.getText());
                validationText.setText("Calories must be a number.");
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
