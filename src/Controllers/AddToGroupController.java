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

public class AddToGroupController implements Initializable {

    public HBox topBar;
    public TextField userName;
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
        if (DBAdd.addMember(userName.getText(), GroupsController.current.getGroupName())) {
            Launch.stage.close();
            Launch.stage = Launch.primary;
            DBAdd.addMesage(userName.getText(), GroupsController.current, "USER ADDED");
            Parent root = FXMLLoader.load(getClass().getResource("/View/groups.fxml"));
            Launch.stage.getScene().setRoot(root);
        } else {
            validationText.setText("User not found");
        }
    }
}
