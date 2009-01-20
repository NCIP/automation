/*
SQLyog Community Edition- MySQL GUI
MySQL - 5.0.27-community-nt 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `target_lookup` (
	`TARGET_NAME` varchar (300),
	`MAP_NAME` varchar (300),
	`id` double 
); 
insert into `target_lookup` (`TARGET_NAME`, `MAP_NAME`, `id`) values('validate:JAD','singleCommandBuild','1');
insert into `target_lookup` (`TARGET_NAME`, `MAP_NAME`, `id`) values('validate:svn:checkout-project','singleCommandBuild','2');
insert into `target_lookup` (`TARGET_NAME`, `MAP_NAME`, `id`) values('build:single-command-build','singleCommandBuild','3');
insert into `target_lookup` (`TARGET_NAME`, `MAP_NAME`, `id`) values('build:single-command-deployment','singleCommandDeployment','4');
insert into `target_lookup` (`TARGET_NAME`, `MAP_NAME`, `id`) values('build:database-integration','databaseIntegration','5');
