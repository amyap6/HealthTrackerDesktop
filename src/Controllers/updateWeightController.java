package Controllers;

import DBClasses.DBAdd;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class updateWeightController implements Initializable {

    public HBox topBar;
    public TextField weight;
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
            DBAdd.updateWeight(Launch.getCurrentUser().getUserName(), Integer.parseInt(weight.getText()));
            Launch.getCurrentUser().setWeight(Integer.parseInt(weight.getText()));
            Launch.stage.close();
            Launch.stage = Launch.primary;
            Parent root = FXMLLoader.load(getClass().getResource("/View/dailyLog.fxml"));
            Launch.stage.getScene().setRoot(root);
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
        return true;
    }
}
