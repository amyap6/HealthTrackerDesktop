package Controllers;

import DBClasses.DBRemoval;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{

    public HBox topBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.primary.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void toDailyLog(MouseEvent mouseEvent) throws IOException {
        Launch.mainCheck();
        Parent root = FXMLLoader.load(getClass().getResource("/View/dailyLog.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toGoals(MouseEvent mouseEvent) throws IOException {
        Launch.mainCheck();
        Parent root = FXMLLoader.load(getClass().getResource("/View/goals.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toHome(MouseEvent mouseEvent) throws IOException {
        Launch.mainCheck();
        Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toGroups(MouseEvent mouseEvent) throws IOException {
        Launch.mainCheck();
        Parent root = FXMLLoader.load(getClass().getResource("/View/groups.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toSettings(MouseEvent mouseEvent) throws IOException {
        Launch.mainCheck();
        Parent root = FXMLLoader.load(getClass().getResource("/View/settings.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Launch.stage.close();
        if (Launch.stage != Launch.primary) {
            Launch.stage = Launch.primary;
            Launch.stage.close();
        }
        Launch.newWindow(root, new Stage());
        Launch.primary = Launch.stage;
    }

    public void viewAccountDetails(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/viewAccountDetails.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void changePassword(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/changePassword.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void viewHealthData(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/viewHealthData.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws IOException {
        DBRemoval.removeUser(Launch.getCurrentUser());
    }

    public void viewCustomFoods(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/viewCustomFoods.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void clearCustomFoods(MouseEvent mouseEvent) throws IOException {
        //maybe add pop-up window asking are you sure
        DBClasses.DBAccess.getConnection();
        try {
            DBClasses.DBAccess.st.executeUpdate("DELETE FROM FOODS WHERE USERNAME = '"+ Launch.getCurrentUser().getUserName()+"'");
            System.out.println("All custom foods for user: "+ Launch.getCurrentUser().getUserName()+" removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBClasses.DBAccess.closeConnection();
    }

    public void viewCustomExercises(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/viewCustomExercises.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void clearCustomExercises(MouseEvent mouseEvent) throws IOException {
        //maybe add pop-up window asking are you sure
        DBClasses.DBAccess.getConnection();
        try {
            DBClasses.DBAccess.st.executeUpdate("DELETE FROM EXERCISE WHERE USERNAME = '"+ Launch.getCurrentUser().getUserName()+"'");
            System.out.println("All goals for user: "+ Launch.getCurrentUser().getUserName()+" removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBClasses.DBAccess.closeConnection();
    }

    public void viewCompletedGoals(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary){
            Parent root = FXMLLoader.load(getClass().getResource("/View/viewCompletedGoals.fxml"));

            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }

    }

}
