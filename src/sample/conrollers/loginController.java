package sample.conrollers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.animations.Shake;
import sample.helpers.DbConnect;


public class loginController implements Initializable {

   @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    @FXML
    private Label wrongLogin;


    @FXML
    void enter(MouseEvent event) {

        String username, password;

        username = loginField.getText();
        password = passwordField.getText();

        Connection connection = DbConnect.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM employees WHERE user_name = '"+username+"' and user_password = '"+password+"'");
            if(resultSet.next()){
                OpenWindow();
            }else{
                wrongLogin.setText("Oops...Login or password is wrong!");
                Shake userLoginAnim = new Shake(loginField);
                Shake userPassAnim = new Shake(passwordField);
                userLoginAnim.playAnim();
                userPassAnim.playAnim();
            }
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enterButton.setDisable(true);


        loginField.setOnKeyReleased(event -> {
            keyRealisedProperty();
        });

        passwordField.setOnKeyReleased(event -> {
            keyRealisedProperty();
        });

    }

    public void OpenWindow(){
        enterButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/mainWindow.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Main Window");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public void keyRealisedProperty(){
        String un = loginField.getText();
        String ps = passwordField.getText();
        // userName and Password
        boolean isDisabled = (un.isEmpty()||un.trim().isEmpty() || un.length() < 5)||(ps.isEmpty()||ps.trim().isEmpty()
                || ps.length() < 5);
        enterButton.setDisable(isDisabled);
    }

}

