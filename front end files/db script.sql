--drop tables
drop table if exists specialization cascade;
drop table if exists nufm_user cascade;
drop table if exists nufm_role cascade;
drop table if exists user_specialization cascade;
drop table if exists user_role cascade;
drop table if exists attendance cascade;
drop table if exists contractor cascade;
drop table if exists facility_type cascade;
drop table if exists facility cascade;
drop table if exists equipment cascade;
drop table if exists facility_equipment cascade;
drop table if exists project cascade;
drop table if exists project_worker cascade;
drop table if exists task_type cascade;
drop table if exists task cascade;
drop table if exists task_request cascade;
drop table if exists task_notification cascade;
drop table if exists worker_task cascade;
drop table if exists worker_schedule cascade;
drop table if exists safety_material cascade;
drop table if exists safety_worker cascade;
drop table if exists invoice cascade;
drop table if exists confirmation_token cascade;
drop table if exists facility_document cascade;
drop table if exists safety_material_type cascade;
drop table if exists notification  cascade;
--create tables 
create table specialization(
    eid varchar(128) primary key,
    name varchar(128) not null,
    type varchar(128) not null
);
--Security part
create table nufm_user(
    eid varchar(128) primary key,--email is the person id
    full_name varchar(128) not null,
    password varchar(128) not null,
    enabled boolean NULL DEFAULT false,
    profile_image varchar not null,
    phone varchar(128) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null
);

create table nufm_role(
    eid varchar(128) primary key,
    name  varchar(128) not null,
    description varchar(128) not null
);

create table user_specialization(
	eid varchar(128) primary key,
	user_id varchar(128) not null,
	specialization_id varchar(128) not null,
	constraint fk_specialization_user_specialization
      foreign key(specialization_id) 
	  REFERENCES specialization(eid)
	  on delete cascade,
	constraint fk_nufm_user_user_specialization
      foreign key(user_id) 
	  REFERENCES nufm_user (eid)
	  on delete cascade
);

create table user_role(
    eid varchar(128) primary key,	
    user_id varchar(128) not null,
    role_id varchar(128) not null,
    creation_date TIMESTAMP not null,
    constraint fk_nufm_user_user_role
      foreign key(user_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_nufm_role_user_role
      foreign key(role_id) 
	  REFERENCES nufm_role(eid)  
	  on delete cascade
);
--worker/contractor part
create table attendance(
    eid varchar(128) primary key,
    worker_id varchar(128) not null,
    status varchar(128) not null,
    constraint fk_nufm_user_attendance
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade
);

create table contractor(
    eid varchar(128) primary key,
    email varchar(128) not null,
    fullName varchar(128) not null,
    password varchar(128) not null,
    National_id varchar(128) not null,
    phone varchar(128) not null
);

--facility part
create table facility_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);

