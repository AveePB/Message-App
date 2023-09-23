package log;

import java.lang.String;

public enum LogLvl {
    FATAL("[FATAL]"),
    ERROR("[ERROR]"),
    WARN("[WARN]"),
    INFO("[INFO]"),
    DEBUG("[DEBUG]");

    protected final String str;

    LogLvl(String str) {
        this.str = str;
    }
}
