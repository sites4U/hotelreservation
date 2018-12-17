package sample.conrollers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

import sample.helpers.DbConnect;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class userRegisterController implements Initializable {


    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_country;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField tf_passport;

    @FXML
    private TextField tf_phone;

    @FXML
    private DatePicker dateDOB;

    @FXML
    private TextField tf_lastName;

    @FXML
    private TextField tf_email;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    void registration(MouseEvent event) {

        Connection connection = DbConnect.getInstance().getConnection();

        try {
            String firstName = tf_firstName.getText();
            String lastName = tf_lastName.getText();
            String country = tf_country.getText();
            String phone = tf_phone.getText();
            String passport = tf_passport.getText();
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd"); // ISO format
            Date d = Date.valueOf(dateDOB.getValue());
            String dob = dformat.format(d);
            String email = tf_email.getText();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO customers (first_name,last_name,country,phone,passport,email,dob, gender) VALUES(?,?,?,?,?,?,?,?)");

            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,country);
            statement.setString(4,phone);
            statement.setString(5,passport);
            statement.setString(6,dob);
            statement.setString(7,email);
            statement.setString(8,gender);

            int status = statement.executeUpdate();

            if(status > 0 ){
                //System.out.println("Customer registered");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Guest registration");
                alert.setContentText("GUEST SUCCESSFULLY ADDED TO THE DATABASE OF THE HOTEL!");

                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String gender = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                //Has selection
                if(group.getSelectedToggle()!=null){
                    RadioButton button = (RadioButton) group.getSelectedToggle();
                    if(rbMale.isSelected()){
                        gender = "Male";
                    }
                    else if(rbFemale.isSelected()){
                        gender = "Female";
                    }
                }
            }
        });

       //Radio : Male
        rbMale.setToggleGroup(group);
        rbMale.setSelected(true);

        //Radio : Female
        rbFemale.setToggleGroup(group);

        cancelBtn.setOnAction(event -> {
            CancelButton();
        });

    }

    // Close window Cancel button
    private void CancelButton() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}