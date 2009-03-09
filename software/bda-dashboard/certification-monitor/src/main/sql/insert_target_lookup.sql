


create table target_lookup (
	ID double ,
	TARGET_NAME varchar (150),
	MAP_NAME varchar (150),
	IS_VALUE varchar (15)
); 
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(1,'validate:JAD','singleCommandBuild',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(2,'validate:svn:checkout-project','singleCommandBuild',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(3,'build:project','singleCommandBuild',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(4,'build:single-command-deployment','singleCommandDeployment',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(5,'build:database-integration','databaseIntegration',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(9,'build:ci-server','latestCIBuild','true');
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(6,'build:check-bdafied','bdaEnabled','true');
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(7,'build:template-properties','templateValidation',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(8,'build:private-repository-properties','privateRepositoryProperties',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(10,'build:validate-app-servers','deploymentShakeout',NULL);
insert into target_lookup (ID, TARGET_NAME, MAP_NAME, IS_VALUE) values(11,'build:check-commandline-installer','commandLineInstaller',NULL);
