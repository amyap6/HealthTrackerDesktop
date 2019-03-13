package Controllers;

import DBClasses.DBAdd;
import DBClasses.LoadUser;
import Model.User;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public HBox topBar;
    public Label currentDate;
    public Label welcome;
    public ProgressIndicator dayProgress;
    public Label daysLeft;
    public ProgressIndicator calorieProgress;
    public Label caloriesLeft;
    public ProgressIndicator goalProgress;
    public Label goalLeft;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        SimpleDateFormat date = new SimpleDateFormat("EEEEE, dd MMMMM yyyy");
        currentDate.setText(date.format(new Date()));

        welcome.setText("Welcome, " + Launch.getCurrentUser().getFirstName());

        setCalorieProgress(calorieProgress, caloriesLeft);
        setDaysLeft(dayProgress, daysLeft);
        setGoalProgress(goalProgress, goalLeft);
    }

    public static void setCalorieProgress(ProgressIndicator p, Label l) {
        User current = Launch.getCurrentUser();

        int todayCalories = 0;
        int allowedCalories = current.getAllowedCalories();

        java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            todayCalories = LoadUser.getCaloriesByDate(current.getUserName(), today, false);
        } catch (SQLException e) {
            DBAdd.addCalories(current.getUserName(), today, todayCalories, "Breakfast");
            DBAdd.addCalories(current.getUserName(), today, todayCalories, "Lunch");
            DBAdd.addCalories(current.getUserName(), today, todayCalories, "Dinner");
            DBAdd.addCalories(current.getUserName(), today, todayCalories, "Snack");
        }

        l.setText(String.valueOf(allowedCalories-todayCalories));

        double progress = 0;
        if ((allowedCalories-todayCalories) > 0) {
            progress = (double)(allowedCalories-todayCalories) / allowedCalories;
        }

        p.setProgress(progress);
    }

    public static void setDaysLeft(ProgressIndicator p, Label l) {
        User current = Launch.getCurrentUser();

        l.setText(String.valueOf(current.getGoals().get(0).getDaysRemaining()));

        double remaining = current.getGoals().get(0).getDaysRemaining();
        double percentage = 0;
        if (remaining > 0) {
            percentage = remaining / current.getGoals().get(0).getStartDays();
        }
        p.setProgress(percentage);
    }

    public static void setGoalProgress(ProgressIndicator p, Label l) {
        User current = Launch.getCurrentUser();
        double percentLost = DBAdd.getLostPercentage(current);
        l.setText((int)(percentLost*100) + "%");
        if (percentLost > 0) {
            p.setProgress(percentLost);
        } else {
            p.setProgress(0);
        }
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
}
