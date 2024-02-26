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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Edit_medicineController implements Initializable {

    @FXML
    private TextField number_stock;
    @FXML
    private ComboBox<String> menu_name;
    @FXML
    private ChoiceBox<String> menu_brand;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Name Menu
        menu_name.getItems().addAll("Null");
        
        try {
            PreparedStatement preparedStatementdata = PdbConnection.prepareStatement("SELECT name FROM getAllMedicines();");

            ResultSet row = preparedStatementdata.executeQuery();

            while(row.next()) {
                menu_name.getItems().addAll(row.getString(1));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Something Going Wrong...");

            alert.showAndWait();
            logger.info("Something Going Wrong...");
            System.exit(0);
        } 

        menu_name.setValue("Null");
        
    }    

    @FXML
    private void edit(ActionEvent event) throws SQLException {
        String choice_ = menu_name.getValue();
        
        if(choice_ == "Null" || (number_stock.getText() == null || number_stock.getText().trim().isEmpty()) || menu_brand.getSelectionModel().getSelectedItem() == "Null") 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            int num = Integer.parseInt(number_stock.getText());
                       
            String stock = "Yes";
            
            if(num <= 0) {
                stock = "No";
            }

            String insert = "UPDATE Medicine SET stockno = ?, instock = ? WHERE name = ? AND brand = ?;";

            PreparedStatement preparedStatementtmp = PdbConnection.prepareStatement(insert);
            preparedStatementtmp.setInt(1, num);
            preparedStatementtmp.setString(2, stock);
            preparedStatementtmp.setString(3, choice_);
            preparedStatementtmp.setString(4, menu_brand.getSelectionModel().getSelectedItem());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText("Update Successful!");

            alert.showAndWait();
            logger.info("Update Medicine OK");
            preparedStatementtmp.executeUpdate();

        }
    }

    @FXML
    private void change_brand(ActionEvent event) throws SQLException {
        if(menu_name.getSelectionModel().getSelectedItem() != "Null") {
            menu_brand.getItems().clear();
            menu_brand.getItems().addAll("Null");
        
            menu_brand.setValue("Null");
            
            PreparedStatement preparedStatementbrand = PdbConnection.prepareStatement("SELECT brand FROM getMedicine(?)");
            preparedStatementbrand.setString(1, menu_name.getSelectionModel().getSelectedItem());

            ResultSet rowbrand = preparedStatementbrand.executeQuery();

            while(rowbrand.next()) {
                menu_brand.getItems().addAll(rowbrand.getString(1));
            }
        } 
    }
    
}
