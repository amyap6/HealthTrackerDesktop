package Controllers;

import DBClasses.DBAdd;
import DBClasses.LoadUser;
import Model.Exercise;
import Model.Group;
import Model.Message;
import Model.User;
import application.Launch;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    public HBox topBar;
    public ScrollPane scrollPane;
    public TextField message;
    public ScrollPane messageDisplay;
    public HBox bottomBar;
    public Label addMembers;

    private static Group longevity = new Group("Longevity", null);
    public static Group current = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Launch.makeStageDraggable(topBar);

        ArrayList<Group> groups = new ArrayList<>();
        groups.addAll(DBAdd.getGroups(Launch.getCurrentUser().getUserName()));
        groups.add(longevity);

        if (groups.size() == 1) {
            current = longevity;
        } else if (current == null) {
            current = groups.get(0);
        }

        ArrayList<Message> messages = DBAdd.getMessages(current.getGroupName());

        if (current.getGroupName().equals("Longevity")) {
            bottomBar.setDisable(true);
            addMembers.setDisable(true);
        } else {
            bottomBar.setDisable(false);
            addMembers.setDisable(false);
        }

        VBox v = new VBox();
        v.getChildren().clear();
        v.setFillWidth(true);
        for (int i = 0; i < groups.size(); i++) {
            VBox temp = sideBarCreate(groups.get(i));
            v.getChildren().add(temp);
        }
        scrollPane.setContent(v);

        VBox messageView = new VBox();
        messageView.setStyle("-fx-padding: 0px 0px 15px 0px");
        messageView.setMaxWidth(500);
        messageView.getChildren().clear();
        for (int i = 0; i < messages.size(); i++) {
            Message mess = messages.get(i);
            HBox wrap = new HBox();
            wrap.setPrefWidth(500);
            wrap.setMaxWidth(500);
            wrap.setMinWidth(500);
            wrap.getChildren().clear();
            VBox message = new VBox();
            message.getStyleClass().add("messageWrapper");
            Label a = new Label(mess.getName());
            a.getStyleClass().add("messageName");
            String messs = mess.getMessage();
            Label b = new Label(mess.getMessage());
            b.getStyleClass().add("message");
            a.setWrapText(true);
            b.setWrapText(true);
            message.getChildren().clear();
            if (i == 0) {
                message.getChildren().add(a);
            } else if (!messages.get(i-1).getUser().equals(messages.get(i).getUser())) {
                message.getChildren().add(a);
            } else {
                message.setStyle("-fx-padding: 3px 5px 0px 15px");
            }
            if (!messs.equals("USER ADDED")) message.getChildren().add(b);
            wrap.getChildren().add(message);
            if (!mess.getUser().equals(Launch.getCurrentUser().getUserName())) {
                b.getStyleClass().clear();
                b.getStyleClass().add("messageOther");
                wrap.setAlignment(Pos.TOP_RIGHT);
                message.setAlignment(Pos.TOP_RIGHT);
            }
            if (messs.equals("USER ADDED")) {
                b.setText("New user added!");
                wrap.getStyleClass().clear();
                wrap.setAlignment(Pos.CENTER);
                message.setAlignment(Pos.CENTER);
                a.setText("+ " + mess.getName() + " was added to the group");
                a.getStyleClass().clear();
                a.getStyleClass().add("messageAdded");
            }
            messageView.getChildren().add(wrap);
        }
        messageDisplay.vvalueProperty().bind(messageView.heightProperty());

        messageDisplay.setContent(messageView);
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

    public void onEnter(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            send();
        }
    }

    private void send() throws IOException {
        if (message.getText().equals("")) {
            return;
        }
        DBAdd.addMesage(Launch.getCurrentUser().getUserName(),current,message.getText());
        message.clear();
        message.setStyle("-fx-background-color: transparent");
        Parent root = FXMLLoader.load(getClass().getResource("/View/groups.fxml"));
        Launch.stage.getScene().setRoot(root);
    }

    private VBox sideBarCreate(Group g) {
        VBox temp = new VBox();
        temp.setOnMouseClicked(e -> {
            current = g;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/View/groups.fxml"));
                Launch.stage.getScene().setRoot(root);
            } catch (IOException error) {
                error.printStackTrace();
            }
        });
        Label a = new Label(g.getGroupName());
        a.getStyleClass().add("groupName");
        Label b;
        ArrayList<Message> tempMessages = DBAdd.getMessages(g.getGroupName());
        if (tempMessages.size() > 0) {
            b = new Label(tempMessages.get(tempMessages.size() - 1).getMessage());
        } else {
            b = new Label("");
        }
        b.getStyleClass().add("groupInfo");
        if (current.getGroupName().equals(g.getGroupName())) {
            temp.getStyleClass().add("groupBoxSelected");
        } else {
            temp.getStyleClass().add("groupBox");
        }
        temp.getChildren().add(a);
        temp.getChildren().add(b);

        return temp;
    }

    public void sendClicked(MouseEvent mouseEvent) throws IOException {
        send();
    }

    public void createGroup(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/createGroup.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }

    public void addMembers(MouseEvent mouseEvent) throws IOException {
        if (Launch.stage == Launch.primary) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/addToGroup.fxml"));
            Launch.newWindow(root, new Stage());
            Launch.stage.setAlwaysOnTop(true);
        }
    }
}
