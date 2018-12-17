package sample.conrollers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

public class mainWinController implements Initializable {


    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnRoomDetails;

    @FXML
    private Button btnCloseWin;

    @FXML
    private TableView<Reservation> infoTable;

    @FXML
    private TableColumn<Reservation, String> dateInCol;

    @FXML
    private TableColumn<Reservation, String> dateOutCol;

    @FXML
    private TableColumn<Reservation, String> fullNameCol;

    @FXML
    private TableColumn<Reservation, String> roomTypeCol;

    @FXML
    private TableColumn<Reservation, Integer> numberOfRoomCol;

    @FXML
    private TableColumn<Reservation, String> requirementCol;

    @FXML
    void deleteReserv(MouseEvent event) {
        DeleteInfo();
    }

    @FXML
    void editReserv(MouseEvent event) {

    }


    private ObservableList<Reservation> reservData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initData(); // enter data to TableView

        dateInCol.setCellValueFactory(new PropertyValueFactory<Reservation,String>("dataIn"));
        dateOutCol.setCellValueFactory(new PropertyValueFactory<Reservation,String>("dataOut"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<Reservation,String>("fullName"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<Reservation,String>("roomType"));
        numberOfRoomCol.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("roomNumber"));
        requirementCol.setCellValueFactory(new PropertyValueFactory<Reservation,String>("roomReq"));

        infoTable.setItems(reservData);


        btnRegistration.setOnAction(event -> {
            OpenWindowRegistration();
        });

        btnReservation.setOnAction(event -> {
            OpenWindowReservation();
        });

        btnCloseWin.setOnAction(event -> {
            CloseButton();
        });

        btnRoomDetails.setOnAction(event -> {
            OpenRoomDescription();
        });


    }

    private void initData(){
        Connection connection = DbConnect.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM reservation");
            while (rs.next()) {
                reservData.add(new Reservation(rs.getString("date_in"), rs.getString("date_out"),
                                rs.getString("guest"),rs.getString("type_room"),
                                rs.getInt("number_room"),
                                rs.getString("requirements")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void DeleteInfo(){
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Delete Alert");
            alert.setContentText("Are you sure?");

             Optional<ButtonType> result = alert.showAndWait();
             if(result.get() == ButtonType.OK){
                 infoTable.getItems().removeAll(infoTable.getSelectionModel().getSelectedItems());
             } else if (result.get() == ButtonType.CANCEL){
                 alert.close();
             }
        
        
        

        
//        Connection connection = DbConnect.getInstance().getConnection();
//
//        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1,id);
//            preparedStatement.executeUpdate();
//
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }

    public class Reservation{


        private String  dataIn;
        private String dataOut;
        private String fullName;
        private String roomType;
        private Integer roomNumber;
        private String roomReq;

        public Reservation(String dataIn, String dataOut, String fullName, String roomType, int roomNumber,
                           String roomReq)
        {
            this.dataIn = dataIn;
            this.dataOut = dataOut;
            this.fullName = fullName;
            this.roomType = roomType;
            this.roomNumber = roomNumber;
            this.roomReq = roomReq;
        }

        public Reservation(){

        }

        public String getDataIn(){
            return dataIn;
        }

        public String getDataOut(){
            return dataOut;
        }

        public String getFullName(){
            return  fullName;
        }

        public String getRoomType(){
            return roomType;
        }

        public int getRoomNumber(){
            return roomNumber;
        }

        public String getRoomReq(){
            return roomReq;
        }

        public void setDataIn(String dataIn){
            this.dataIn = dataIn;
        }

        public void setDataOut(String dataOut){
            this.dataOut = dataOut;
        }

        public void setFullName(String fullName){
            this.fullName = fullName;
        }

        public void setRoomType(String roomType){
            this.roomType = roomType;
        }

        public void setRoomNumber(int roomNumber){
            this.roomNumber = roomNumber;
        }

        public void setRoomReq(String roomReq){
            this.roomReq = roomReq;
        }
    }



    public void OpenWindowRegistration(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/userRegister.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Guest Registration");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void OpenWindowReservation(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/roomReserv.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Room Reservation");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void OpenRoomDescription(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/roomDescription.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Room Details");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void CloseButton() {
        Stage stage = (Stage) btnCloseWin.getScene().getWindow();
        stage.close();
    }



}

