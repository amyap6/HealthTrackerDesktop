package Controllers;

import DBClasses.DBAdd;
import Model.Calculator;
import Model.Goal;
import Model.User;
import application.Launch;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class RegisterController implements Initializable {

    private User newUser;

    public TextField firstName;
    public TextField lastName;
    public DatePicker dob;
    public TextField userName;
    public TextField email;
    public PasswordField password;

    public ComboBox<String> sex;
    public TextField height;
    public TextField weight;
    public ComboBox<String> activityLevel;
    public TextField targetWeight;
    public DatePicker targetDate;

    public HBox topBar;
    public Label sexPrompt;
    public Label activityPrompt;
    public Label validationText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makePopupDraggable(topBar);

        dob.setShowWeekNumbers(false);
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            if (date.isAfter(LocalDate.now())) {
                this.setDisable(true);
            }
            }
        });

        targetDate.setShowWeekNumbers(false);
        targetDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            if (date.isBefore(LocalDate.now())) {
                this.setDisable(true);
            }
            }
        });

        setActivityLevel();
        setSex();
    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.stage.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }

    public void setActivityLevel() {
        activityLevel.getItems().add("Less than 2 hours per week");
        activityLevel.getItems().add("2-5 hours per week");
        activityLevel.getItems().add("6-10 hours per week");
        activityLevel.getItems().add("More than 10 hours per week");
    }

    public void setSex() {
        sex.getItems().add("Male");
        sex.getItems().add("Female");
    }

    private Boolean validate() {
        if (userName.getText().equals("")) {
            validationText.setText("Please enter a username");
            return false;
        } else {
            newUser = new User(userName.getText());
        }

        if (firstName.getText().equals("")) {
            validationText.setText("Please enter your first name");
            return false;
        } else {
            newUser.setFirstName(firstName.getText());
        }

        if (lastName.getText().equals("")) {
            validationText.setText("Please enter your last name");
            return false;
        } else {
            newUser.setLastName(lastName.getText());
        }

        if (dob.getValue() == null) {
            validationText.setText("Please select your date of birth");
            return false;
        } else {
            newUser.setDob(dob.getValue());
        }

        if (email.getText().equals("")) {
            validationText.setText("Please enter an email address");
            return false;
        } else {
            newUser.setEmail(email.getText());
        }

        if (password.getText().equals("")) {
            validationText.setText("Please enter a password");
            return false;
        } else {
            newUser.setPassword(password.getText());
        }

        if (sex.getSelectionModel().isEmpty()) {
            validationText.setText("Please select your sex");
            return false;
        } else {
            if (sex.getSelectionModel().isSelected(0)) {
                newUser.setSex(0);
            } else {
                newUser.setSex(1);
            }
        }

        if (height.getText().equals("")) {
            validationText.setText("Please enter your height");
            return false;
        } else {
            try {
                newUser.setHeight(Integer.parseInt(height.getText()));
            } catch (Exception e) {
                validationText.setText("Invalid height input");
                return false;
            }
        }

        if (weight.getText().equals("")) {
            validationText.setText("Please enter your weight");
            return false;
        } else {
            try {
                newUser.setWeight(Double.parseDouble(weight.getText()));
            } catch (Exception badDouble) {
                try {
                    newUser.setWeight(Integer.parseInt(weight.getText()));
                } catch (Exception badInt) {
                    validationText.setText("Invalid weight input");
                    return false;
                }
            }
        }

        if (activityLevel.getSelectionModel().isEmpty()) {
            validationText.setText("Please select your level of activity");
            return false;
        } else {
            if (activityLevel.getSelectionModel().isSelected(0)) {
                newUser.setActivityLevel(1.2);
            } else if (activityLevel.getSelectionModel().isSelected(1)) {
                newUser.setActivityLevel(1.375);
            } else if (activityLevel.getSelectionModel().isSelected(2)) {
                newUser.setActivityLevel(1.55);
            } else if (activityLevel.getSelectionModel().isSelected(3)) {
                newUser.setActivityLevel(1.725);
            } else {
                newUser.setActivityLevel(1.9);
            }
        }
        newUser.setBmi((int)Calculator.bmi(newUser.getWeight(), newUser.getHeight()));

        if (targetWeight.getText().equals("")) {
            validationText.setText("Please enter your target weight");
            return false;
        } else {
            try {
                Double.parseDouble(targetWeight.getText());
            } catch (Exception badDouble) {
                try {
                    Integer.parseInt(targetWeight.getText());
                } catch (Exception badInt) {
                    validationText.setText("Invalid target weight input");
                    return false;
                }
            }
        }

        if (targetDate.getValue() == null) {
            validationText.setText("Please enter target date for your goal");
            return false;
        } else {
            double weightLoss = Double.parseDouble(weight.getText()) - Double.parseDouble(targetWeight.getText());
            Date date = Date.from(targetDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Goal startGoal = new Goal(weightLoss, date, newUser.getWeight());
            if (weightLoss < 0) {
                validationText.setText("Please enter a target weight less than your current weight");
                return false;
            } else if (Calculator.getWeightLossExtremity(startGoal) == 0) {
                validationText.setText("Goal is unachievable in this time frame. Please set a more achievable goal.");
                return false;
            } else {
                newUser.addGoal(startGoal);
                DBAdd.addGoal(newUser.getUserName(), startGoal);
                double target = (newUser.getAllowedCalories()*(newUser.getActivityLevel()-1))/2;
                DBAdd.addExerciseGoal(newUser.getUserName(), (int)target);
            }
        }

        return true;
    }

    public void registerSubmit(MouseEvent mouseEvent) {
        if (validate()) {
            try {
                DBAdd.addUser(newUser);
                validationText.setText("ACCOUNT CREATED SUCCESSFULLY");
                TimeUnit.MILLISECONDS.sleep(300);
                Launch.stage.close();
                Launch.stage = Launch.primary;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeSex(ActionEvent actionEvent) {
        sexPrompt.setStyle("-fx-text-fill: transparent");
    }

    public void removeActivity(ActionEvent actionEvent) {
        activityPrompt.setStyle("-fx-text-fill: transparent");
    }
}
