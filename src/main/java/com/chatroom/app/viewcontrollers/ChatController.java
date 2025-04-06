package com.chatroom.app.viewcontrollers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.chatroom.app.animations.InFade;
import com.chatroom.app.animations.OutFade;
import com.chatroom.app.controllers.MemberChatUIWrapper;
import com.chatroom.app.controllers.MembersListUpdater;
import com.chatroom.app.member.MemberManager;
import com.chatroom.app.memberdata.MemberContainer;
import com.chatroom.app.memberdata.Message;
import com.chatroom.app.server.ServerConnectionHandler;
import com.chatroom.app.utils.EventAnimationHandler;
import com.chatroom.app.utils.SceneHandler;
import com.chatroom.app.utils.Theme;
import com.chatroom.app.utils.ThemeSwitcher;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

public class ChatController implements Initializable {
  @FXML
  private Text showUsername;

  @FXML
  private Text showStatus;

  @FXML
  private Button logoutButton;

  @FXML
  private TextArea msgArea;
  // private VBox msgArea;

  @FXML
  private TextField msgField;

  @FXML
  private Button msgSendButton;

  @FXML
  private Text currentMembers;

  @FXML
  private Text textLog;

  @FXML
  private VBox currentMembersBox;

  @FXML
  private Circle themeButton;

  @FXML
  private Text switchThemeLabel;

  private Color errorLogColor;

  private Color blueLogColor;

  private Color greenLogColor;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      // Bind the chat controller to the member
      new MemberChatUIWrapper(this);

      // Update the room members
      MembersListUpdater.getInstance()
          .updateMembersList(ServerConnectionHandler.getInstance().getServer().getAllMembers());

      // Get all the messages from the server
      ArrayList<Message> messages = ServerConnectionHandler.getInstance().getServer().getAllMessages();
      for (Message message : messages) {
        showMessage(message);
      }

      // Set the colors for the text
      if (ThemeSwitcher.getCurrentTheme() == Theme.LIGHT) {
        blueLogColor = Color.DARKBLUE;
        greenLogColor = Color.DARKGREEN;
        errorLogColor = Color.FIREBRICK;
      } else {
        blueLogColor = Color.LIGHTBLUE;
        greenLogColor = Color.LIGHTGREEN;
        errorLogColor = Color.LIGHTYELLOW;
      }

      // Set the current members text
      currentMembers.setFill(blueLogColor);
      currentMembers.setFont(new Font("tahoma", 18));
      currentMembers.setText("Members in the room:");

      // Set the username text
      showUsername.setFill(blueLogColor);
      showUsername.setFont(new Font("tahoma", 18));
      showUsername.setText("username: " + MemberManager.getMember().getMemberData().getUsername());

      // Set the status text
      showStatus.setFill(greenLogColor);
      showStatus.setFont(new Font("tahoma", 18));
      showStatus.setText("status: connected");

      // Set the switch theme label color
      switchThemeLabel.setFill(greenLogColor);

      // set the fade animation for all buttons
      new EventAnimationHandler(new InFade(logoutButton), logoutButton::setOnMouseEntered);
      new EventAnimationHandler(new InFade(msgSendButton), msgSendButton::setOnMouseEntered);
      new EventAnimationHandler(new InFade(themeButton), themeButton::setOnMouseEntered);

      new EventAnimationHandler(new OutFade(logoutButton), logoutButton::setOnMouseExited);
      new EventAnimationHandler(new OutFade(msgSendButton), msgSendButton::setOnMouseExited);
      new EventAnimationHandler(new OutFade(themeButton), themeButton::setOnMouseExited);

      // Set the fade animation for the message text field
      new EventAnimationHandler(new InFade(msgField), msgField::setOnMouseEntered);
      new EventAnimationHandler(new OutFade(msgField), msgField::setOnMouseExited);

