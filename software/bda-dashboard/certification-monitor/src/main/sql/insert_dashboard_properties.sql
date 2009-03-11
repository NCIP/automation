drop table dashboard_properties;

create table `dashboard_properties` (
	`dashboard_key` varchar (600),
	`dashboard_value` varchar (600)
); 
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.system.user','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.system.password','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.server','localhost');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.port','3306');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.name','genericdb');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.user','genericuser');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.password','password');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.url','jdbc:mysql://${mysql.database.server}:${mysql.database.port}/${mysql.database.name}');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.database.system.url','jdbc:mysql://${mysql.database.server}:${mysql.database.port}/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.system.user','postgresadmin');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.system.password','password');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.server','localhost');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.port','5432');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.name','genericdb');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.user','genericuser');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.password','password');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.url','jdbc:postgresql://${postgresql.database.server}:${postgresql.database.port}/${postgresql.database.name}');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('postgresql.database.system.url','jdbc:postgresql://${postgresql.database.server}:${postgresql.database.port}/template1');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('mysql.minimum.version','5.0.27');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('force.reinstall','true');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ant.minimum.version','1.7.0');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('java.major.version','1.5');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('java.minor.version','1.5.0_10');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bda.version','0.9.0');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ci.server.name','http://cbvapp-c1006.nci.nih.gov:48080/hudson/job');

insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.svn.project.url','http://gforge.nci.nih.gov/svnroot/bda-petstore');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.master.build.location','${petstore.svn.local.checkout}/trunk/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.exploded.dir.location','${petstore.svn.local.checkout}/trunk/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/petstore');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('petstore.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.svn.project.url','http://gforge.nci.nih.gov/svnroot/caarray2/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.master.build.location','${caarray.svn.local.checkout}/software');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.exploded.dir.location','${caarray.svn.local.checkout}/software/target');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/caarray2');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caarray.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.svn.project.url','http://gforge.nci.nih.gov/svnroot/ncia/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.master.build.location','${ncia.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.exploded.dir.location','${ncia.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/ncia');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.ci-server.hostname','cbv-ciweb-base.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.ci-server.jobname','ncia_ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncia.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.database.type','postgresql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.svn.project.url','http://gforge.nci.nih.gov/svnroot/gpsxar/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.master.build.location','${protexpress.svn.local.checkout}/software/build-prot');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.exploded.dir.location','${protexpress.svn.local.checkout}/software/install-target/protExpress/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/protexpress');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('protexpress.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.svn.project.url','http://gforge.nci.nih.gov/svnroot/lex-browser/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.master.build.location','${bioportal.svn.local.checkout}/software');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.exploded.dir.location','${bioportal.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/bioportal');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('bioportal.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.svn.project.url','http://gforge.nci.nih.gov/svnroot/lexevs/base/v5/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.master.build.location','${lexevs.svn.local.checkout}/lexevs_bda/Lexevs_build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.exploded.dir.location','${lexevs.svn.local.checkout}/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/lexevs');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('lexevs.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.svn.project.url','http://gforge.nci.nih.gov/svnroot/security/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.master.build.location','${upt.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.exploded.dir.location','${upt.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/csmupt');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.ci-server.hostname','cbvapp-c1002.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.ci-server.jobname','csm-install');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('upt.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.database.type','postgresql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.svn.project.url','http://gforge.nci.nih.gov/svnroot/coppa/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.master.build.location','${po.svn.local.checkout}/code/build-po');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.exploded.dir.location','${po.svn.local.checkout}/code/target/po/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/coppa');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.ci-server.hostname','cbvapp-c1005.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.ci-server.jobname','coppa-po-ci-full');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('po.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.svn.project.url','http://gforge.nci.nih.gov/svnroot/caintegrator2/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.master.build.location','${caintegrator2.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.exploded.dir.location','${caintegrator2.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/cai2');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('caintegrator2.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.svn.project.url','http://gforge.nci.nih.gov/svnroot/calab/cananolab/trunk/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.master.build.location','${cananolab.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.exploded.dir.location','${cananolab.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cananolab');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cananolab.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.database.type','postgresql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.svn.project.url','http://gforge.nci.nih.gov/svnroot/coppa/trunk/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.master.build.location','${pa.svn.local.checkout}/code/build-pa');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.exploded.dir.location','${pa.svn.local.checkout}/software/target/pa/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/coppa');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.ci-server.hostname','cbvapp-c1005.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.ci-server.jobname','pa-bda-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('pa.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.svn.project.url','http://gforge.nci.nih.gov/svnroot/rembrandt/trunk/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.master.build.location','${rembrandt.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.exploded.dir.location','${rembrandt.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/rembrandt');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('rembrandt.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.svn.project.url','http://gforge.nci.nih.gov/svnroot/cabiodb/cabioapi/trunk/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.master.build.location','${cabio.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.exploded.dir.location','${cabio.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cabio');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.ci-server.hostname','ncias-d185-v.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.ci-server.jobname','cabioapi');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cabio.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.svn.project.url','http://gforge.nci.nih.gov/svnroot/ispy/trunk/');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.master.build.location','${ispy.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.exploded.dir.location','${ispy.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/ispy');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ispy.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.svn.project.url','http://gforge.nci.nih.gov/svnroot/cpas/trunk/cpas');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.master.build.location','${cpas.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.exploded.dir.location','${cpas.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cpas');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cpas.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.svn.project.url','http://gforge.nci.nih.gov/svnroot/ccts/trunk/ccts/codebase');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.master.build.location','${ccts.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.exploded.dir.location','${ccts.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/ccts');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ccts.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.svn.project.url','http://gforge.nci.nih.gov/svnroot/c3prv2/trunk/c3prv2/codebase');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.master.build.location','${c3prv2.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.exploded.dir.location','${c3prv2.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/c3prv2');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('c3prv2.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.svn.project.url','http://gforge.nci.nih.gov/svnroot/cadsr/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.master.build.location','${cadsr.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.exploded.dir.location','${cadsr.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cadsr');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cadsr.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.svn.project.url','http://gforge.nci.nih.gov/svnroot/camod/trunk/camod');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.master.build.location','${camod.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.exploded.dir.location','${camod.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/camod');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('camod.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.svn.project.url','http://gforge.nci.nih.gov/svnroot/catissue/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.master.build.location','${catissue.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.exploded.dir.location','${catissue.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/catissue');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('catissue.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.svn.project.url','http://gforge.nci.nih.gov/svnroot/cab2b/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.master.build.location','${cab2b.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.exploded.dir.location','${cab2b.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cab2b');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cab2b.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.svn.project.url','http://gforge.nci.nih.gov/svnroot/cagrid/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.master.build.location','${cagrid.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.exploded.dir.location','${cagrid.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/cagrid');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('cagrid.ci-server.portnumber','48080');

insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.svn.project.url','http://gforge.nci.nih.gov/svnroot/ncit/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.master.build.location','${ncit.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.exploded.dir.location','${ncit.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/ncit');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('ncit.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.svn.project.url','http://gforge.nci.nih.gov/svnroot/reportwriter/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.master.build.location','${reportwriter.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.exploded.dir.location','${reportwriter.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/reportwriter');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('reportwriter.ci-server.portnumber','48080');


insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.svn.project.url','http://gforge.nci.nih.gov/svnroot/genepattern/trunk/wrong');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.master.build.location','${genepattern.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.exploded.dir.location','${genepattern.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/genepattern');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('genepattern.ci-server.portnumber','48080');

insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.database.type','mysql');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.svn.username','narram');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.svn.password','Test123$');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.svn.project.url','http://gforge.nci.nih.gov/svnroot/tfs/trunk');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.svn.local.checkout','working/bda_certification');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.master.build.location','${tfs.svn.local.checkout}/software/build');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.master.install.location','working/installer');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.single-command.build.target','build:all');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.single-command.deployment.target','dist:installer:prep');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.database.integration.target','install:database');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.master.build.file','build.xml');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.exploded.dir.location','${tfs.svn.local.checkout}/software/target/dist/exploded');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.private.repository','http://gforge.nci.nih.gov/svnroot/scm-private/trunk/projects/tfs');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.local.private.checkout','working/scm_private');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.ci-server.hostname','cbvapp-c1007.nci.nih.gov');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.ci-server.jobname','bda-blueprints-ci');
insert into `dashboard_properties` (`dashboard_key`, `dashboard_value`) values('tfs.ci-server.portnumber','48080');
