package app.db.tables;

//Java Structured Query Language
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Java Utilities
import java.util.ArrayList;
import java.util.List;

//Java Language
import java.lang.String;

/**
 * It's an interface of the chat table
 * in the MySQL database called 'messageapp'
 */
public class ChatTable {
    /**
     * Returns the chat id.
     * @param stmt the SQL statement object.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return the chat id.
     */
    public static int getId(Statement stmt, int user1Id, int user2Id) throws SQLException {
        if (user1Id == user2Id) throw new SQLException("SAME USER IDs!!!");

        int minId = Math.min(user1Id, user2Id);
        int maxId = Math.max(user1Id, user2Id);

        String sqlStmt = "SELECT id FROM chat WHERE ";
        sqlStmt = sqlStmt + "user1_id = " + minId + " AND ";
        sqlStmt = sqlStmt + "user2_id = " + maxId + ";";

        ResultSet rs = stmt.executeQuery(sqlStmt);

        if (rs.next())
            return rs.getInt(1);
        else
            throw new SQLException("NO RESULTS FOR SUCH DATA!!!");
    }

    /**
     * Returns the maximum chat id.
     * @param stmt the SQL statement object.
     * @return the chat id.
     */
    public static int getMaxId(Statement stmt) throws SQLException {
        String sqlStmt = "SELECT MAX(id) FROM chat;";
        ResultSet rs = stmt.executeQuery(sqlStmt);

        if (rs.next())
            return rs.getInt(1);
        return -1;
    }

    /**
     * Returns nicknames of all people user chats with.
     * @param stmt the SQL statement object.
     * @param userId the user's id.
     * @return a list of nicknames.
     */
    public static List<String> getFriends(Statement stmt, int userId) throws SQLException {
        //userId can be found on both fields user1_id & user2_id
        String sqlStmt1 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt1 = sqlStmt1 + "JOIN user ON chat.user2_id = user.id ";
        sqlStmt1 = sqlStmt1 + "WHERE chat.user1_id = " + userId + ';';

        String sqlStmt2 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt2 = sqlStmt2 + "JOIN user ON chat.user1_id = user.id ";
        sqlStmt2 = sqlStmt2 + "WHERE chat.user2_id = " + userId + ';';

        List<String> nicknames = new ArrayList<>();

        ResultSet rs = stmt.executeQuery(sqlStmt1);
        while (rs.next())
            nicknames.add(rs.getString("nickname"));

        rs = stmt.executeQuery(sqlStmt2);
        while (rs.next())
            nicknames.add(rs.getString("nickname"));

        return nicknames;
    }

    /**
     * Creates a new chat between two users.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return boolean value.
     */
    public static boolean createChat(Statement stmt, int user1Id, int user2Id) {
        if (user1Id == user2Id) return false;

        //If chat exists return false.
        try {
            ChatTable.getId(stmt, user1Id, user2Id);
            return false;
        }
        catch (SQLException ignored) {  }

        try {
            //SQL statement inserting new row in table chat.
            int newChatId = ChatTable.getMaxId(stmt) + 1;
            String sqlStmt = "INSERT INTO chat (id, user1_id, user2_id) VALUES";
            sqlStmt = sqlStmt + '(' + newChatId + ',' + Math.min(user1Id, user2Id) + ',' + Math.max(user1Id, user2Id) + ");";
            stmt.executeUpdate(sqlStmt);
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }
}
