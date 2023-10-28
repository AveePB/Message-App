package app;

//Java Language
import java.lang.String;

/**
 * The Config class is used to
 * fully configure server settings.
 */
public class Config {
    //The API constants:
    public static final int PORT = 55555;

    //The Database constants:
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "password";
    public static final String MYSQL_DB_URL = "jdbc:mysql://localhost/messageapp";

    //The Logger constants:
    public static final boolean ARE_LOGS_APPENDED = true;
    public static final String LOG_DIR = "./logs/records.log";
}
