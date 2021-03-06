CREATE TABLE `project_initialization` (
   `ID` INT(11) DEFAULT NULL,
   `PROJECT_NAME` VARCHAR(100) DEFAULT NULL,
   `COMMAND_NAME` VARCHAR(1000) DEFAULT NULL,
   `TO_LOCATION` VARCHAR(1000) DEFAULT NULL,
   `IS_FILE` VARCHAR(5) DEFAULT NULL,
   `RUN_AS_ROOT` VARCHAR(5) DEFAULT NULL,
   `IS_REBOOT_REQUIRED` VARCHAR(5) DEFAULT NULL
 ) ENGINE=MYISAM DEFAULT CHARSET=latin1
 ;
 
 INSERT INTO project_initialization VALUES (1,'caarray','resources/init.sh','','true','true','false','true');
 INSERT INTO project_initialization VALUES (2,'caarray','resources/hosts','/etc/','true','true','false','true');
 INSERT INTO project_initialization VALUES (3,'caarray','chmod 700 init.sh','','false','true','false','true');
 INSERT INTO project_initialization VALUES (4,'caarray','yum install sysutils','','false','true','false','true');
 INSERT INTO project_initialization VALUES (5,'caarray','dos2unix init.sh','','false','true','false','true');
 INSERT INTO project_initialization VALUES (6,'caarray','sh init.sh','','false','true','true','true');
 INSERT INTO project_initialization VALUES (7,'caarray','resources/mysqld','/etc/init.d/','true','true','false','true');
 INSERT INTO project_initialization VALUES (8,'caarray','resources/my.cnf','/etc','true','true','false','true');
 INSERT INTO project_initialization VALUES (9,'caarray','resources/my.cnf','/etc','true','true','false','true');
 INSERT INTO project_initialization VALUES (10,'caarray','mkfs.ext3 -F /dev/sdi','','false','true','false','true');
 INSERT INTO project_initialization VALUES (11,'caarray','mkdir /mnt/datamnt','','false','true','false','true');
 INSERT INTO project_initialization VALUES (12,'caarray','mount -t ext3 /dev/sdi /mnt/datamnt','','false','true','false','true'); 
 INSERT INTO project_initialization VALUES (13,'caarray','/etc/init.d/mysqld restart','','false','true','false','true');
 INSERT INTO project_initialization VALUES (14,'caarray','mysqladmin -u root password mysql','','false','true','false','true');
 INSERT INTO project_initialization VALUES (15,'caarray','resources/.bash_profile','','true','false','false','true');
 INSERT INTO project_initialization VALUES (16,'caarray','. .bash_profile >> profile.log','','false','false','false','true');
 INSERT INTO project_initialization VALUES (17,'caarray','mkdir ~/working','','false','false','false','true');
 INSERT INTO project_initialization VALUES (18,'caarray','mkdir ~/working/project_checkout','','false','false','false','true');
 INSERT INTO project_initialization VALUES (19,'caarray','mkdir ~/working/setup','','false','false','false','true');
 INSERT INTO project_initialization VALUES (20,'caarray','mkdir ~/working/setup/utility-scripts','','false','false','false','true');
 INSERT INTO project_initialization VALUES (21,'caarray','svn co http://gforge.nci.nih.gov/svnroot/caarray2/tags/CAARRAY_R2_3_1_RC5/ working/project_checkout','','false','false','false','true');
 INSERT INTO project_initialization VALUES (22,'caarray','resources/local.properties','./working/project_checkout/software/master_build/','true','false','false','true');
 INSERT INTO project_initialization VALUES (23,'caarray','resources/build.xml','./working/setup/utility-scripts/','true','false','false','true');
 INSERT INTO project_initialization VALUES (24,'caarray','resources/local.properties','./working/setup/utility-scripts','true','false','false','true');
 INSERT INTO project_initialization VALUES (25,'caarray','ant -f ./working/setup/utility-scripts/build.xml database:create >> utility.log','','false','false','false','true');
 INSERT INTO project_initialization VALUES (26,'caarray','ant -f ./working/project_checkout/software/master_build/build.xml deploy:local:install >> install.log','','false','false','false','true');
 
 