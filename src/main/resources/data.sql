----- ROLE
INSERT INTO role(
	id, created_at, updated_at,role_name)
	 VALUES
		(1, now(), now(), 0),
		(2, now(), now(), 1),
		(3, now(), now(), 2),
		(4, now(), now(), 3),
		(5, now(), now(), 4)
	ON CONFLICT (id) DO UPDATE SET role_name = EXCLUDED.role_name;

----------------User-----------------------
INSERT INTO "user" (id, created_at, updated_at, first_name, last_name, nic, mobile_number, subscription, email, password,status, role_id, user_status, user_type)
       select nextval('SEQ_FM_USER'), now(), now(), 'admin', 'admin', '971234567V', '0777123456', 'GOLD', 'admin@gmail.com','$2a$10$s9XT/zwrgEbtBIUcySUUSe2hftB7tvXmhrJY9hfw.uwVcAAr7LtYu',true,1, 'ACTIVE', 'ADMIN'
       WHERE NOT EXISTS (SELECT true FROM "user" WHERE id=1);
       
--------------Mail Properties-----------------
INSERT INTO mail_properties (created_at, updated_at, host, password, port, username, active, protocol)
       select  now(), now(), 'smtp.gmail.com', 'hbtqkpkgfhbfcmzg', 587, 'sgictesting1@gmail.com', true, 'smtp'
       WHERE NOT EXISTS (SELECT true FROM mail_properties WHERE username='sgictesting1@gmail.com' and password='hbtqkpkgfhbfcmzg');

----------- Driving Licence Type ---------------
INSERT INTO driving_license_type(id, created_at, updated_at, type)
	select nextval('SEQ_FM_DRIVINGLICENCETYPE'), now(), now(), 'Heavy' 
		WHERE NOT EXISTS (SELECT true FROM driving_license_type WHERE id=1);
		
INSERT INTO driving_license_type(id, created_at, updated_at, type)
	select nextval('SEQ_FM_DRIVINGLICENCETYPE'), now(), now(), 'Light' 
		WHERE NOT EXISTS (SELECT true FROM driving_license_type WHERE id=2);
       
INSERT INTO oauth_client_details(
	id, access_token_validity, authorities, authorized_grant_types, client_id, client_secret, refresh_token_validity, resource_ids, scope)
	SELECT 1, 360000 , 'ROLE_CLIENT,ADMIN', 'password,authorization_code,refresh_token', 'client1', '$2a$10$MpJI325fp5aNiZou8eRaJu/jpcO9C0ssOwrINPVODByoK8zX5E1Lu', '360000', 'resource-server-rest-api', 'read,write'
   	WHERE NOT EXISTS (SELECT true FROM oauth_client_details WHERE id=1);-- secret 123
   	
   	
-----------Module ---------------
---- CREATE MODULES
INSERT INTO module (
    id, prefix ,name,permission_name,created_at,updated_at)
    VALUES
        (1, 'LICENSE','license','License',now(),now()),
        (2, 'COMPANY','company','Company',now(),now()),
        (3, 'COMPANY_ADMIN','company admin','Company admin',now(),now()),
        (4, 'COMPANY_BRANCH','company branch','Company branch',now(),now()),
        (5, 'COMPANY_BRANCH_ADMIN','company branch admin','Company branch admin',now(),now()),
        (6, 'VEHICLE','vehicle','Vehicle',now(),now()),
        (7, 'DRIVER','driver','Driver',now(),now()),
        (8,'VEHICLE_DRIVER','vehicle driver','Vehicle driver',now(),now()),
        (9,'PRIVILEGE','privilege','Privilege',now(),now()),
        (10,'USER','user','User',now(),now()),
        (11,'SETTINGS','settings','Settings',now(),now()),
        (12,'VEHICLE_SERVICE','vehicle','Vehicle',now(),now()),
        (13, 'DOCUMENT','document','Document',now(),now()),
        (14,'PARTS','parts','Parts',now(),now()),
        (15, 'GENERATOR','generator','Generator',now(),now()),
        (16,'PART','part','Part driver',now(),now()),
        (17, 'NOTIFICATION','notification','Notification',now(),now()),
            (18, 'VEHICLE_RESOURCE','vehicleResource','VehicleResource',now(),now())
    ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name,prefix = EXCLUDED.prefix;
 

---- FM Admin's Permissions -------
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 1, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=1);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 2, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=2);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 3, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=3);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 4, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=4);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 5, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=5);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 6, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=6);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 7, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=7);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 8, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=8);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 9, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=9);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 10, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=10);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 11, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=11);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 12, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=12);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 13, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=13);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 14, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=14);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 15, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=15);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 16, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=16);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 17, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=17);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 2, 18, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=18);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 1, 18, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=19);
	   
INSERT INTO role_permission (id, status, permission, module_id, role_id,contains)
       select nextval('SEQ_FM_ROLEPERMISSION'),true, 0, 18, 1,true 
       WHERE NOT EXISTS (SELECT true FROM role_permission WHERE id=20);
   
------- Company -----------------
INSERT INTO "company" (id, created_at, updated_at,address,company_email,company_name,company_phone_number,licence_type,registration_number)
       select nextval('SEQ_FM_COMPANY'), now(), now(),'Jaffna','info@invictainnovations.com','Invicta Innovations','0212214205',2,'SL002'
       WHERE NOT EXISTS (SELECT true FROM "company" WHERE id=1);
       
------ Company Admin -------------
INSERT INTO "user" (id, created_at, updated_at, first_name, last_name, nic, mobile_number, subscription, email, password,status, role_id, user_status, user_type)
       select nextval('SEQ_FM_USER'), now(), now(), 'Cudemariyan', 'Cudeson', '950881836V', '94750428428', 'GOLD', 'cude1995son@gmail.com','$2a$10$s9XT/zwrgEbtBIUcySUUSe2hftB7tvXmhrJY9hfw.uwVcAAr7LtYu',true,2, 'ACTIVE', 'COMPANYADMIN'
       WHERE NOT EXISTS (SELECT true FROM "user" WHERE id=2);
       
------- Branch -------------------
INSERT INTO "branch" (id, branch_name,address,phone_number,email,company_id)
       select nextval('SEQ_FM_BRANCH'), 'Invicta Innovations', 'Jaffna','0212214205','info@invictainnovations.com',1
       WHERE NOT EXISTS (SELECT true FROM "branch" WHERE id=1);
       
