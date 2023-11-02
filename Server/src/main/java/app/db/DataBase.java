package app.db;

//Java Structured Query Language
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Java Utilities
import java.util.ArrayList;
import java.util.List;

//Java Language
import java.lang.Integer;
import java.lang.String;

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

    /**
     * Validates the user data.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return true if the user data are valid otherwise false.
     */
    public boolean isUserDataValid(String nickname, String password) {
        String sqlStmt = "SELECT * FROM user  WHERE ";
        sqlStmt = sqlStmt + "nickname = '" + nickname + "' AND ";
        sqlStmt = sqlStmt + "password = '" + password + "';";

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next())
                return true;
        }
        catch (SQLException ignored) { }

        return false;
    }

    /**
     * Returns user id based on the nickname.
     * @param nickname the user nickname.
     * @return -1 if the user doesn't exist otherwise user id.
     */
    public Integer getUserId(String nickname) {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE nickname = '" + nickname + "';";

        Integer id = null;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next())
                id = rs.getInt("id");
        }
        catch (SQLException ignored) { }

        return id;
    }

    /**
     * Returns user nickname based on the id.
     * @param id the user id.
     * @return null if the user doesn't exist otherwise user nickname.
     */
    public String getUserNickname(int id) {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE id = '" + id + "';";

        String nickname = null;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next())
                nickname = rs.getString("nickname");
        }
        catch (SQLException ignored) { }

        return nickname;
    }

    /**
     * Returns the chat id.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return -1 if the chat doesn't exist otherwise chat id.
     */
    public Integer getChatId(int user1Id, int user2Id) {
        int minId = Math.min(user1Id, user2Id);
        int maxId = Math.max(user1Id, user2Id);

        String sqlStmt = "SELECT id FROM chat WHERE ";
        sqlStmt = sqlStmt + "user1_id = " + minId + " AND ";
        sqlStmt = sqlStmt + "user2_id = " + maxId + ";";

        Integer id = null;
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch (SQLException ignored) { }

        return id;
    }

    /**
     * Returns the maximum chat id.
     * @return the maximum chat id.
     */
    public Integer getMaxChatId() {
        String sqlStmt = "SELECT MAX(id) FROM chat;";
        Integer maxId = null;

        try  {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        catch (SQLException ignored) { }

        return maxId;
    }

    /**
     * Returns nicknames of all people user chats with.
     * @param userId the user's id.
     * @return a list of nicknames.
     */
    public List<String> getChatUsers(int userId) {
        //userId can be found on both fields user1_id & user2_id
        String sqlStmt1 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt1 = sqlStmt1 + "JOIN user ON chat.user2_id = user.id ";
        sqlStmt1 = sqlStmt1 + "WHERE chat.user1_id = " + userId + ';';

        String sqlStmt2 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt2 = sqlStmt2 + "JOIN user ON chat.user1_id = user.id ";
        sqlStmt2 = sqlStmt2 + "WHERE chat.user2_id = " + userId + ';';

        List<String> nicknames = new ArrayList<>();

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery(sqlStmt1);
            while (rs.next()) {
                nicknames.add(rs.getString("nickname"));
            }

            rs = stmt.executeQuery(sqlStmt2);
            while (rs.next()) {
                nicknames.add(rs.getString("nickname"));
            }
        }
        catch (SQLException ignored) { }

        return nicknames;
    }

    /**
     * Returns a list of messages between user1 and user2.
     * @param chatId the chat id.
     * @return a list of messages.
     */
    public List<String> getMessages(int chatId) {
        List<String> messages = new ArrayList<>();

        String sqlStmt = "SELECT author_id, content FROM Message WHERE ";
        sqlStmt = sqlStmt + "chat_id = " + chatId + ';';

        try {
            if (chatId == -1) throw new SQLException("THERE IS NO CHAT!!!");

            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            while (rs.next()) {
                String msg = getUserNickname(rs.getInt("author_id")) +
                        ": " + rs.getString("content");
                messages.add(msg);
            }
        }
        catch (SQLException ex) {
            return messages;
        }

        return messages;
    }

    /**
     * Registers new user in MySQL database.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return true if a user has been created otherwise false.
     */
    public boolean createUser(String nickname, String password) {
        String sqlStmt = "INSERT INTO user (nickname, password) VALUES";
        sqlStmt = sqlStmt + "('" + nickname + "','" + password + "');";

        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }
        catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * Creates a new chat between two users.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return true if a chat has been created otherwise false.
     */
    public boolean createChat(int user1Id, int user2Id)  {
        if (user1Id == user2Id) return false;

        int minId = Math.min(user1Id, user2Id);
        int maxId = Math.max(user1Id, user2Id);

        //SQL statement returning number of existing rows with these requirements.
        String sqlStmt = "SELECT COUNT(*) FROM chat WHERE ";
        sqlStmt = sqlStmt + "user1_id = " + minId + " AND ";
        sqlStmt = sqlStmt + "user2_id = " + maxId + ";";

        //SQL statement inserting new row in table chat.
        int newChatId = getMaxChatId() + 1;
        String sqlStmtU = "INSERT INTO chat (id, user1_id, user2_id) VALUES";
        sqlStmtU = sqlStmtU + '(' + newChatId + ',' + minId + ',' + maxId + ");";

        try {
            Statement stmt = this.conn.createStatement();

            //1. Checks if chat already exists.
            ResultSet rs = stmt.executeQuery(sqlStmt);
            if (rs.next() && rs.getInt(1) != 0)
                return false;

            //2. Creates chat.
            stmt.executeUpdate(sqlStmtU);
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }

    /**
     * Creates a new message and delivers message to online user.
     * @param authorId the author's id.
     * @param recipientId the recipient's id.
     * @param msgContent the message content.
     * @return true if a message has been created otherwise false.
     */
    public boolean createMessage(int authorId, int recipientId, String msgContent) {
        int chatId = getChatId(authorId, recipientId);
        if (chatId == -1) return false;

        String sqlStmt = "INSERT INTO message (chat_id, author_id, content) VALUES";
        sqlStmt = sqlStmt + '(' + chatId + ',' + authorId + ",'" + msgContent + "');";

        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }
        catch (SQLException ex) {
            return false;
        }

        return true;
    }
}
