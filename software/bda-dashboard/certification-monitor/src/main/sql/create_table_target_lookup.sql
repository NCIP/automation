drop table IF EXISTS target_lookup;
create table target_lookup (
	ID int8 ,
	TARGET_NAME varchar (150),
	MAP_NAME varchar (150),
	IS_VALUE varchar (15),
	IS_OPTIONAL varchar (15)
);
