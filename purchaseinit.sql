CREATE DATABASE purchasedb;

CREATE USER 'purchasetest'@'172.17.0.1' IDENTIFIED BY '1234';
CREATE USER 'purchasetest'@'172.17.0.2' IDENTIFIED BY '1234';
CREATE USER 'purchasetest'@'localhost' IDENTIFIED BY '1234';
CREATE USER 'purchasetest'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'purchasetest'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'purchasetest'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'purchasetest'@'172.17.0.1' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'purchasetest'@'172.17.0.2' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

CREATE TABLE member (
    memberid BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(255),
    birth DATETIME,
    name VARCHAR(255),
    profileimage VARCHAR(255),
    point INT DEFAULT 0,
    nick_name VARCHAR(255)
);


INSERT INTO member (birth, email, name, nick_name, password, phone_number)
VALUES ('1990-01-01 00:00:00', 'test1@example.com', 'John Doe', 'johnny', 'password1', '123-456-7890');

INSERT INTO member (birth, email, name, nick_name, password, phone_number)
VALUES ('1995-05-15 12:30:00', 'test2@example.com', 'Jane Smith', 'janesmith', 'password2', '987-654-3210');

INSERT INTO member (birth, email, name, nick_name, password, phone_number)
VALUES ('1988-11-30 08:45:00', 'test3@example.com', 'Michael Johnson', 'mikej', 'password3', '555-123-4567');


CREATE TABLE payment (
    PayNumber INT AUTO_INCREMENT PRIMARY KEY,
    PaymentID VARCHAR(255),
    status VARCHAR(255),
    paytime TIMESTAMP,
    totalamount INT,
    ordername VARCHAR(255),
    memberid BIGINT,
    FOREIGN KEY (memberid) REFERENCES member(memberid)
);


CREATE TABLE point (
    pointid INT AUTO_INCREMENT PRIMARY KEY,
    pointname VARCHAR(255),
    pointprice INT,
    pointamount INT
);

INSERT INTO point (pointname, pointprice, pointamount) VALUES 
('5000포인트', 5000, 5000),
('10000포인트', 10000, 10000),
('20000포인트', 20000, 20000),
('50000포인트', 50000, 50000),
('100000포인트', 100000, 100000);