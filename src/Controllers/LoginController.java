package Controllers;

import DBClasses.LoadUser;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public HBox topBar;
    public Button button;
    public Label validationText;
    public TextField userName;
    public PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        password.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                try {
                    loginSubmit();
                } catch (IOException IO) {
                    IO.printStackTrace();
                }
            }
        });
    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.primary.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void register(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/register.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        loginSubmit();
    }

    public void loginSubmit() throws IOException {
        if (!validate()) {
            return;
        } else {
            LoadUser user = null;

            try {
                user = new LoadUser(userName.getText());

                if (user.getUser().getPassword().equals(password.getText())) {
                    Launch.setCurrentUser(user.getUser());
                } else {
                    validationText.setText("Incorrect password");
                    return;
                }
            } catch (SQLException e) {
                validationText.setText("User not found");
                return;
            }
        }

        System.out.println(Launch.getCurrentUser());

        Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        Launch.stage.close();
        if (Launch.stage != Launch.primary) {
            Launch.stage = Launch.primary;
            Launch.stage.close();
        }
        Launch.newWindow(root, new Stage());
        Launch.primary = Launch.stage;
    }

    private Boolean validate() {
        if (userName.getText().equals("")) {
            validationText.setText("Enter your username");
            return false;
        }

        if (password.getText().equals("")) {
            validationText.setText("Enter your password");
            return false;
        }

        return true;
    }
}
