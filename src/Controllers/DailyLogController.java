package Controllers;

import DBClasses.DBAdd;
import DBClasses.LoadUser;
import Model.Calculator;
import Model.Exercise;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DailyLogController implements Initializable {

    public HBox topBar;
    public ProgressIndicator calorieProgress;
    public Label calorieLeft;
    public ProgressIndicator exerciseProgress;
    public Label exerciseLabel;
    public Label breakCurrent;
    public Label breakCalories;
    public Label lunchCurrent;
    public Label lunchCalories;
    public Label dinnerCurrent;
    public Label dinnerCalories;
    public Label snackCurrent;
    public Label snackCalories;

    private static String meal;
    private static Exercise.Type exercise;
    public Label net;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        HomeController.setCalorieProgress(calorieProgress, calorieLeft);
        exerciseProgress.setProgress(0);
        exerciseLabel.setText("0");

        Date today = new Date(Calendar.getInstance().getTime().getTime());
        String user = Launch.getCurrentUser().getUserName();
        breakCurrent.setText(String.valueOf(DBAdd.getMealCalories(user, today, "Breakfast")));
        lunchCurrent.setText(String.valueOf(DBAdd.getMealCalories(user, today, "Lunch")));
        dinnerCurrent.setText(String.valueOf(DBAdd.getMealCalories(user, today, "Dinner")));
        snackCurrent.setText(String.valueOf(DBAdd.getMealCalories(user, today, "Snack")));

        int calories = Launch.getCurrentUser().getAllowedCalories();
        breakCalories.setText(String.valueOf(Calculator.targetBreakfast(calories)));
        lunchCalories.setText(String.valueOf(Calculator.targetLunch(calories)));
        dinnerCalories.setText(String.valueOf(Calculator.targetDinner(calories)));
        snackCalories.setText(String.valueOf(Calculator.targetSnacks(calories)));

        try {
            int loss = LoadUser.getCaloriesByDate(user,today,true);
            exerciseLabel.setText(String.valueOf(loss*-1));
            int net =  LoadUser.getCaloriesByDate(user,today,false) + loss;
            this.net.setText("Net Calories Today: " + net);

            ArrayList<String> exerciseGoal = DBAdd.getExerciseGoal(Launch.getCurrentUser().getUserName());
            int exerciseTarget = Integer.parseInt(exerciseGoal.get(1));
            int exerciseBurned = LoadUser.getCaloriesByDate(Launch.getCurrentUser().getUserName(),
                    new Date(Calendar.getInstance().getTime().getTime()), true);
            double percentage = 1-((double)(exerciseTarget+exerciseBurned)/exerciseTarget);
            exerciseProgress.setProgress(percentage);


        } catch (SQLException e) {

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


    public void addBreakfast(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            meal = "Breakfast";
            Parent root = FXMLLoader.load(getClass().getResource("/View/addFood.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void addLunch(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            meal = "Lunch";
            Parent root = FXMLLoader.load(getClass().getResource("/View/addFood.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void addDinner(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            meal = "Dinner";
            Parent root = FXMLLoader.load(getClass().getResource("/View/addFood.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void addSnack(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            meal = "Snack";
            Parent root = FXMLLoader.load(getClass().getResource("/View/addFood.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public static String getMeal() {
        return meal;
    }

    public void addStrength(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            exercise = Exercise.Type.STRENGTH;
            Parent root = FXMLLoader.load(getClass().getResource("/View/addExercise.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void addCardio(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            exercise = Exercise.Type.CARDIO;
            Parent root = FXMLLoader.load(getClass().getResource("/View/addExercise.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public static Exercise.Type getExercise() {
        return exercise;
    }

    public void updateWeight(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/updateWeight.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }
}
