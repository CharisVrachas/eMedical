package emedical;

import static emedical.EMedical.logger;
import java.sql.*;

public class ConnectDB {
    
    static String PdriverClassName = "org.postgresql.Driver";
    static String Purl = "jdbc:postgresql://dblabs.iee.ihu.gr:5432/it185157";
    static Connection PdbConnection = null;
    static String Pusername = "it185157";
    static String Ppasswd = "technology";
    
    public static Connection ConnectDB() throws ClassNotFoundException {
        try {
            Class.forName (PdriverClassName);
            PdbConnection = DriverManager.getConnection (Purl, Pusername, Ppasswd);
            
            return PdbConnection;
        } catch (SQLException e) {
            logger.info("ERROR CONNECTION DB: " + e);
            return null;
        }
    }
    
}
