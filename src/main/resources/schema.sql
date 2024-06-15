DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS file;
DROP TABLE IF EXISTS postTag;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id int NOT NULL AUTO_INCREMENT,
    userId varchar(45) NOT NULL,
    passWord varchar(200) NOT NULL,
    nickName varchar(45) DEFAULT NULL,
    isAdmin tinyint NOT NULL DEFAULT '0',
    createTime datetime DEFAULT NULL,
    isWithDraw tinyint NOT NULL,
    status varchar(45) DEFAULT NULL,
    updateTime datetime DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    url varchar(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE post
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    isAdmin tinyint NOT NULL,
    contents varchar(500) NOT NULL,
    createTime datetime NOT NULL,
    views int NOT NULL,
    categoryId int NOT NULL,
    userId int NOT NULL,
    fileId int DEFAULT NULL,
    updateTime datetime DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FK_1 (categoryId),
    KEY FK_2 (userId),
    CONSTRAINT FK_1 FOREIGN KEY (categoryId) REFERENCES category (id),
    CONSTRAINT FK_2 FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE postTag
(
    id int NOT NULL AUTO_INCREMENT,
    postId int NOT NULL,
    tagId int NOT NULL,
    PRIMARY KEY (id),
    KEY FK_1 (postId),
    CONSTRAINT FK_3 FOREIGN KEY FK_1 (postId) REFERENCES post (id),
    KEY FK_2 (tagId),
    CONSTRAINT FK_4 FOREIGN KEY FK_2 (tagId) REFERENCES tag (id)
);

CREATE TABLE file
(
    id int NOT NULL AUTO_INCREMENT,
    path varchar(100) NOT NULL,
    name varchar(45) NOT NULL,
    extension varchar(45) NOT NULL,
    postId int NOT NULL,
    PRIMARY KEY (id),
    KEY FK_1 (postId),
    CONSTRAINT FK_6 FOREIGN KEY FK_1 (postId) REFERENCES post (id)
);

CREATE TABLE comment
(
    id int NOT NULL AUTO_INCREMENT,
    postId int NOT NULL,
    contents varchar(300) NOT NULL,
    subCommentId int NOT NULL,
    PRIMARY KEY (id),
    KEY FK_1 (postId),
    CONSTRAINT FK_5 FOREIGN KEY FK_1 (postId) REFERENCES post (id)
);