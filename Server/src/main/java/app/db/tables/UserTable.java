package app.db.tables;

//Java Structured Query Language
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Java Language
import java.lang.String;

/**
 * It's an interface of the user table
 * in the MySQL database called 'messageapp'
 */
public class UserTable {

    /**
     * Validates the user data.
     * @param stmt the SQL statement object.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return true if the user data are valid otherwise false.
     */
    public static boolean validate(Statement stmt, String nickname, String password) throws SQLException {
        String sqlStmt = "SELECT * FROM user  WHERE ";
        sqlStmt = sqlStmt + "nickname = '" + nickname + "' AND ";
        sqlStmt = sqlStmt + "password = '" + password + "';";

        ResultSet rs = stmt.executeQuery(sqlStmt);
        return rs.next();
    }

    /**
     * Returns user id based on the nickname.
     * @param stmt the SQL statement object.
     * @param nickname the user nickname.
     * @return the user id.
     */
    public static int getId(Statement stmt, String nickname) throws SQLException {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE nickname = '" + nickname + "';";

        ResultSet rs = stmt.executeQuery(sqlStmt);

        if (rs.next())
            return rs.getInt("id");
        else
            throw new SQLException("NO RESULTS FOR SUCH DATA!!!");
    }

    /**
     * Returns user nickname based on the id.
     * @param stmt the SQL statement object.
     * @param id the user id.
     * @return the user nickname.
     */
    public static String getNickname(Statement stmt, int id) throws SQLException {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE id = '" + id + "';";

        ResultSet rs = stmt.executeQuery(sqlStmt);

        if (rs.next())
            return rs.getString("nickname");
        else
            throw new SQLException("NO RESULTS FOR SUCH DATA!!!");
    }

    /**
     * Registers new user in MySQL database and retrievers it.
     * @param stmt the SQL statement object.
     * @param nickname nickname the user nickname.
     * @param password password the user password.
     * @return the boolean value.
     */
    public static boolean createUser(Statement stmt, String nickname, String password) {
        String sqlStmt = "INSERT INTO user (nickname, password) VALUES";
        sqlStmt = sqlStmt + "('" + nickname + "','" + password + "');";

        try {
            stmt.executeUpdate(sqlStmt);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }
}
