USE messageapp;

CREATE TABLE user(
    id INT NOT NULL,
    nick_name VARCHAR(16) NOT NULL,
    password VARCHAR(32) NOT NULL,

    PRIMARY KEY(id)
);