create table facility(
    eid  varchar(128) primary key,
    parent_id varchar(128) null,
    name varchar(128) not null,
    type_id varchar(128) not null,
    occupant_id varchar(128) null,
    location varchar(128) not null,
    creation_date TIMESTAMP not null,
    constraint fk_facility_facility
      foreign key(parent_id) 
	  REFERENCES facility(eid),
    constraint fk_nufm_user_facility
      foreign key(occupant_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_facility_type_facility
      foreign key(type_id) 
	  REFERENCES facility_type(eid)
	  on delete cascade
);

create table facility_document(
	doc_path varchar primary key,
	facility_id varchar(128) not null,
    constraint fk_facility_document
      foreign key(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade
	);

create table equipment(
    eid varchar(128) primary key,
    name varchar(128) not null,
    specification varchar(128) not null,
    monitoring_rules varchar(128) not null,
    location varchar(128) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null
);

create table facility_equipment(
	    eid varchar(128) primary key,
    facility_id varchar(128) not null,
    equipment_id varchar(128) not null,
    constraint fk_facility_facility_equipment
      foreign key(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade,
	constraint fk_equipment_facility_equipment
      foreign key(equipment_id) 
	  REFERENCES equipment (eid)
	  on delete cascade
   );

--Project part
create table project(
    eid varchar(128) primary key,
    facility_id varchar(128) not null,
    contractor_id varchar(128) not null,
    document_id varchar(128) not null,
    name varchar(128) not null,
    date_from date not null,
    date_to date not null,
    status varchar(128) not null,
    comment varchar(128) not null,
    constraint fk_facility_project
      foreign key(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade,
    constraint fk_contractor_project
      foreign key(contractor_id) 
	  REFERENCES contractor(eid)
	  on delete cascade
);

create table project_worker(
    eid varchar(128) primary key,
    project_id varchar(128) not null,
    worker_id varchar(128) not null,
    constraint fk_project_project_worker
      foreign key(project_id) 
	  REFERENCES project(eid)
	  on delete cascade,
    constraint fk_nufm_user_project
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade
);

--task part

create table task_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);

create table task(
    eid varchar(128) primary key,
    facility_id varchar(128) not null,
    type_id varchar(128) not null,
    project_id varchar(128) not null,
    name varchar(128) not null,
    created_at varchar(128) not null,
    date_from date,
    date_to  date,
    status varchar(128) not null,
    comment varchar(128) not null,
    document_id varchar(128),
    constraint fk_facility_task
      foreign key(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade,
    constraint fk_project_task
      foreign key(project_id) 
	  REFERENCES project(eid)
	  on delete cascade,
    constraint fk_task_type_task
      foreign key(type_id) 
	  REFERENCES task_type(eid)
	  on delete cascade
);

create table notification(
	eid varchar(128) primary key,
	sender_id varchar(128) not null,
	receiver_id varchar(128) not null,
	sender_name varchar(128) not null,
	task_name varchar(128) not null,
	task_type varchar(128) not null,
	task_status varchar(128),
	task_date date,
	facility_name varchar(128),
	delivery_date date,
	creation_date timestamp not null,
	comment varchar not null,
	constraint fk_sender_nuser_notification
      foreign key(sender_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade, 
	constraint fk_receiver_nuser_notification
      foreign key(receiver_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade);
	

--assigning tasks part

create table worker_task(
    eid varchar(128) primary key,
    worker_id varchar(128) not null,
    task_id varchar(128) not null,
    assigned_date TIMESTAMP not null,
    constraint fk_nufm_user_worker_task
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_task_worker_task
      foreign key(task_id) 
	  REFERENCES task(eid)
	  on delete cascade
);

create table worker_schedule(
    eid varchar(128) primary key,
    date date not null,
    task_id varchar(128) not null,
    worker_id varchar(128) not null,
    constraint fk_nufm_user_worker_schedule
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_task_worker_schedule
      foreign key(task_id) 
	  REFERENCES task(eid)
);

--safety 
create table safety_material_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);


create table safety_material(
    eid varchar(128) primary key,
    name varchar(128) not null,
    status varchar(128) not null,
    type varchar(128) not null,
    document_id varchar(128),
    constraint fk_safety_material_safety_material_type
      foreign key(type) 
	  REFERENCES safety_material_type (eid)
	  on delete cascade
);

create table safety_worker(
    eid varchar(128) primary key,
    material_id varchar(128) not null,
    worker_id varchar(128) not null,
    constraint fk_nufm_user_safety_worker
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_safety_material_safety_worker
      foreign key(material_id) 
	  REFERENCES safety_material(eid)
	  on delete cascade
);

--invoice
create table invoice(
    eid varchar(128) primary key,
    invoice_to varchar(128) not null,
    deliver_to varchar(128) not null,
    quantity decimal(10,2) not null,
    price decimal(10,2) not null,
    description varchar(128),
    sub_total decimal(10,2) not null,
    sales_tax decimal(10,2) not null,
    invoice_total decimal(10,2) not null,
    status varchar(128) not null,
    date TIMESTAMP not null
);
--security 
CREATE TABLE CONFIRMATION_TOKEN (
	IID VARCHAR(128) PRIMARY KEY,
	TOKEN VARCHAR(128) NOT NULL,
	USER_ID VARCHAR(128) NOT NULL,
    CREATED_AT TIMESTAMP not null,
    EXPIRED_AT TIMESTAMP not null,
	CONFIRMED_AT TIMESTAMP null,
	CONSTRAINT FK_CONFIRMATION_TOKEN_USER
      FOREIGN KEY(USER_ID) 
	  REFERENCES NUFM_USER(eid)
	  on delete cascade
);  
--fill tables 
--roles
insert into nufm_role values ('ROLE_WORKER','ROLE_WORKER','ROLE_WORKER');
insert into nufm_role values ('ROLE_ADMIN','ROLE_ADMIN','ROLE_ADMIN');
insert into nufm_role values ('ROLE_CONTRACTOR','ROLE_CONTRACTOR','ROLE_CONTRACTOR');
insert into nufm_role values ('ROLE_OCCUPANT','ROLE_OCCUPANT','ROLE_OCCUPANT');
--specializations
insert into specialization values ('CLEANER','CLEANER','w');
insert into specialization values ('DRIVER','DRIVER','w');
insert into specialization values ('REPAIR','REPAIR','w');
insert into specialization values ('op1','op1','c');
insert into specialization values ('op2','op2','c');
insert into specialization values ('op3','op3','c');
--facility types
insert into facility_type values('BUILDING','BUILDING');
insert into facility_type values('FLOOR','FLOOR');
insert into facility_type values('ROOM','ROOM');
--equipment 
insert into equipment values('VACUUM','VACUUM','NOISE CANCEL','CHECK DIRT BACK','STORAGE ROOM','2020-12-12 01:24:23.000','2020-12-12 01:24:23.000');
insert into equipment values('BROOM','BROOM','LARGE','CHECK STICK IF BROKEN','STORAGE ROOM','2020-12-12 01:24:23.000','2020-12-12 01:24:23.000');
insert into equipment values('MERCEDES_BENZ','MERCEDES_BENZ','BLACK 2010','CHECK MOTOR BEFORE USE','GARAGE','2020-12-12 01:24:23.000','2020-12-12 01:24:23.000');
insert into equipment values('ELECTRIC_SCREWDRIVER','ELECTRIC_SCREWDRIVER','BATTERY','CHECK BATTERY LEVEL','STORAGE ROOM','2020-12-12 01:24:23.000','2020-12-12 01:24:23.000');
--create admin
INSERT INTO nufmschema.nufm_user
(eid, full_name, "password", enabled, profile_image, phone, created_at, updated_at)
VALUES('ahmadkhouja3050@gmail.com', 'Ahmad Khouja', '$2a$10$nK04pqS6l5iJI6Y8Xz1XFu0ebctn7twR5QKwAHbCeH6c0yrz5Qhtu', true, 'D:\AVH projects\Workspaces\NufmWorkspace\nufm\code\avh.nufm\src\main\resources\storage\profile\admin\99bc6aab-96ff-4f11-be0d-b59204f59a28_image1.jpg', '06288477', '2022-07-19 03:28:24.499', '2022-07-19 03:28:24.499');

INSERT INTO nufmschema.user_role
(eid, user_id, role_id, creation_date)
VALUES('6b0f529e-d714-4175-9723-3961235840b1', 'ahmadkhouja3050@gmail.com', 'ROLE_ADMIN', '2022-07-19 03:28:24.612');

INSERT INTO nufmschema.confirmation_token
(iid, "token", user_id, created_at, expired_at, confirmed_at)
VALUES('51e75192-f259-47bc-afc7-1a37fc0557e7', '78b6a205-bffe-4980-8117-0e5b95559607', 'ahmadkhouja3050@gmail.com', '2022-07-19 03:28:24.616', '2022-07-19 03:43:24.616', '2022-07-19 03:28:51.733');

insert into nufmschema.safety_material_type 
values('helmet','helmet');
