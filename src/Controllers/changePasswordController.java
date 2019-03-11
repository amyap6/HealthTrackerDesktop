package Controllers;

import application.Launch;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class changePasswordController implements Initializable {
    public HBox topBar;
    public PasswordField password;
    public PasswordField confirm;
    public Button change;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);
    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.primary.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }

    public void change(MouseEvent mouseEvent){
        if (password.toString() != null && confirm.toString() != null && password.toString() == confirm.toString()){
            Launch.getCurrentUser().setPassword(password.toString());
        }
    }
}
