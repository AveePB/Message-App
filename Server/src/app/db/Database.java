package app.db;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java SQL (Structured Query Language)
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

//Java Utilities (Popular Classes)
import java.util.HashMap;
import java.util.Map;

//Java Language (Fundamental Classes)
import java.lang.StringBuilder;
import java.lang.String;

//Java Custom Packages
import app.api.Response;
import app.Settings;


public class Database {
    private Map<Integer, PrintWriter> activeUsers;
    private Connection conn;

    /**
     * Constructs a database object.
     * @param dbUrl the MySQL database url.
     * @param username the MySQL username.
     * @param userPassword the MySQL user password.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    public Database(String dbUrl, String username, String userPassword) throws SQLException {
        this.activeUsers = new HashMap<>();
        this.conn = DriverManager.getConnection(dbUrl, username, userPassword);
    }

    /**
     * Sets user status to online.
     * @param userId the user's id.
     * @param pw the user text-output stream.
     */
    public void setUserStatusOnline(int userId, PrintWriter pw) {
        this.activeUsers.put(userId, pw);
    }

    /**
     * Sets user status to offline.
     * @param userId the user's id.
     */
    public void setUserStatusOffline(int userId) {
        this.activeUsers.remove(userId);
    }

    /**
     * Retrieves user object from MySQL database.
     * @param nickname the user nickname.
     * @return the user object.
     */
    public User getUser(String nickname) {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE nickname = '" + nickname + "';";
        User user = null;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next()) {
                user = new User(rs.getString("nickname"), rs.getString("password"), rs.getInt("id"));
            }
        }
        catch (SQLException ignored) { }

        return user;
    }

    /**
     * Retrieves user object from MySQL database.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return the user object.
     */
    public User getUser(String nickname, String password) {
        String sqlStmt = "SELECT * FROM user";
        sqlStmt = sqlStmt + " WHERE nickname = '" + nickname + "'";
        sqlStmt = sqlStmt + " AND password = '" + password + "';";

        User user = null;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);

            if (rs.next()) {
                user = new User(rs.getString("nickname"), rs.getString("password"), rs.getInt("id"));
            }
        }
        catch (SQLException ignored) { }

        return user;
    }

    /**
     * Registers new user in MySQL database and retrievers it.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return the user object.
     */
    public User registerUser(String nickname, String password) {
        String sqlStmt = "INSERT INTO user (nickname, password) VALUES";
        sqlStmt = sqlStmt + "('" + nickname + "','" + password + "');";

        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        }
        catch (Exception e) {
            return null;
        }

        return getUser(nickname, password);
    }

    /**
     * Returns the maximum chat id.
     * @return the maximum chat id.
     */
    public int getMaxChatId() {
        String sqlStmt = "SELECT MAX(id) FROM chat;";
        int maxId = 0;

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
     * Returns the chat id.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return -1 if the chat doesn't exist otherwise chat id.
     */
    public int getChatId(int user1Id, int user2Id) {
        int minId = Math.min(user1Id, user2Id);
        int maxId = Math.max(user1Id, user2Id);

        String sqlStmt = "SELECT id FROM chat WHERE ";
        sqlStmt = sqlStmt + "user1_id = " + minId + " AND ";
        sqlStmt = sqlStmt + "user2_id = " + maxId + ";";

        int id = -1;
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
     * Returns nicknames of all people user chats with.
     * @param userId the user's id.
     * @return a list of nicknames.
     */
    public String getChatUsers(int userId) {
        StringBuilder nicknames = new StringBuilder();

        //userId can be found on both fields user1_id & user2_id
        String sqlStmt1 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt1 = sqlStmt1 + "JOIN user ON chat.user2_id = user.id ";
        sqlStmt1 = sqlStmt1 + "WHERE chat.user1_id = " + userId + ';';

        String sqlStmt2 = "SELECT user.nickname AS nickname FROM chat ";
        sqlStmt2 = sqlStmt2 + "JOIN user ON chat.user1_id = user.id ";
        sqlStmt2 = sqlStmt2 + "WHERE chat.user2_id = " + userId + ';';

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery(sqlStmt1);
            while (rs.next()) {
                nicknames.append('.').append(rs.getString("nickname"));
            }

            rs = stmt.executeQuery(sqlStmt2);
            while (rs.next()) {
                nicknames.append('.').append(rs.getString("nickname"));
            }
        }
        catch (SQLException ignored) { }

        return nicknames.toString();
    }

    /**
     * Creates a new chat between two users.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return 1 if the chat has just been created,
     * 0 if the chat has already existed,
     * -1 if the chat hasn't been created.
     */
    public int createChat(int user1Id, int user2Id)  {
        if (user1Id == user2Id) return -1;

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
                return 0;

            //2. Creates chat.
            stmt.executeUpdate(sqlStmtU);
        }
        catch (SQLException e) {
            return -1;
        }

        return 1;
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
        catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Sends message to recipient if he is online.
     * @param recipientId the recipient's id.
     * @param authorEmail the author's email.
     * @param msgContent the message content.
     */
    public void sendMessage(int recipientId, String authorEmail,  String msgContent) {
        PrintWriter recipientPw = this.activeUsers.get(recipientId);
        if (recipientPw == null) return;

        recipientPw.println(Response.NEW_MSG + Settings.API_SEPARATOR + authorEmail + Settings.API_SEPARATOR + msgContent);
        recipientPw.flush();
    }
}
