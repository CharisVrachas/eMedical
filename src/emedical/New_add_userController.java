package emedical;

import static emedical.ConnectDB.PdbConnection;
import static emedical.EMedical.logger;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class New_add_userController implements Initializable {

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private TextField fullname;
    @FXML
    private TextField email;
    @FXML
    private RadioButton yes;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choice.getItems().addAll("Cardiology");
        choice.getItems().addAll("Pathology");
        choice.getItems().addAll("Surgery");
        choice.getItems().addAll("Null");
        
        choice.setValue("Null");
    }    

    @FXML
    private void insert(ActionEvent event) throws SQLException {
        String fullname_ = fullname.getText();
        String email_ = email.getText();
        String choice_ = choice.getSelectionModel().getSelectedItem();
        String itern_ = "No";
        
        if(yes.isSelected()) {
            itern_ = "Yes";
        }
        
        if(email.getText() == null || fullname.getText().trim().isEmpty() || choice_ == "Null") 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT * FROM getUsersFromEmail(?)");
            preparedStatement.setString(1, email.getText());

            ResultSet resultSet1 = preparedStatement.executeQuery();

            if(resultSet1.next()) {
                String insert = "SELECT insertDuty(?,?,?,?);";

                PreparedStatement preparedStatementtmp = PdbConnection.prepareStatement(insert);
                preparedStatementtmp.setString(1, fullname.getText());
                preparedStatementtmp.setString(2, email.getText());
                preparedStatementtmp.setString(3, choice_);
                preparedStatementtmp.setString(4, itern_);

                preparedStatementtmp.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Insert Successful!");
                logger.info("Insert Successful!");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User not exist...");

                alert.showAndWait();
            }
        }
    }
}
