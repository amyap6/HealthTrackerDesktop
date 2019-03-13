package Controllers;

import DBClasses.DBAdd;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class updateFoodGoalController implements Initializable {

    public HBox topBar;
    public TextField weight;
    public Label validationText;
    public DatePicker date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makePopupDraggable(topBar);

        date.setShowWeekNumbers(false);
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    this.setDisable(true);
                }
            }
        });
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
        if (weight.getText().equals("")) {
            return false;
        } else {
            try {
                Integer.parseInt(weight.getText());
            } catch (Exception e) {
                return false;
            }
        }
        if (date.getValue() == null) {
            return false;
        }
        return true;
    }
}
