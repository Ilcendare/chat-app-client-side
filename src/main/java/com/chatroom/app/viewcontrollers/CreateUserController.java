package com.chatroom.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

import com.chatroom.app.animations.InFade;
import com.chatroom.app.animations.OutFade;
// import com.chatroom.app.animations.TranslateButton;
import com.chatroom.app.member.MemberManager;
import com.chatroom.app.utils.EventAnimationHandler;
import com.chatroom.app.utils.SceneHandler;
import com.chatroom.app.utils.Theme;
import com.chatroom.app.utils.ThemeSwitcher;

public class CreateUserController implements Initializable {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button createUserButton;

  @FXML
  private Text textLog;

  @FXML
  private Button goBackButton;

  @FXML
  private Label usernameLabel;

  @FXML
  private Label passwordLabel;

  @FXML
  private Circle themeButton;

  @FXML
  private Text switchThemeLabel;

  private Color successLogColor;

  private Color errorLogColor;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    if (ThemeSwitcher.getCurrentTheme() == Theme.DARK) {
      successLogColor = Color.LIGHTGREEN;
      errorLogColor = Color.LIGHTYELLOW;
    } else {
      successLogColor = Color.DARKGREEN;
      errorLogColor = Color.FIREBRICK;
    }

    textLog.setFill(successLogColor);
    textLog.setText("Create a new user to start chatting!");

    // Set the switch theme label color
    switchThemeLabel.setFill(successLogColor);

    // Set the fade animation for all buttons
    new EventAnimationHandler(new InFade(goBackButton), goBackButton::setOnMouseEntered);
    new EventAnimationHandler(new InFade(createUserButton), createUserButton::setOnMouseEntered);
    new EventAnimationHandler(new InFade(themeButton), themeButton::setOnMouseEntered);

    new EventAnimationHandler(new OutFade(goBackButton), goBackButton::setOnMouseExited);
    new EventAnimationHandler(new OutFade(createUserButton), createUserButton::setOnMouseExited);
    new EventAnimationHandler(new OutFade(themeButton), themeButton::setOnMouseExited);

    // Set the fade animation for all text fields
    new EventAnimationHandler(new InFade(usernameField), usernameField::setOnMouseEntered);
    new EventAnimationHandler(new InFade(passwordField), passwordField::setOnMouseEntered);

    new EventAnimationHandler(new OutFade(usernameField), usernameField::setOnMouseExited);
    new EventAnimationHandler(new OutFade(passwordField), passwordField::setOnMouseExited);

    usernameField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          System.out.println("Enter pressed");
          this.createUser();
        } catch (Exception e) {
          System.out.println("Error ");
        }
      } else {
        textLog.setText("");
      }
    });

    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          System.out.println("Enter pressed");
          this.createUser();
        } catch (Exception e) {
          System.out.println("Error ");
        }
      } else {
        textLog.setText("");
      }
    });
  }

  @FXML
  private void createUser() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Check if the username and password fields are not empty
    if (username.isEmpty() || password.isEmpty()) {
      textLog.setFill(errorLogColor);
      textLog.setText("Please enter a username and password");
    } else {
      // Check if the username and password are valid
      if (username.matches("[a-zA-z0-9]{2,}")
          && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*_]).{8,}$")) {
        try {
          // Create a new member to be registered in the server
          MemberManager.createMember(username, password);
          // Register the member in the server
          if (MemberManager.registerMember()) {
            textLog.setFill(successLogColor);
            textLog.setText("User created successfully");
            MemberManager.clearMember();
            // If the member is registered, set the scene to the login screen
            SceneHandler.switchScene("login");
          } else {
            // If the member is not registered, send a message to the user
            textLog.setFill(errorLogColor);
            textLog.setText("User already exists with that username");
          }
        } catch (Exception e) {
          System.err.println("Error loading login.fxml from CreateUserController: " + e.getMessage());
        }
      } else {
        // If the username and password are not valid, send a message to the user
        textLog.setFill(errorLogColor);
        textLog.setText("Please enter a valid username and password\n" +
            "Password must:\n" +
            "    - contain at least 8 characters\n" +
            "    - contain at least 1 uppercase letter\n" +
            "    - contain at least 1 lowercase letter\n" +
            "    - contain at least 1 number\n" +
            "    - contain at least 1 special character");
      }
    }
  }

  @FXML
  private void goBack() {
    try {
      SceneHandler.switchScene("login");
    } catch (Exception e) {
      System.err.println("Error loading login.fxml from CreateUserController: " + e.getMessage());
    }
  }

  @FXML
  private void switchTheme() {
    Color oldTextColor = (Color) textLog.getFill();

    if (ThemeSwitcher.getCurrentTheme() == Theme.LIGHT) {
      ThemeSwitcher.switchTheme(Theme.DARK);
      if (oldTextColor == successLogColor) {
        successLogColor = Color.LIGHTGREEN;
        errorLogColor = Color.LIGHTYELLOW;
        textLog.setFill(successLogColor);
      } else if (oldTextColor == errorLogColor) {
        errorLogColor = Color.LIGHTYELLOW;
        errorLogColor = Color.LIGHTYELLOW;
        textLog.setFill(errorLogColor);
      }
    } else {
      ThemeSwitcher.switchTheme(Theme.LIGHT);
      if (oldTextColor == successLogColor) {
        successLogColor = Color.DARKGREEN;
        errorLogColor = Color.FIREBRICK;
        textLog.setFill(successLogColor);
      } else if (oldTextColor == errorLogColor) {
        successLogColor = Color.DARKGREEN;
        errorLogColor = Color.FIREBRICK;
        textLog.setFill(errorLogColor);
      }
    }
    switchThemeLabel.setFill(successLogColor);
  }
}
