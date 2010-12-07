DROP DATABASE IF EXISTS bda-blueprints;
CREATE DATABASE bda-blueprints;

GRANT ALL PRIVILEGES ON bda-blueprints.* TO 'bdauser'@'localhost' IDENTIFIED BY 'bdapwd' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON bda-blueprints.* TO 'bdauser'@'%' IDENTIFIED BY 'bdapwd' WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost'
    IDENTIFIED BY 'sa' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost'
    IDENTIFIED BY 'sa' WITH GRANT OPTION;