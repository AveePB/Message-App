package app.log;

//Java Language
import java.lang.String;

/**
 * This is an enum class used as easy way
 * to display log levels.
 */
public enum LogLevel {
    EMERG("Emergency"), //Indicates that the system is unusable and requires immediate attention.
    ALERT("Alert"), //Indicates that immediate action is necessary to resolve a critical issue.

    CRIT("Critical"), //Signifies critical conditions in the program that demand intervention to prevent system failure.
    ERROR("Error"), //Indicates error conditions that impair some operation but are less severe than critical situations.
    WARN("Warning"), //Signifies potential issues that may lead to errors or unexpected behavior in the future if not addressed.

    NOTICE("Notice"), //Applies to normal but significant conditions that may require monitoring.
    INFO("Informatinonal"), //Includes messages that provide a record of the normal operation of the system.
    DEBUG("Debug"); //Intended for logging detailed information about the system for debugging purposes.

    //Variables:
    private String strValue;

    LogLevel(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public String toString() {
        return '[' + this.strValue + ']';
    }
}
