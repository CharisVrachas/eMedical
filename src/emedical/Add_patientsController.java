package emedical;

import static emedical.ConnectDB.PdbConnection;
import static emedical.EMedical.logger;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Add_patientsController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> brand;
    @FXML
    private ChoiceBox<String> room;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        room.getItems().addAll("Null");
        brand.getItems().addAll("Null");
        
        
        // Medicine
        try {
            PreparedStatement preparedStatementmedicine = PdbConnection.prepareStatement("SELECT * FROM getMedicine();");

            ResultSet rowmedicine = preparedStatementmedicine.executeQuery();

            while(rowmedicine.next()) {
                brand.getItems().addAll(rowmedicine.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Rooms
        try {
            PreparedStatement preparedStatement = PdbConnection.prepareStatement("SELECT roomtype FROM getRoomType();");

            ResultSet row = preparedStatement.executeQuery();

            while(row.next()) {
                room.getItems().addAll(row.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        room.setValue("Null");
        brand.setValue("Null");
    }    

    @FXML
    private void add(ActionEvent event) throws SQLException {
        String name_ = name.getText();
        String choice_ = room.getSelectionModel().getSelectedItem();
        String brand_ = brand.getSelectionModel().getSelectedItem();
        
        
        if(choice_.equals("Null") || name_ == null || brand_ == null ) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            PreparedStatement preparedStatementadd = PdbConnection.prepareStatement("SELECT * FROM getPatientsByName(?)");
            preparedStatementadd.setString(1, name_);

            ResultSet resultSetadd = preparedStatementadd.executeQuery();

            if(!resultSetadd.next()) {
                String insert = "SELECT insertPatient(?,?,?);";

                PreparedStatement preparedStatementtmpadd = PdbConnection.prepareStatement(insert);
                preparedStatementtmpadd.setString(1, name_);
                preparedStatementtmpadd.setString(2, brand_);
                preparedStatementtmpadd.setString(3, choice_);

                preparedStatementtmpadd.executeUpdate();
                
                update_DB_rooms();
                              
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Insert Successful!");

                alert.showAndWait();
                
                logger.info("Patient ADD");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Patient exists...");

                alert.showAndWait();
            }
        }
    }
    
    private void update_DB_rooms() throws SQLException {
        PreparedStatement preparedStatementrooms = PdbConnection.prepareStatement("SELECT patients FROM getPatientsByRoomType(?)");
        preparedStatementrooms.setString(1, room.getSelectionModel().getSelectedItem());

        ResultSet resultSetrooms = preparedStatementrooms.executeQuery();
        
        resultSetrooms.next();
        
        int count = resultSetrooms.getInt(1);
        
        // UPDATE Rooms
        count++;
        PreparedStatement preparedStatementtmpupd = PdbConnection.prepareStatement("UPDATE Rooms SET patients = ? WHERE roomtype = ?");
        preparedStatementtmpupd.setInt(1, count);
        preparedStatementtmpupd.setString(2, room.getSelectionModel().getSelectedItem());
        
        preparedStatementtmpupd.executeUpdate();
    }
    
}
