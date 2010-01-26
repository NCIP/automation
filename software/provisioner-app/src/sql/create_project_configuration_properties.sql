CREATE TABLE `project_configuration_properties` (
  `project_key` VARCHAR(600) DEFAULT NULL,
  `project_value` VARCHAR(2000) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1
 
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('access.id','AKIAI53FXM32D5P3LZKA');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('secret.id','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');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('instance.type','default');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.portlist','18080,38080');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.force.reinstall','true');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.bda.version','1.5.18');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.application.base.path.linux','\/mnt\/appuser\/apps');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.type','mysql');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.system.user','root');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.system.password','mysql');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.server','localhost');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.port','3306');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.name','bda_db');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.user','bda_db_user');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.password','password');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.url','jdbc:mysql:\/\/${database.server}:${database.port}\/${database.name}');
INSERT INTO project_configuration_properties (project_key, project_value) VALUES('caarray.database.system.url','jdbc:mysql:\/\/${database.server}:${database.port}\/');