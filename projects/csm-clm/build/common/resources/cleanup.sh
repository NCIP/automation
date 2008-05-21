rm_file_list="ApplicationSecurityConfig.xml AuthSchemaMySQL.sql AuthSchemaOracle.sql AuthSchemaPostgres.sql AuthSchemaSQLServer.sql DataPrimingMySQL.sql DataPrimingOracle.sql DataPrimingPostgres.sql DataPrimingSQLServer.sql hibernate.cfg.xml log4jConfig.xml login-config.db-block.xml login-config.ldap-block.xml MigrationScript3.2MySQL.sql MigrationScript3.2Oracle.sql MigrationScript4.0MySQL.sql MigrationScript4.0Oracle.sql mysql-ds.xml ObjectStateLoggerConfig.xml oracle-ds.xml PasswordEncrypter.java postgres-ds.xml sqlserver-ds.xml"

for file in $rm_file_list
do
	svn remove  ../../api/$file
	svn remove  ../../securityws/$file
	svn remove  ../../upt/$file
done
