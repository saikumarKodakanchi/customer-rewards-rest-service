DROP TABLE IF EXISTS CUSTOMER;
DROP TABLE IF EXISTS PURCHASE_TRANSACTION;

CREATE TABLE CUSTOMER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email_address VARCHAR(250) DEFAULT NULL
);

CREATE TABLE PURCHASE_TRANSACTION (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  amount NUMERIC(10, 2) NOT NULL,
  created_ts TIMESTAMP NOT NULL,
  customer_id INT,
  CONSTRAINT FK_PURCHASE_TRANSACTION FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)

);