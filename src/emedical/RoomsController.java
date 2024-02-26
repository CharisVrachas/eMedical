package emedical;

import static emedical.ConnectDB.PdbConnection;
import static emedical.FXMLDocumentController.tmp_login_email;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RoomsController implements Initializable {

    private Parent home;
    private Stage home_stage;
    private Scene home_scene;
    
    private Parent duty;
    private Stage duty_stage;
    private Scene duty_scene;
    
    private Parent medicine;
    private Stage medicine_stage;
    private Scene medicine_scene;
    
    private Parent patients;
    private Stage patients_stage;
    private Scene patients_scene;
    @FXML
    private AnchorPane cardiology_a;
    @FXML
    private Text text_a;
    @FXML
    private AnchorPane cardiology_b;
    @FXML
    private Text text_b;
    @FXML
    private AnchorPane cardiology_c;
    @FXML
    private Text text_c;
    @FXML
    private AnchorPane pathology_b;
    @FXML
    private Text p_b;
    @FXML
    private AnchorPane pathology_a;
    @FXML
    private Text p_a;
    @FXML
    private AnchorPane pathology_c;
    @FXML
    private Text p_c;
    @FXML
    private AnchorPane surgery_a;
    @FXML
    private Text s_a;
    @FXML
    private Text s_b;
    @FXML
    private Text s_c;
    @FXML
    private AnchorPane surgery_b;
    @FXML
    private AnchorPane surgery_c;
    @FXML
    private Text name_login;
    @FXML
    private Text type_login;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Card Mumber
            PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT fullname, doctor FROM getUsersFromEmail(?);");
            preparedStatement.setString(1, tmp_login_email);
            
            ResultSet resultSet1 = preparedStatement.executeQuery();
            
            resultSet1.next();
            
            if(resultSet1.getBoolean("doctor")) {
                type_login.setText("Doctor");
            } else {
                type_login.setText("Nurse");
            }
            name_login.setText(resultSet1.getString("fullname"));
            
            // Cardiology
            Statement stmt = PdbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT patients FROM getRoomsByRoom('Cardiology');");

            resultSet.next();

            // Cardiology A
            int count_cardiology_a = resultSet.getInt("patients");

            if(count_cardiology_a <= 10) {
                cardiology_a.setStyle("-fx-background-color:  #84c37b");
                text_a.setText("AVAILABLE");
            } else if(count_cardiology_a <= 20) {
                cardiology_a.setStyle("-fx-background-color:   #e8df7e");
                text_a.setText("OCCUPIED");
            } else {
                cardiology_a.setStyle("-fx-background-color:    #d37b7b");
                text_a.setText("N/A");
            }
            
            // Cardiology B
            resultSet.next();

            int count_cardiology_b = resultSet.getInt("patients");

            if(count_cardiology_b <= 10) {
                cardiology_b.setStyle("-fx-background-color:  #84c37b");
                text_b.setText("AVAILABLE");
            } else if(count_cardiology_b <= 20) {
                cardiology_b.setStyle("-fx-background-color:   #e8df7e");
                text_b.setText("OCCUPIED");
            } else {
                cardiology_b.setStyle("-fx-background-color:    #d37b7b");
                text_b.setText("N/A");
            }
            
            // Cardiology C
            resultSet.next();

            int count_cardiology_c = resultSet.getInt("patients");

            if(count_cardiology_c <= 10) {
                cardiology_c.setStyle("-fx-background-color:  #84c37b");
                text_c.setText("AVAILABLE");
            } else if(count_cardiology_c <= 20) {
                cardiology_c.setStyle("-fx-background-color:   #e8df7e");
                text_c.setText("OCCUPIED");
            } else {
                cardiology_c.setStyle("-fx-background-color:    #d37b7b");
                text_c.setText("N/A");
            }
            
            
            // Pathology
            Statement stmt2 = PdbConnection.createStatement();
            ResultSet resultSet2 = stmt2.executeQuery("SELECT patients FROM getRoomsByRoom('Pathology')");

            resultSet2.next();

            // Pathology A
            int count_pathology_a = resultSet2.getInt("patients");

            if(count_pathology_a <= 10) {
                pathology_a.setStyle("-fx-background-color:  #84c37b");
                p_a.setText("AVAILABLE");
            } else if(count_pathology_a <= 20) {
                pathology_a.setStyle("-fx-background-color:   #e8df7e");
                p_a.setText("OCCUPIED");
            } else {
                pathology_a.setStyle("-fx-background-color:    #d37b7b");
                p_a.setText("N/A");
            }
            
            // Pathology B
            resultSet2.next();

            int count_pathology_b = resultSet2.getInt("patients");

            if(count_pathology_b <= 10) {
                pathology_b.setStyle("-fx-background-color:  #84c37b");
                p_b.setText("AVAILABLE");
            } else if(count_pathology_b <= 20) {
                pathology_b.setStyle("-fx-background-color:   #e8df7e");
                p_b.setText("OCCUPIED");
            } else {
                pathology_b.setStyle("-fx-background-color:    #d37b7b");
                p_b.setText("N/A");
            }
            
            // Pathology C
            resultSet2.next();

            int count_pathology_c = resultSet2.getInt("patients");

            if(count_pathology_c <= 10) {
                pathology_c.setStyle("-fx-background-color:  #84c37b");
                p_c.setText("AVAILABLE");
            } else if(count_pathology_c <= 20) {
                pathology_c.setStyle("-fx-background-color:   #e8df7e");
                p_c.setText("OCCUPIED");
            } else {
                pathology_c.setStyle("-fx-background-color:    #d37b7b");
                p_c.setText("N/A");
            }
            
            
            // Surgery
            Statement stmt3 = PdbConnection.createStatement();
            ResultSet resultSet3 = stmt3.executeQuery("SELECT patients FROM getRoomsByRoom('Surgery')");

            resultSet3.next();

            // Surgery A
            int count_surgery_a = resultSet3.getInt("patients");

            if(count_surgery_a <= 10) {
                surgery_a.setStyle("-fx-background-color:  #84c37b");
                s_a.setText("AVAILABLE");
            } else if(count_surgery_a <= 20) {
                surgery_a.setStyle("-fx-background-color:   #e8df7e");
                s_a.setText("OCCUPIED");
            } else {
                surgery_a.setStyle("-fx-background-color:    #d37b7b");
                s_a.setText("N/A");
            }
            
            // Surgery B
            resultSet3.next();

            int count_surgery_b = resultSet3.getInt("patients");

            if(count_surgery_b <= 10) {
                surgery_b.setStyle("-fx-background-color:  #84c37b");
                s_b.setText("AVAILABLE");
            } else if(count_surgery_b <= 20) {
                surgery_b.setStyle("-fx-background-color:   #e8df7e");
                s_b.setText("OCCUPIED");
            } else {
                surgery_b.setStyle("-fx-background-color:    #d37b7b");
                s_b.setText("N/A");
            }
            
            // Surgery C
            resultSet3.next();

            int count_surgery_c = resultSet3.getInt("patients");

            if(count_surgery_c <= 10) {
                surgery_c.setStyle("-fx-background-color:  #84c37b");
                s_c.setText("AVAILABLE");
            } else if(count_surgery_c <= 20) {
                surgery_c.setStyle("-fx-background-color:   #e8df7e");
                s_c.setText("OCCUPIED");
            } else {
                surgery_c.setStyle("-fx-background-color:    #d37b7b");
                s_c.setText("N/A");
            }
            
            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Something Going Wrong...");

            alert.showAndWait();
            
            System.exit(0);
        } 
    }   

    @FXML
    private void home(ActionEvent event) throws IOException {
        home = FXMLLoader.load(getClass().getResource("Home.fxml"));

        home_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        home_scene = new Scene(home);

        home_stage.setTitle("eMedical");
        home_stage.getIcons().add(new Image("img/favicon.png"));
        home_stage.setResizable(false);
        home_stage.setScene(home_scene);
        home_stage.centerOnScreen();
        home_stage.show();
    }

    @FXML
    private void duty(ActionEvent event) throws IOException {
        duty = FXMLLoader.load(getClass().getResource("Duty.fxml"));

        duty_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        duty_scene = new Scene(duty);

        duty_stage.setTitle("eMedical");
        duty_stage.getIcons().add(new Image("img/favicon.png"));
        duty_stage.setResizable(false);
        duty_stage.setScene(duty_scene);
        duty_stage.centerOnScreen();
        duty_stage.show();
    }

    @FXML
    private void medicine(ActionEvent event) throws IOException {
        medicine = FXMLLoader.load(getClass().getResource("Medicine.fxml"));

        medicine_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        medicine_scene = new Scene(medicine);

        medicine_stage.setTitle("eMedical");
        medicine_stage.getIcons().add(new Image("img/favicon.png"));
        medicine_stage.setResizable(false);
        medicine_stage.setScene(medicine_scene);
        medicine_stage.centerOnScreen();
        medicine_stage.show();
    }

    @FXML
    private void patients(ActionEvent event) throws IOException {
        patients = FXMLLoader.load(getClass().getResource("Patients.fxml"));

        patients_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        patients_scene = new Scene(patients);

        patients_stage.setTitle("eMedical");
        patients_stage.getIcons().add(new Image("img/favicon.png"));
        patients_stage.setResizable(false);
        patients_stage.setScene(patients_scene);
        patients_stage.centerOnScreen();
        patients_stage.show();
    }
}
