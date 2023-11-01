package app.db;

//Java Structured Query Language
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used as an interface to
 * execute sql queries on MySQL database.
 */
public class DataBase {
    //Variables:
    private Connection conn;

    /**
     * Constructs a database (interface) object.
     * @param userNickname the MySQL username.
     * @param userPassword the MySQL password.
     * @param dbURL the MySQL database URL.
     */
    public DataBase(String userNickname, String userPassword, String dbURL) throws SQLException {
        this.conn = DriverManager.getConnection(dbURL, userNickname, userPassword);
    }

    /* FUNCTIONS TO CREATE:
     1. GET USER ID
     2. GET_CHAT_ID
     3. GET_USERNAME
     4. GET_LIST_OF_USERS_CHAT_WITH
     5. GET_CHAT_MESSAGES
     5. MAX_CHAT_ID
     6. IS_NICKNAME_UNIQUE
     7. CREATE_CHAT
     8. CREATE_MESSAGE
     */
}
