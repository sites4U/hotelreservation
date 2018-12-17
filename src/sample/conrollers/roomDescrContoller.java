package sample.conrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class roomDescrContoller implements Initializable {


    @FXML
    private Button cancelBtn;

    @FXML
    private AnchorPane singleRoom;

    @FXML
    private ImageView photoImage;

    @FXML
    private Slider photoSlider;

    @FXML
    private AnchorPane doubleRoom;

    @FXML
    private Slider photoSlider1;

    @FXML
    private ImageView photoImage1;

    @FXML
    private RadioButton rbSRoom;

    @FXML
    private RadioButton rbDRoom;

    @FXML
    void clicked(MouseEvent event) {

    }

    @FXML
    void close(MouseEvent event) {
        CancelButton();
    }

    @FXML
    void doubleR(MouseEvent event) {
        singleRoom.setVisible(false);
        doubleRoom.setVisible(true);
    }

    @FXML
    void singleR(MouseEvent event) {
        singleRoom.setVisible(true);
        doubleRoom.setVisible(false);
    }

    private Image[] roomImage ={
            new Image("sample/assets/room_single_01.jpg"),
            new Image("sample/assets/room_single_02.jpg"),
            new Image("sample/assets/room_double_01.jpg"),
            new Image("sample/assets/room_double_02.jpg")
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        photoSlider.setMin(0);
        photoSlider.setValue(10);
        photoSlider.setMax(20);
        photoSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            photoImage.setImage(roomImage[0]);
            photoImage.setImage(roomImage[1]);
        });

//        photoSlider.setShowTickLabels(true);
//        photoSlider.setShowTickMarks(true);


        singleRoom.setVisible(true);
        ToggleGroup group = new ToggleGroup();
         //Single Room
         rbSRoom.setToggleGroup(group);
         rbSRoom.setSelected(true);
         //Double Room
        rbDRoom.setToggleGroup(group);

    }


    // Close window Cancel button
    private void CancelButton() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}

