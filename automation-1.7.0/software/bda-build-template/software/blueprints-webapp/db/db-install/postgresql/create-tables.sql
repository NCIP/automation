CREATE TABLE study (
  ID bigint default '0' NOT NULL,
  STUDY_NAME varchar(50) default ''  NOT NULL,
  RESEARCHER varchar(50) default '' NOT NULL,
  date_received DATE,
  PRIMARY KEY  (ID)
)
/

CREATE TABLE state(
  STATE varchar(2) NOT NULL,
  description  varchar(50),
  PRIMARY KEY  (STATE)
)
/


CREATE FUNCTION SET_CSM_PG_PE_UPDATE_DATE() RETURNS trigger AS $SET_CSM_PG_PE_UPDATE_DATE$
BEGIN
	NEW.update_date := current_date;
	RETURN NEW;
END;
$SET_CSM_PG_PE_UPDATE_DATE$ LANGUAGE plpgsql
/
