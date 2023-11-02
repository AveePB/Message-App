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
 * It's an interface of the message table
 * in the MySQL database called 'messageapp'
 */
public class MessageTable {

    /**
     * Returns a list of messages between user1 and user2.
     * @param stmt the SQL statement object.
     * @param chatId the chat id.
     * @return a list of messages.
     */
    public static List<String> getMessages(Statement stmt, int chatId) throws SQLException {
        if (chatId == -1) throw new SQLException("THERE IS NO CHAT!!!");

        String sqlStmt = "SELECT author_id, content FROM Message WHERE ";
        sqlStmt = sqlStmt + "chat_id = " + chatId + ';';

        ResultSet rs = stmt.executeQuery(sqlStmt);
        List<String> messages = new ArrayList<>();

        while (rs.next()) {
            String msg = UserTable.getNickname(stmt, rs.getInt("author_id")) +
                    ": " + rs.getString("content");
            messages.add(msg);
        }

        return messages;
    }
}
