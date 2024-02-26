package emedical;

import static emedical.ConnectDB.PdbConnection;
import static emedical.EMedical.logger;
import static emedical.FXMLDocumentController.tmp_login_email;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DutyController implements Initializable {
    
    private Parent room;
    private Stage room_stage;
    private Scene room_scene;
    
    private Parent home;
    private Stage home_stage;
    private Scene home_scene;
    
    private Parent medicine;
    private Stage medicine_stage;
    private Scene medicine_scene;
    
    private Parent patients;
    private Stage patients_stage;
    private Scene patients_scene;
    
    private Parent duty;
    private Stage duty_stage;
    private Scene duty_scene;
    
    @FXML
    private ImageView plus;
    @FXML
    private ImageView remove;
    
    private ObservableList<Doctors> data;

    // Doctors
    @FXML
    private TableView<Doctors> tableDoctors;
    @FXML
    private TableColumn<Doctors, String> fullnamedoctortable;
    @FXML
    private TableColumn<Doctors, String> emaildoctortable;
    @FXML
    private TableColumn<Doctors, String> specializationdoctortable;
    @FXML
    private TableColumn<Doctors, String> interndoctortable;
    
    @FXML
    private ImageView plus1;
    @FXML
    private Text name_login;
    @FXML
    private Text type_login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Something Going Wrong...");

            alert.showAndWait();
            logger.info("Something Going Wrong...");
            System.exit(0);
        } 
            
        data = FXCollections.observableArrayList();
        setCellTable();
        try {
            loadDataFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DutyController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void plus(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("New_add_user.fxml"));
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("eMedical - New User");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    private void remove_user(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Remove_users.fxml"));
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("eMedical - Remove User");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    
    public void setCellTable() {
        fullnamedoctortable.setCellValueFactory(new PropertyValueFactory<>("fullnameDoctor"));
        emaildoctortable.setCellValueFactory(new PropertyValueFactory<>("emailDoctor"));
        specializationdoctortable.setCellValueFactory(new PropertyValueFactory<>("specializationDoctor"));
        interndoctortable.setCellValueFactory(new PropertyValueFactory<>("internDoctor"));
    }
    
    public void loadDataFromDatabase() throws SQLException {
        PreparedStatement preparedStatementdata = PdbConnection.prepareStatement("SELECT * FROM Duty;");
        
        ResultSet row = preparedStatementdata.executeQuery();
        
        while(row.next()) {
            data.add(new Doctors(row.getString(1), row.getString(2), row.getString(3), row.getString(4)));
        }
        
        tableDoctors.setItems(data);
    }

    @FXML
    private void refresh(MouseEvent event) throws SQLException {
        data = FXCollections.observableArrayList();
        setCellTable();
        try {
            loadDataFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DutyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
