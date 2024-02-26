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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Remove_patientController implements Initializable {

    @FXML
    private Button remove_patient;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> room;
    @FXML
    private ImageView search;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Room Menu
        room.getItems().addAll("Null");
        
        room.setValue("Null");
    }    

    @FXML
    private void delete_user(ActionEvent event) throws SQLException {
        String name_ = name.getText();
        String room_ = room.getSelectionModel().getSelectedItem();
        
        if(room_ == "Null" || name_ == null ) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            String delete = "DELETE FROM Patients WHERE fullname = ?;";

            PreparedStatement preparedStatementdlt = PdbConnection.prepareStatement(delete);
            preparedStatementdlt.setString(1, name_);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText("Delete Successful!");

            alert.showAndWait();
            logger.info("Delete Patient Successful!");
            preparedStatementdlt.executeUpdate();
            
            remove_DB_rooms();
        }
    }
    
    private void remove_DB_rooms() throws SQLException {
        PreparedStatement preparedStatementrooms = PdbConnection.prepareStatement("SELECT patients FROM getPatientsByRoomType(?);");
        preparedStatementrooms.setString(1, room.getSelectionModel().getSelectedItem());

        ResultSet resultSetrooms = preparedStatementrooms.executeQuery();
        
        resultSetrooms.next();
        
        int count = resultSetrooms.getInt(1);
        
        // UPDATE Rooms
        count--;
        PreparedStatement preparedStatementtmpupd = PdbConnection.prepareStatement("UPDATE Rooms SET patients = ? WHERE roomtype = ?");
        preparedStatementtmpupd.setInt(1, count);
        preparedStatementtmpupd.setString(2, room.getSelectionModel().getSelectedItem());
        
        preparedStatementtmpupd.executeUpdate();
    }

    @FXML
    private void search(MouseEvent event) throws SQLException {
        if(name.getText() != null) {
            
            room.getItems().clear();
            room.getItems().addAll("Null");
        
            room.setValue("Null");
            
            PreparedStatement preparedStatementsrc = PdbConnection.prepareStatement("SELECT room FROM getPatientsByName(?);");
            preparedStatementsrc.setString(1, name.getText());

            ResultSet resultSetsrc = preparedStatementsrc.executeQuery();
            
            while(resultSetsrc.next()) {
                room.getItems().addAll(resultSetsrc.getString(1));
            }
            
        }
    }
}
