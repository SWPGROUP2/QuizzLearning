package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private static final Logger LOGGER = Logger.getLogger(DBContext.class.getName());
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "SWPQuiz";
    private static final String PORT_NUMBER = "3306";
    private static final String USER_ID = "root";
    private static final String PASSWORD = "1234";
    protected Connection connection;

    public DBContext() {
        try {
            String connectionUrl = "jdbc:mysql://" + SERVER_NAME + ":" + PORT_NUMBER + "/" + DATABASE_NAME
                    + "?useSSL=false&allowPublicKeyRetrieval=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, USER_ID, PASSWORD);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}


class TestConnection {

    private static final Logger LOGGER = Logger.getLogger(TestConnection.class.getName());

    public static void main(String[] args) {

        DBContext dbContext = new DBContext();
        if (dbContext.connection != null) {
            LOGGER.log(Level.INFO, "Ket noi thanh cong");
        } else {
            LOGGER.log(Level.SEVERE, "Ket noi that bai");
        }
    }
}
