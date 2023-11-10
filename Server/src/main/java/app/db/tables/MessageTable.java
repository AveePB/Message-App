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

        String sqlStmt = "SELECT user.nickname, message.content FROM message ";
        sqlStmt = sqlStmt + "INNER JOIN user ON message.author_id = user.id ";
        sqlStmt = sqlStmt + "AND message.chat_id = " + chatId + ';';

        ResultSet rs = stmt.executeQuery(sqlStmt);
        List<String> messages = new ArrayList<>();

        while (rs.next()) {
            String msg = rs.getString("nickname") + ": " + rs.getString("content");
            messages.add(msg);
        }

        return messages;
    }

    /**
     * Creates a new message and delivers message to online user.
     * @param chatId the chat id.
     * @param authorId the author's id.
     * @param msg the message content.
     * @return true if a message has been created otherwise false.
     */
    public static boolean createMessage(Statement stmt, int chatId, int authorId, String msg) {
        String sqlStmt = "INSERT INTO message (chat_id, author_id, content) VALUES";
        sqlStmt = sqlStmt + '(' + chatId + ',' + authorId + ",'" + msg + "');";

        try {
            stmt.executeUpdate(sqlStmt);
        }
        catch (Exception ex) {
            return false;
        }

        return true;
    }
}
