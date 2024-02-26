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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Remove_usersController implements Initializable {

    @FXML
    private TextField email_remove;
    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private Button delete_user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choice.getItems().addAll("Cardiology");
        choice.getItems().addAll("Pathology");
        choice.getItems().addAll("Surgery");
        choice.getItems().addAll("Null");
        
        choice.setValue("Null");
    }    

    @FXML
    private void delete_user(ActionEvent event) throws SQLException {
        String choice_ = choice.getSelectionModel().getSelectedItem();
        
        if(email_remove.getText() == null || choice_ == "Null") 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT * FROM getDutyFromSE(?,?);");
            preparedStatement.setString(1, email_remove.getText());
            preparedStatement.setString(2, choice_);

            ResultSet resultSet1 = preparedStatement.executeQuery();

            if(resultSet1.next()) {
                String delete = "DELETE FROM Duty WHERE email = ? AND specialization = ?";

                PreparedStatement preparedStatementtmp = PdbConnection.prepareStatement(delete);
                preparedStatementtmp.setString(1, email_remove.getText());
                preparedStatementtmp.setString(2, choice_);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Remove Successful!");
                logger.info("Remove User Successful!");
                alert.showAndWait();

                preparedStatementtmp.executeQuery();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User not exist...");

                alert.showAndWait();
            }
        }
    }
}
