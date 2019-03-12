package Controllers;

import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void change(MouseEvent mouseEvent) throws IOException {
        if (password.getText() != null && confirm.getText() != null && password.getText().equals(confirm.getText())){
            Launch.getCurrentUser().setPassword(password.getText());
            Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
            Launch.stage.close();
            if (Launch.stage != Launch.primary) {
                Launch.stage = Launch.primary;
                Launch.stage.close();
            }
            Launch.newWindow(root, new Stage());
            Launch.primary = Launch.stage;
        }
        else {
            System.out.println("Password fields do not match!");
        }
    }
}
