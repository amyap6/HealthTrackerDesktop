package Controllers;

import application.Launch;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class updateExerciseGoalController implements Initializable {

    public HBox topBar;
    public TextField calories;
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

    public void submit(MouseEvent mouseEvent) throws IOException {
        if (validate()) {

        }
    }

    private Boolean validate() {
        if (calories.getText().equals("")) {
            return false;
        } else {
            try {
                Integer.parseInt(calories.getText());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
