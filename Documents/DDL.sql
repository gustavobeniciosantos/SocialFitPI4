CREATE DATABASE SOCIAL_FIT

CREATE TABLE user_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    cpf VARCHAR(14) UNIQUE NOT NULL,
    fullAddress VARCHAR(255),
    nationality VARCHAR(50),
    gender CHAR(1),
    birthDate DATE,
    weight DECIMAL(5,2)
);

CREATE TABLE friends_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId1 INT NOT NULL,
    userId2 INT NOT NULL,
    FOREIGN KEY (userId1) REFERENCES user_table(id),
    FOREIGN KEY (userId2) REFERENCES user_table(id),
    UNIQUE (userId1, userId2)
);

CREATE TABLE publication_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    publication_text TEXT NOT NULL,
    FOREIGN KEY (userId) REFERENCES user_table(id)
);

CREATE TABLE publication_like_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publicationId INT NOT NULL,
    userId INT NOT NULL,
    FOREIGN KEY (publicationId) REFERENCES publication_table(id),
    FOREIGN KEY (userId) REFERENCES user_table(id),
    UNIQUE (publicationId, userId)
);