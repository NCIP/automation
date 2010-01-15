insert into csm_protection_element
values (3,'NCIA.Project','NCIA.TRIAL_DATA_PROVENANCE','NCIA.Project','NCIA.PROJECT',NULL,2,'2009-04-05',NULL);

insert into csm_protection_element
values (4,'NCIA.Project//SiteName','NCIA.TRIAL_DATA_PROVENANCE','NCIA.Project//SiteName','NCIA.PROJECT//DP_SITE_NAME',NULL,2,'2009-04-05',NULL);

insert into csm_user_group_role_pg
values (2,1,NULL,3,1,'2009-04-05');
insert into csm_user_group_role_pg
values (3,1,NULL,4,1,'2009-04-05');
insert into csm_user_group_role_pg
values (4,1,NULL,1,1,'2009-04-05');
insert into csm_user_group_role_pg
values (5,1,NULL,5,1,'2009-04-05');

insert into csm_user_group_role_pg
values (6,NULL,1,1,1,'2009-04-05');

insert into csm_pg_pe 
value(1,1,3,'1950-01-01');
insert into csm_pg_pe 
value(2,1,4,'1950-01-01');


update general_image
set visibility='1';

update general_series
set visibility='1';

update study
set visibility='1';

update patient
set visibility='1';
