USE messageapp;

CREATE TABLE message(
    id INT NOT NULL,
    chat_id INT NOT NULL,
    author_id INT NOT NULL,
    content TEXT NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(chat_id) REFERENCES app.chat(id) ON DELETE CASCADE,
    FOREIGN KEY(author_id) REFERENCES user(id) ON DELETE CASCADE
);