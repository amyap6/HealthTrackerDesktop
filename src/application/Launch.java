package application;

import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launch extends Application {

    private static User currentUser;

    public static Stage stage = null;
    public static Stage primary = null;

    private static double xOffSet = 0;
    private static double yOffSet = 0;
    private static double xOffSetPopup = 0;
    private static double yOffSetPopup = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        newWindow(root, primary);
    }

    @SuppressWarnings("Duplicates")
    public static void makeStageDraggable(HBox topBar) {
        topBar.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        topBar.setOnMouseDragged((event) -> {
            primary.setX(event.getScreenX() - xOffSet);
            primary.setY(event.getScreenY() - yOffSet);
        });
    }

    @SuppressWarnings("Duplicates")
    public static void makePopupDraggable(HBox topBar) {
        topBar.setOnMousePressed((event) -> {
            xOffSetPopup = event.getSceneX();
            yOffSetPopup = event.getSceneY();
        });
        topBar.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - xOffSetPopup);
            stage.setY(event.getScreenY() - yOffSetPopup);
        });
    }

    public static void newWindow(Parent r, Stage s) {
        stage = s;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(r));
        stage.show();
    }

    public static void mainCheck() {
        if (primary != stage) {
            stage.close();
            stage = primary;
        }
    }

    public static void setCurrentUser(User u) {
        currentUser = u;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
