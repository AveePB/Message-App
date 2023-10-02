USE messageapp;

CREATE TABLE app.chat(
    id INT NOT NULL,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(user1_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY(user2_id) REFERENCES user(id) ON DELETE CASCADE
);