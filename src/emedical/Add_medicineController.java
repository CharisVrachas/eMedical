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
import javafx.scene.control.TextField;

public class Add_medicineController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField brand;
    @FXML
    private ChoiceBox<String> stock;
    @FXML
    private TextField number;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stock.getItems().addAll("Yes");
        stock.getItems().addAll("No");
        
        stock.setValue("Null");
    }    

    @FXML
    private void add(ActionEvent event) throws SQLException {
        String name_ = name.getText();
        String brand_ = brand.getText();
        String choice_ = stock.getSelectionModel().getSelectedItem();
        
        if(choice_.equals("Null") || name_ == null || brand_ == null || (number.getText() == null || number.getText().trim().isEmpty())) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Fill All Cells!");

            alert.showAndWait();
        } else {
            int number_ = Integer.parseInt(number.getText());
            
            PreparedStatement preparedStatementadd = PdbConnection.prepareStatement("SELECT * FROM getMedicine(?);");
            preparedStatementadd.setString(1, name_);

            ResultSet resultSetadd = preparedStatementadd.executeQuery();

            if(!resultSetadd.next()) {
                String insert = "SELECT insertMedicine(?,?,?,?);";

                PreparedStatement preparedStatementtmpadd = PdbConnection.prepareStatement(insert);
                preparedStatementtmpadd.setString(1, name.getText());
                preparedStatementtmpadd.setString(2, brand.getText());
                preparedStatementtmpadd.setString(3, choice_);
                preparedStatementtmpadd.setInt(4, number_);

                preparedStatementtmpadd.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Insert Successful!");

                alert.showAndWait();
                
                logger.info("Medicine ADD");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Medice exists...");

                alert.showAndWait();
            }
        }
    }
    
}
