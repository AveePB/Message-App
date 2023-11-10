package app.db;

//Java Custom
import app.api.APIException;
import app.api.StatusCode;
import app.db.tables.ChatTable;
import app.db.tables.MessageTable;
import app.db.tables.UserTable;

//Java Structured Query Language
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Java Utilities
import java.util.List;

//Java Language
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
    public boolean validateUserData(String nickname, String password) throws APIException {
        try {
            return UserTable.validate(this.conn.createStatement(), nickname, password);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns user id based on the nickname.
     * @param nickname the user nickname.
     * @return -1 if the user doesn't exist otherwise user id.
     */
    public int getUserId(String nickname) throws APIException {
        try {
            return UserTable.getId(this.conn.createStatement(), nickname);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns user nickname based on the id.
     * @param id the user id.
     * @return null if the user doesn't exist otherwise user nickname.
     */
    public String getUserNickname(int id) throws APIException {
        try {
            return UserTable.getNickname(this.conn.createStatement(), id);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the chat id.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return -1 if the chat doesn't exist otherwise chat id.
     */
    public int getChatId(int user1Id, int user2Id) throws APIException {
        try {
            return ChatTable.getId(this.conn.createStatement(), user1Id, user2Id);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the maximum chat id.
     * @return the maximum chat id.
     */
    public int getMaxChatId() throws APIException {
        try {
            return ChatTable.getMaxId(this.conn.createStatement());
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns nicknames of all people user chats with.
     * @param userId the user's id.
     * @return a list of nicknames.
     */
    public List<String> getFriendList(int userId) throws APIException {
        try {
            return ChatTable.getFriends(this.conn.createStatement(), userId);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns a list of messages between user1 and user2.
     * @param chatId the chat id.
     * @return a list of messages.
     */
    public List<String> getMessages(int chatId) throws APIException {
        try {
            return MessageTable.getMessages(this.conn.createStatement(), chatId);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Registers new user in MySQL database and retrievers it.
     * @param nickname the user nickname.
     * @param password the user password.
     * @return boolean value.
     */
    public boolean createUser(String nickname, String password) throws APIException {
        try {
            return UserTable.createUser(this.conn.createStatement(), nickname, password);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new chat between two users.
     * @param user1Id the first user's id.
     * @param user2Id the second user's id.
     * @return true if a chat has been created otherwise false
     */
    public boolean createChat(int user1Id, int user2Id) throws APIException {
        try {
            return ChatTable.createChat(this.conn.createStatement(), user1Id, user2Id);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new message and delivers message to online user.
     * @param chatId the chat id.
     * @param authorId the author's id.
     * @param msg the message content.
     * @return true if a message has been created otherwise false.
     */
    public boolean createMessage(int chatId, int authorId, String msg) throws APIException {
        try {
            return MessageTable.createMessage(this.conn.createStatement(), chatId,authorId, msg);
        }
        catch (Exception ex) {
            throw new APIException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
