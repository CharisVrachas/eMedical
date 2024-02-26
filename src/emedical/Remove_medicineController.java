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
import javafx.scene.control.TextField;

public class Remove_medicineController implements Initializable {

    @FXML
    private Button delete_medicine;
    @FXML
    private TextField name_remove;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void delete_medicine(ActionEvent event) throws SQLException {
        String name_ = name_remove.getText();
        
        if(name_ == null) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill Cell!");

            alert.showAndWait();
        } else {
            PreparedStatement preparedStatementrem = PdbConnection.prepareStatement("SELECT * FROM getMedicine(?);");
            preparedStatementrem.setString(1, name_);

            ResultSet resultSetrem = preparedStatementrem.executeQuery();

            if(resultSetrem.next()) {
                String delete = "DELETE FROM Medicine WHERE name = ?";

                PreparedStatement preparedStatementtmp = PdbConnection.prepareStatement(delete);
                preparedStatementtmp.setString(1, name_);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Remove Successful!");
                logger.info("Remove Medecine OK");
                alert.showAndWait();
                
                preparedStatementtmp.executeQuery();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Medicine not exist...");

                alert.showAndWait();
            }
        }
    }
    
}
