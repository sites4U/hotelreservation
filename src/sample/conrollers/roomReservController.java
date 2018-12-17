package sample.conrollers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class roomReservController implements Initializable {


    @FXML
    private Button reservationBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker dateCheckIn;

    @FXML
    private DatePicker dateCheckOut;

    @FXML
    protected ComboBox<String> cmbRoomType;

    @FXML
    protected ComboBox<Integer> cmbFloor;

    @FXML
    protected ComboBox<String> cmbRequirement;

    @FXML
    protected ComboBox<Integer> cmbRoom;

    @FXML
    private ComboBox<Customer> cmbGuest;

    @FXML
    private TextField txtTotalPeople;

    @FXML
    void reservation(MouseEvent event) {

        Connection connection = DbConnect.getInstance().getConnection();

        try {
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd"); // ISO format

            Date dateIn = Date.valueOf(dateCheckIn.getValue());
            Date dateOut = Date.valueOf(dateCheckOut.getValue());
            String roomType = cmbRoomType.getValue();
            Integer floorNum = cmbFloor.getValue();
            Customer guest = cmbGuest.getValue();
            String requir = cmbRequirement.getValue();
            Integer number_room = cmbRoom.getValue();
            String peopleTotal = txtTotalPeople.getText();



            PreparedStatement statement = connection.prepareStatement("INSERT INTO reservation (date_in,date_out,type_room, floor, guest,requirements,number_room, total_people) values (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, dformat.format(dateIn));
            statement.setString(2, dformat.format(dateOut));
            statement.setString(3, roomType);
            statement.setInt(4,floorNum);
            statement.setString(5, guest.toString());
            statement.setString(6, requir);
            statement.setInt(7, number_room);
            statement.setString(8, peopleTotal);
            // TODO rest of the params
            int status = statement.executeUpdate();


            if(status > 0 ){
                //System.out.println("Customer registered");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Room reservation");
                alert.setContentText("ROOM SUCCESSFULLY RESERVED TO THE DATABASE OF THE HOTEL!");

                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    List<Customer> guests = new ArrayList<>();
    List<String> rooms = new ArrayList<>();
    List<String> requirement = new ArrayList<>();
    List<Integer> roomsNumb = new ArrayList<>();
    List<Integer> floors = new ArrayList<>();

    private class Customer
    {
        public String firstName;
        public String lastName;
        public Customer(String firstName, String lastName)
        {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString()
        {
            return firstName + " " + lastName;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = DbConnect.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT first_name, last_name FROM customers");
            while(resultSet.next()){
                guests.add( new Customer(resultSet.getString("first_name"), resultSet.getString("last_name")));
                cmbGuest.setItems(FXCollections.observableArrayList(guests));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT discription FROM room_type");
            while(resultSet.next()){
                rooms.add(resultSet.getString("discription"));
                cmbRoomType.setItems(FXCollections.observableArrayList(rooms));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT smoke FROM room_type");
            while(resultSet.next()){
                requirement.add(resultSet.getString("smoke"));
                cmbRequirement.setItems(FXCollections.observableArrayList(requirement));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT number FROM rooms WHERE floor = 1");
            while(resultSet.next()){
                roomsNumb.add(resultSet.getInt("number"));
                cmbRoom.setItems(FXCollections.observableArrayList(roomsNumb));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT DISTINCT floor FROM rooms");
            while(resultSet.next()){
                floors.add(resultSet.getInt("floor"));
                cmbFloor.setItems(FXCollections.observableArrayList(floors));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



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

