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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    
    private Parent room;
    private Stage room_stage;
    private Scene room_scene;
    
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
    private Text total_patients;
    @FXML
    private Text name_login;
    @FXML
    private Text type_login;
    @FXML
    private Text num_doctor;
    @FXML
    private Text num_nurse;
    @FXML
    private Text cardiology_sum;
    @FXML
    private Text pathology_sum;
    @FXML
    private Text surgery_sum;
    @FXML
    private ImageView img_type;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Patients
            Statement stmt = PdbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) AS patientsCount FROM getPatientsCount();");
            
            resultSet.next();
            
            int count = resultSet.getInt("patientsCount");

            total_patients.setText(Integer.toString(count));
            
            // Card Mumber
            PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT fullname, doctor FROM cardMember(?);");
            preparedStatement.setString(1, tmp_login_email);
            
            ResultSet resultSet1 = preparedStatement.executeQuery();
            
            resultSet1.next();
            
            if(resultSet1.getBoolean("doctor")) {
                type_login.setText("Doctor");
            } else {
                type_login.setText("Nurse");
            }
            name_login.setText(resultSet1.getString("fullname"));
            
            // Card Doctors, Nurses
            Statement stmt1 = PdbConnection.createStatement();
            ResultSet resultSet2 = stmt1.executeQuery("SELECT COUNT(*) AS sumDoctors FROM getDoctorCount();");
            
            resultSet2.next();
            
            int count2 = resultSet2.getInt("sumDoctors");

            num_doctor.setText(Integer.toString(count2));
            
            Statement stmt2 = PdbConnection.createStatement();
            ResultSet resultSet3 = stmt2.executeQuery("SELECT COUNT(*) AS sumNurses FROM getNurseCount();");
            
            resultSet3.next();
            
            int count3 = resultSet3.getInt("sumNurses");

            num_nurse.setText(Integer.toString(count3));
            
            // Cards With Sums
            Statement stmt3 = PdbConnection.createStatement();
            ResultSet resultSet4 = stmt3.executeQuery("SELECT room, SUM(patients) AS sum FROM getRoomCount() GROUP BY room ORDER BY room;");
            
            resultSet4.next();
            cardiology_sum.setText(Integer.toString(resultSet4.getInt("sum")));
            
            resultSet4.next();
            pathology_sum.setText(Integer.toString(resultSet4.getInt("sum")));
            
            resultSet4.next();
            surgery_sum.setText(Integer.toString(resultSet4.getInt("sum")));
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Something Going Wrong...");

            alert.showAndWait();
            
            System.exit(0);
        }
    }    

    @FXML
    private void rooms(ActionEvent event) throws IOException {
        room = FXMLLoader.load(getClass().getResource("Rooms.fxml"));

        room_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        room_scene = new Scene(room);

        room_stage.setTitle("eMedical");
        room_stage.getIcons().add(new Image("img/favicon.png"));
        room_stage.setResizable(false);
        room_stage.setScene(room_scene);
        room_stage.centerOnScreen();
        room_stage.show();
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
