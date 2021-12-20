
--
-- Host: localhost    Database: IES_PROJ
-- ------------------------------------------------------

--
--

DROP TABLE IF EXISTS fav_stores;

DROP TABLE IF EXISTS user;

DROP TABLE IF EXISTS worker;

DROP TABLE IF EXISTS store;

DROP TABLE IF EXISTS shopping_center;

CREATE TABLE shopping_center (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  opening_time time DEFAULT NULL,
  closing_time time DEFAULT NULL,
  max_capacity float DEFAULT NULL,
  people_count int DEFAULT NULL ,
  PRIMARY KEY (id)
);


CREATE TABLE store (
  shop int NOT NULL,
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  opening_time time DEFAULT NULL,
  closing_time time DEFAULT NULL,
  max_capacity float DEFAULT NULL,
  people_count int DEFAULT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (shop) REFERENCES shopping_center(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE worker (
  id int NOT NULL AUTO_INCREMENT,
  shop int NOT NULL,
  name varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  job varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (shop) REFERENCES shopping_center(id) ON DELETE CASCADE ON UPDATE CASCADE

);


CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  type int NOT NULL,
  name varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fav_stores (
  user_id int NOT NULL,
  store_id int NOT NULL,
  PRIMARY KEY (user_id,store_id),
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE CASCADE ON UPDATE CASCADE
);


--
-- Dumping data in tables
--

INSERT INTO shopping_center(name,opening_time,closing_time,max_capacity) VALUES ('Aveiro Shop','09:00:00','22:00:00',400);

INSERT INTO shopping_center(name,opening_time,closing_time,max_capacity) VALUES ('Shoping Premier','09:00:00','22:00:00',350);

INSERT INTO shopping_center(name,opening_time,closing_time,max_capacity) VALUES ('Center City','09:00:00','22:00:00',250);

INSERT INTO shopping_center(name,opening_time,closing_time,max_capacity) VALUES ('City Buy','09:00:00','22:00:00',150);

INSERT INTO shopping_center(name,opening_time,closing_time,max_capacity) VALUES ('Mega Shop','09:00:00','22:00:00',450);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (1,'Perfumes Cheirosos','10:00:00','20:00:00',30);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (1,'Calçados Premium','09:30:00','19:00:00',45);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (2,'Food and Things','11:00:00','23:00:00',50);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (2,'Vodafone','09:00:00','22:00:00',30);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (3,'The Mobile Center','10:00:00','21:00:00',40);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (3,'Shop','09:00:00','21:00:00',30);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (4,'Food and Things','11:00:00','23:00:00',50);

INSERT INTO store(shop,name,opening_time,closing_time,max_capacity) VALUES (4,'Computers','11:00:00','19:00:00',40);

INSERT INTO worker(name,username,password,job,shop) VALUES ('Admin','admin','admin','segurança',1);

INSERT INTO worker(name,username,password,job,shop) VALUES ('Admin','admin','admin','segurança',2);

INSERT INTO worker(name,username,password,job,shop) VALUES ('Admin','admin','admin','segurança',3);

INSERT INTO worker(name,username,password,job,shop) VALUES ('Admin','admin','admin','segurança',4);

INSERT INTO user(name,type,username,password) VALUES ('Admin',1,'admin','admin');

INSERT INTO user(name,type,username,password) VALUES ('user',0,'user','user');

INSERT INTO fav_stores(user_id,store_id) VALUES (1,1);
