package Controllers;

import application.Launch;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class viewHealthDataController implements Initializable {

    public Label bmi;
    public Label ideal;
    public Label targetCals;
    public Label targetFat;
    public Label targetCarbs;
    public Label targetProtein;
    public HBox topBar;
    public Label username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bmi.setText(Integer.toString(Launch.getCurrentUser().getBmi()));
        ideal.setText(Integer.toString(Launch.getCurrentUser().getIdealWeight()));
        targetCals.setText(Integer.toString(Launch.getCurrentUser().getAllowedCalories()));
        targetFat.setText(Integer.toString(Launch.getCurrentUser().getFat()));
        targetCarbs.setText(Integer.toString(Launch.getCurrentUser().getCarbs()));
        targetProtein.setText(Integer.toString(Launch.getCurrentUser().getProtein()));
        username.setText(Launch.getCurrentUser().getUserName());

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }
}
