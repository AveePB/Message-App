package app.db;

//Java Language (Fundamental Classes)
import java.lang.String;


public class User {
    private final String nickname;
    private final String password;
    private final int id;

    /**
     * Constructs a user object.
     * @param nickname the user nickname.
     * @param password the user password.
     * @param id the user id.
     */
    public User(String nickname, String password, int id) {
        this.nickname = nickname;
        this.password = password;
        this.id = id;
    }

    /**
     * Copies user nickname and returns it.
     * @return the user nickname.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Copies user password and returns it.
     * @return the user password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Copies user id and returns it.
     * @return the user id.
     */
    public int getId() {
        return this.id;
    }
}
