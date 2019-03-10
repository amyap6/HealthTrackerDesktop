package Controllers;

import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

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
        Parent root = FXMLLoader.load(getClass().getResource("/View/dailyLog.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toGoals(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/goals.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toHome(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toGroups(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/groups.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void toSettings(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/settings.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        //log out this user and return to launch view
    }

    public void viewAccountDetails(MouseEvent mouseEvent) throws IOException {
        //view this user's name, username, sex, height, weight, date of birth, current goals
        //in popup window
    }

    public void changePassword(MouseEvent mouseEvent) throws IOException {
        //pop-up window with new password setter
    }

    public void viewHealthData(MouseEvent mouseEvent) throws IOException {
        //pop-up window, view this user's bmi, ideal weight, target calories, target fat, carbs and protein
    }

    public void deleteAccount(MouseEvent mouseEvent) throws IOException {
        //delete this user from database
        //maybe pop-up window to ask are you sure
    }

    public void viewCustomFoods(MouseEvent mouseEvent) throws IOException {
        //pop-up window to view users custom foods
    }

    public void clearCustomFoods(MouseEvent mouseEvent) throws IOException {
        //pop-up window, are you sure you want to clear your custom foods, then remove from database
    }

    public void viewCustomExercises(MouseEvent mouseEvent) throws IOException {
        //pop-up window to view users custom exercises
    }

    public void clearCustomExercises(MouseEvent mouseEvent) throws IOException {
        //pop-up window, are you sure you want to clear your custom exercises, then remove from database
    }


}
