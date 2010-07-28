DROP TABLE study IF EXISTS;
DROP TABLE state IF EXISTS;
DROP TABLE user IF EXISTS;


CREATE TABLE study (
  ID bigint default '0' NOT NULL,
  STUDY_NAME varchar(50) default ''  NOT NULL,
  RESEARCHER varchar(50) default '' NOT NULL,
  date_received DATE,
  PRIMARY KEY  (ID)
);

CREATE TABLE state(
  STATE varchar(2) NOT NULL,
  description  varchar(50),
  PRIMARY KEY  (STATE)
);

CREATE TABLE user (
  USERNAME varchar(16) default '' NOT NULL ,
  PASSWORD varchar(50) default '' NOT NULL ,
  PRIMARY KEY  (USERNAME)
);

commit;