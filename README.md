# Message App (Socket Programming Project in Java)
Welcome to the "Message App" project! This is an application that allows users 
to communicate with each other in real time. This project is developed to learn 
more about designing and creating more engaging applications.

## Server Configuration
The server application has the configuration file (***.\Server\src\main\java\app\Config.java***).
It is used to change the most important server parameters.

### API Constants
- **PORT** <- the server port (default: 55555).

### Database Constants
It is recommended to create the MySQL database using the sql script (***.\Server\assets\sql\create_db.sql***).

- **MYSQL_USERNAME** <- the MySQL user nickname used to manage the database (default: "root").
- **MYSQL_PASSWORD** <- the MySQL user password used to log in (default: "password").
- **MYSQL_DB_URL** <- the MySQL database URL (default: "jdbc:mysql://localhost/messageapp").

### Logger Constants
- **ARE_SPACES_BETWEEN_LOGS** <- it is used to determine if log messages are separated by the line.
- **ARE_LOGS_APPENDED** <- it is used to determine if log messages are written to the file or appended.
- **LOG_FILE_NAME** <- the name of the file that stores server log messages (default: "records.log").
- **LOG_DIR** <- the name of the folder that stroes log files (default: "./logs/").

## Client Configuration
...

## Usage
...
