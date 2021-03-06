package Controllers;

import DBClasses.DBAccess;
import DBClasses.DBAdd;
import application.Launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class viewCustomFoodsController implements Initializable {

    public ListView customFoods;
    public Label username;
    public HBox topBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        ObservableList<String> items = FXCollections.observableArrayList ();

        DBAccess.getConnection();

        ArrayList<String> foods = DBAdd.getFoods
                    ("Custom", Launch.getCurrentUser().getUserName());

        DBAccess.closeConnection();

        items.addAll(foods);
        customFoods.setItems(items);

    }

    public void minimise(MouseEvent mouseEvent) {
        Launch.primary.setIconified(true);
    }

    public void close(MouseEvent mouseEvent) {
        Launch.stage.close();
        Launch.stage = Launch.primary;
    }

}
