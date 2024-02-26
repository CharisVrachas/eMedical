package emedical;

import static emedical.ConnectDB.PdbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    private Parent register;
    private Stage register_stage;
    private Scene register_scene;
    
    private Parent home;
    private Stage home_stage;
    private Scene home_scene;
    
    @FXML
    private RadioButton doctor;
    @FXML
    private TextField fullname;
    @FXML
    private TextField  pass;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private RadioButton nurse;
    @FXML
    private TextField login_email;
    @FXML
    private TextField  login_pass;
    @FXML
    private ToggleGroup spiecies;
    
    public static String tmp_login_email = null;
    
    public String getLogin_email() {
        return login_email.getText();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void register(ActionEvent event) throws IOException {
        register = FXMLLoader.load(getClass().getResource("Register.fxml"));
        
        register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        register_scene = new Scene(register);
        
        register_stage.setTitle("eMedical");
        register_stage.getIcons().add(new Image("img/favicon.png"));
        register_stage.setResizable(false);
        register_stage.setScene(register_scene);
        register_stage.centerOnScreen();
        register_stage.show();
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        Parent register = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        register_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        register_scene = new Scene(register);
        
        register_stage.setTitle("eMedical");
        register_stage.getIcons().add(new Image("img/favicon.png"));
        register_stage.setResizable(false);
        register_stage.setScene(register_scene);
        register_stage.centerOnScreen();
        register_stage.show();
    }

    @FXML
    private void register_in_system(ActionEvent event) throws SQLException {
        
        if((fullname.getText() == null || fullname.getText().trim().isEmpty())
            || (username.getText() == null || username.getText().trim().isEmpty())
            || (email.getText() == null || email.getText().trim().isEmpty())
            || (pass.getText() == null || pass.getText().trim().isEmpty())
            || (!(doctor.isSelected()) && !(nurse.isSelected()))) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            PreparedStatement preparedStatementtmp = PdbConnection.prepareStatement("SELECT * FROM getUsersFromEmail(?)");
        
            preparedStatementtmp.setString(1, email.getText());

            ResultSet resultSettmp = preparedStatementtmp.executeQuery();

            if(resultSettmp.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("The Email has existed!");

                alert.showAndWait();
            } else {
                boolean check_doctor = false;
                
                if(doctor.isSelected()) {
                    check_doctor = true;
                }

                try {
                    String insert = "SELECT insertUsers(?, ?, ?, ?, ?);";

                    PreparedStatement preparedStatement = PdbConnection.prepareStatement(insert);
                    preparedStatement.setString(1, fullname.getText());
                    preparedStatement.setString(2, username.getText());
                    preparedStatement.setString(3, email.getText());
                    preparedStatement.setString(4, pass.getText());
                    preparedStatement.setBoolean(5, check_doctor);

                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setContentText("Register Successful!");

                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setContentText("Fail with Register, Try again later...");

                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void login_in_system(ActionEvent event) throws SQLException, IOException {
        PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT COUNT(*) FROM getUsersFromEmailPass(?, ?);");
        
        preparedStatement.setString(1, login_email.getText());
        preparedStatement.setString(2, login_pass.getText());
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if(resultSet.next()) {
            if(resultSet.getInt(1) == 1) {
                tmp_login_email = login_email.getText();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
                alert.setContentText("Login Successful!");

                alert.showAndWait();
                
                home = FXMLLoader.load(getClass().getResource("Home.fxml"));

                home_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                home_scene = new Scene(home);

                home_stage.setTitle("eMedical");
                home_stage.getIcons().add(new Image("img/favicon.png"));
                home_stage.setResizable(false);
                home_stage.setScene(home_scene);
                home_stage.centerOnScreen();
                home_stage.show();
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Username or Password was wrong...");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Username or Password was wrong...");

            alert.showAndWait();
        }
    }
    
}
