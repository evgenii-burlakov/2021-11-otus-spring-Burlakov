DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE
        );

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE
        );

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME      VARCHAR(255) NOT NULL UNIQUE,
    AUTHOR_ID BIGINT       NOT NULL references AUTHORS (ID) ON DELETE CASCADE,
    GENRE_ID  BIGINT       NOT NULL references GENRES (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    COMMENT VARCHAR(512) NOT NULL,
    BOOK_ID BIGINT       NOT NULL references BOOKS (ID) ON DELETE CASCADE
        );