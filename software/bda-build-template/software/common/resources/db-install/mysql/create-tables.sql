DROP TABLE IF EXISTS study;
DROP TABLE IF EXISTS state;
DROP TABLE IF EXISTS user;

CREATE TABLE study (
  id bigint default '0' NOT NULL,
  study_name varchar(50) default ''  NOT NULL,
  researcher varchar(50) default '' NOT NULL,
  date_received DATE,
  PRIMARY KEY  (id)
);

CREATE TABLE state(
  state varchar(2) NOT NULL,
  description  varchar(50),
  PRIMARY KEY  (state)
);

CREATE TABLE user (
  username varchar(16) default '' NOT NULL ,
  password varchar(50) default '' NOT NULL ,
  PRIMARY KEY  (username)
);