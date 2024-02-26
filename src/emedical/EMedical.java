package emedical;

import static emedical.ConnectDB.PdbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class EMedical extends Application {

    static Logger  logger = Logger.getLogger("eMedicalLog");
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        // Log
        FileHandler fh;  

        try {  
            fh = new FileHandler("MyLogFile.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        }
        
        Connection conn = ConnectDB.ConnectDB();
        
        if (conn != null) {
            PreparedStatement preparedStatementdrop = PdbConnection.prepareStatement("SELECT droptables();");
            preparedStatementdrop.executeQuery();

            PreparedStatement preparedStatementcrt = PdbConnection.prepareStatement("SELECT filltables();");
            preparedStatementcrt.executeQuery();

            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
            Scene scene = new Scene(root);

            stage.setTitle("eMedical");
            stage.getIcons().add(new Image("img/favicon.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            
            alert.setContentText("Connection with DataBase Successful!");

            alert.showAndWait();
        } else {
            logger.info("ERROR WITH DB CONNECTION");
            
            Alert alert = new Alert(AlertType.ERROR);
            
            alert.setContentText("Ooops, Error with DataBase Connection!");

            alert.showAndWait();
        }
        
    }

    public static void main(String[] args) {
        
        launch(args);
    }
    
}
