CREATE DATABASE messageapp;

USE messageapp;

CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(32) NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE chat(
    id INT UNIQUE NOT NULL,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,

    PRIMARY KEY(user1_id, user2_id),
    FOREIGN KEY(user1_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY(user2_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE message(
    id INT NOT NULL AUTO_INCREMENT,
    chat_id INT NOT NULL,
    author_id INT NOT NULL,
    content TEXT NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(chat_id) REFERENCES chat(id) ON DELETE CASCADE,
    FOREIGN KEY(author_id) REFERENCES user(id) ON DELETE CASCADE
);
