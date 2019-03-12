package Controllers;

import DBClasses.LoadUser;
import Model.Goal;
import application.Launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import org.h2.jdbc.JdbcSQLException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class GoalsController implements Initializable {

    public HBox topBar;
    public LineChart<String, Number> chart;
    public Line line;
    public NumberAxis numberAxis;
    public Label dayLeft;
    public Label start;
    public Label current;
    public Label target;
    public Label progress;
    public ProgressIndicator dayProgress;
    public Label targetWeight;
    public Label currentWeight;
    private XYChart.Series<String, Number> series;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);
        HomeController.setDaysLeft(dayProgress, dayLeft);

        Goal weightGoal = Launch.getCurrentUser().getGoals().get(0);
        targetWeight.setText("Target Weight: " + (int)(weightGoal.getStartWeight()-weightGoal.getTargetWeightLoss()) + "kg");
        currentWeight.setText("Current Weight: " + (int)Launch.getCurrentUser().getWeight() + "kg");
        createChart();
        start.setText("Start Weight: " + (int)weightGoal.getStartWeight() + "%");
        target.setText(targetWeight.getText());
        current.setText(currentWeight.getText());
        progress.setText("Progress: " + (int)weightGoal.getPercentLost() + "%");
    }

    private void createChart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd");
        Calendar cal = Calendar.getInstance();
        Date date;

        String[] dateStrings = new String[7];
        java.sql.Date[] dates = new java.sql.Date[7];
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();
            dates[i] = new java.sql.Date(date.getYear(),date.getMonth(),date.getDate());
            dateStrings[i] = dateFormat.format(date);
        }

        series = new XYChart.Series<>();
        int count = 6;

        int high = Integer.MIN_VALUE;
        int low = Integer.MAX_VALUE;

        for (int i = 0; i < 7; i++) {
            try {
                int calories = LoadUser.getCaloriesByDate(Launch.getCurrentUser().getUserName(), dates[count], false);
                if (calories < low) low = calories;
                if (calories > high) high = calories;
                series.getData().add(new XYChart.Data<>(dateStrings[count], calories));
                count--;
            } catch (SQLException e) {
                series.getData().add(new XYChart.Data<>(dateStrings[count], 0));
                count--;
            }
        }

        chart.getData().clear();
        chart.getData().add(series);
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
