package Controllers;

import application.Launch;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class viewAccountDetailsController implements Initializable {
    public Label name;
    public Label height;
    public Label weight;
    public Label age;
    public Label goals;
    public Label username;
    public HBox topBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        username.setText(Launch.getCurrentUser().getUserName());
        name.setText(Launch.getCurrentUser().getFirstName() + " " + Launch.getCurrentUser().getLastName());
        height.setText(Integer.toString(Launch.getCurrentUser().getHeight()) + " cm");
        weight.setText(Integer.toString((int) Launch.getCurrentUser().getWeight()) + " kg");
        age.setText(Integer.toString(Launch.getCurrentUser().getAge()));
        goals.setText(Launch.getCurrentUser().getGoals().get(0).toString());

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }
}
