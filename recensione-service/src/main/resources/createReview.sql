CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    text VARCHAR(200),
    score INTEGER,
    idUser INTEGER
);