      msgField.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          try {
            this.sendMessage();
          } catch (Exception e) {
          }
        } else {
          textLog.setText("");
        }
      });

    } catch (Exception e) {
      System.err.println("ChatController: initialize: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @FXML
  private void logout() throws IOException {
    // Alert the user that they are logging out
    Alert alertWindow = new Alert(AlertType.CONFIRMATION);
    alertWindow.setTitle("Logout");
    alertWindow.setHeaderText("Are you sure you want to logout?");
    Optional<ButtonType> result = alertWindow.showAndWait();

    // if the user clicks yes, logout the member
    if (result.isPresent() && result.get() == ButtonType.OK) {
      // logout the member
      MemberManager.logoutMember();
      // set the scene root to the login window
      SceneHandler.switchScene("login");
    }
  }

  @FXML
  private void sendMessage() throws IOException {
    // if the message field is empty, set the text log to the error message
    if (msgField.getText().isEmpty()) {
      textLog.setFill(errorLogColor);
      textLog.setText("Please enter a message");
      return;
    }

    // create a new message
    String messageText = msgField.getText();
    Timestamp timeSent = new Timestamp(System.currentTimeMillis());
    Message message = new Message(messageText, MemberManager.getMember().getMemberData(), timeSent);

    // clear the text log
    textLog.setText("");

    // send the message to the server
    ServerConnectionHandler.getInstance().getServer().publish(message);

    // append the message to the message area
    // showMessage(message);

    // clear the message field
    msgField.clear();
  }

  @FXML
  private void switchTheme() {
    Color oldTextColor = (Color) textLog.getFill();

    if (ThemeSwitcher.getCurrentTheme() == Theme.LIGHT) {
      ThemeSwitcher.switchTheme(Theme.DARK);
      if (oldTextColor == errorLogColor) {
        errorLogColor = Color.LIGHTYELLOW;
      }
      blueLogColor = Color.LIGHTBLUE;
      greenLogColor = Color.LIGHTGREEN;
      errorLogColor = Color.LIGHTYELLOW;
    } else {
      ThemeSwitcher.switchTheme(Theme.LIGHT);
      if (oldTextColor == errorLogColor) {
        errorLogColor = Color.FIREBRICK;
      }
      blueLogColor = Color.DARKBLUE;
      greenLogColor = Color.DARKGREEN;
      errorLogColor = Color.FIREBRICK;
    }
    textLog.setFill(errorLogColor);
    currentMembers.setFill(blueLogColor);
    showUsername.setFill(blueLogColor);
    showStatus.setFill(greenLogColor);
    switchThemeLabel.setFill(greenLogColor);

    for (Node node : currentMembersBox.getChildren()) {
      Text memberText = (Text) node;
      memberText.setFill(blueLogColor);
    }
  }

  public void updateMembersList(List<MemberContainer> memberList) {
    Platform.runLater(() -> {
      // Clear the current members box
      currentMembersBox.getChildren().clear();
      try {
        // Add the current members to the current members box
        for (MemberContainer member : memberList) {
          if (member != null) {
            Text memberText = new Text(member.getMemberData().getUsername());
            memberText.getStyleClass().add("text-log");
            memberText.setFill(blueLogColor);
            currentMembersBox.getChildren().add(memberText);
          }
        }
      } catch (Exception e) {
        System.err.println("ChatController: updateRoomMembers: " + e.getMessage());
      }
    });
  }

  public void showMessage(Message message) {
    // if the message is not null, append it to the message area
    if (message != null) {
      if (message.getSender() != null) {
        try {
          String msgContent = "[" + ((message.getSender().getId() != MemberManager.getMember().getMemberData().getId())
              ? message.getSender().getUsername()
              : "You") + "]: " + message.getMessage();

          msgArea.appendText(msgContent + "\n\n");
        } catch (Exception e) {
          System.err.println("ChatController: showMessage: " + e.getMessage());
        }
      } else {
        msgArea.appendText("[SYSTEM]: " + message.getMessage() + "\n");
      }
    }
  }
}
