--drop tables
drop table if exists specialization cascade;
drop table if exists nufm_user cascade;
drop table if exists nufm_role cascade;
drop table if exists user_specialization cascade;
drop table if exists user_role cascade;
drop table if exists attendance cascade;
drop table if exists facility_type cascade;
drop table if exists facility cascade;
drop table if exists equipment cascade;
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
drop table if exists safety_material_type cascade;
drop table if exists notification  cascade;
drop table if exists budget  cascade;
drop table if exists vendor cascade;
drop table if exists budget_type cascade;
drop table if exists contract cascade;
drop table if exists invoice_item cascade;
drop table if exists item cascade;
drop table if exists document cascade;
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
    phone varchar(128)  null,
    city varchar(128)  null,
    street varchar(128)  null,
    zip_code varchar(128)  null,
    spec_id varchar(128) not null,
    address varchar(128)  null,
    dob date  null,
    job_title varchar(128)  null,
    work_type varchar(128)  null,
    start_date date null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    constraint fk_specialization_nufm_user
      foreign key(spec_id) 
	  REFERENCES specialization(eid)
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
    schedule_from date not null,
    schedule_to date not null,
    floors_count int not null,
    const_year int not null,
    date_opened date not null,
    business_id varchar(128) not null,
    organizational_unit varchar(128) not null,
    primary_email varchar(128) not null,
    street varchar(128) not null,
    zip_code varchar(20) not null,
    address varchar(128) not null,
    description varchar(128) not null,
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
--worker/contractor part
create table attendance(
    eid varchar(128) primary key,
    worker_id varchar(128) not null,
    facility_id varchar(128) not null,
    status varchar(128) not null,
    clocked_in date not null,
    clocked_out date not null,
    constraint fk_nufm_user_attendance
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_facility_attendance
      foreign key(facility_id) 
	  REFERENCES facility(eid)
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
    parent_id varchar(128) null,
    facility_id varchar(128) not null,
    type_id varchar(128) not null,
    project_id varchar(128) not null,
    name varchar(128) not null,
    created_at varchar(128) not null,
    date_from date,
    date_to  date,
    status varchar(128) not null,
    comment varchar(128) not null,
    frequency varchar(128) not null,
    priority varchar(128) not null,
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
	  on delete cascade,
    constraint fk_task_task
      foreign key(parent_id) 
	  REFERENCES task(eid)
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
    date TIMESTAMP not null,
    client_location varchar(128) not null,
    invoice_number varchar(128) not null,
    currency varchar(28) not null
);

create table item(
    eid varchar(128) primary key,
    name varchar(128) not null,
    unit_price float not null
);


create table invoice_item(
    eid varchar(128) primary key,
    invoice_id varchar(128) not null,
    item_id varchar(128) not null,
    quantity int not null,
    price float not null,
    description varchar(128) not null,
    constraint fk_invoice_invoice_item
      foreign key(invoice_id) 
	  REFERENCES invoice(eid)
	  on delete cascade,
    constraint fk_item_invoice_item
      foreign key(item_id) 
	  REFERENCES item(eid)
	  on delete cascade
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

create table budget_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);

--budget
CREATE TABLE budget (
	IID VARCHAR(128) PRIMARY    KEY,
	facility_id VARCHAR(128) NOT NULL,
	name VARCHAR(128) NOT NULL,
    year int not null,
    month int  not null,
	submission_date TIMESTAMP not null,
    estimation int not null,
    income_tax int not null,
    difference int not null,
    type_id varchar(128) not null,
	CONSTRAINT FK_FACILITY_BUDGET
      FOREIGN KEY(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade,
    CONSTRAINT FK_BUDGET_TYPE_BUDGET
      FOREIGN KEY(type_id) 
	  REFERENCES budget_type(eid)
	  on delete cascade
);  


--contract
create table contract_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);

create table contract(
    eid varchar(128) primary key,
    title varchar(128) not null,
    number varchar(128) not null,
    type_id varchar(128) not null,
    start_date varchar(128) not null,
    end_date date not null,
    review_date date not null,
    notice_date date not null,
    template varchar(128) not null,
    supplier varchar(128) not null,
    payment_method varchar(128) not null,
    credit_period int not null,
    CONSTRAINT FK_CONTRACT_TYPE_CONTRACT
      FOREIGN KEY(type_id) 
	  REFERENCES contract_type(eid)
	  on delete cascade
);
--equipment
create table equipment_type(
    eid varchar(128) primary key,
    name varchar(128) not null
);

create table equipment(
    eid varchar(128) primary key,
    name varchar(128) not null,
    purchase_date varchar(128) not null,
    type_id varchar(128) not null,
    location varchar(128) not null,
    number_of_items int not null,
    unit_cost float not null,
    zip_code varchar(20) not null,
    vendor_id varchar(128) not null,
    description varchar(128) not null,
    CONSTRAINT FK_EQUIPMENT_TYPE_EQUIPMENT
      FOREIGN KEY(type_id) 
	  REFERENCES equipment_type(eid)
	  on delete cascade,
    CONSTRAINT FK_VENDOR_EQUIPMENT
      FOREIGN KEY(vendor_id) 
	  REFERENCES vendor(eid)
	  on delete cascade
);
--vendor
create table vendor(
    eid varchar(128) primary key,
    company_name varchar(128) not null,
    contact_name varchar(128) not null,
    email varchar(128) not null,
    website varchar(128) not null,
    phone_number varchar(128) not null,
    city varchar(128) not null,
    street varchar(128) not null,
    zip_code varchar(28) not null,
    location varchar(128) not null
);
--document table
create table document(
    eid varchar(128) primary key,
    document_type varchar(128),
    document_path varchar(128) not null,
    user_id varchar(128),
    facility_id varchar(128),
    safety_material_id varchar(128),
    contract_id varchar(128),
    equipment_id varchar(128),
    vendor_id varchar(128),
    task_id varchar(128),
    constraint fk_nufm_user_document
      foreign key(user_id) 
	  REFERENCES nufm_user(eid)
	  on delete cascade,
    constraint fk_safety_material_document
      foreign key(safety_material_id) 
	  REFERENCES safety_material(eid)
	  on delete cascade,
    constraint fk_contract_document
      foreign key(contract_id) 
	  REFERENCES contract(eid)
	  on delete cascade,
    constraint fk_equipment_document
      foreign key(equipment_id) 
	  REFERENCES equipment(eid)
	  on delete cascade,
	constraint fk_vendor_document
      foreign key(vendor_id) 
	  REFERENCES vendor(eid)
	  on delete cascade,
    constraint fk_task_document
      foreign key(task_id) 
	  REFERENCES task(eid)
	  on delete cascade,
	    constraint fk_facility_document
      foreign key(facility_id) 
	  REFERENCES facility(eid)
	  on delete cascade
);
--insert all roles 
insert into nufm_role values ('ROLE_PROPERTY_OWNER','PROPERTY OWNER','PROPERTY OWNER');
insert into nufm_role values ('ROLE_PROPERTY_OCCUPANT','PROPERTY OCCUPANT','PROPERTY OCCUPANT');
insert into nufm_role values ('ROLE_PURCHASING_OFFICER','PURCHASING OFFICER','PURCHASING OFFICER');
insert into nufm_role values ('ROLE_PROPERTY_WORKER','PROPERTY WORKER','PROPERTY WORKER');
insert into nufm_role values ('ROLE_PROPERTY_SUPERVISOR','PROPERTY SUPERVISOR','PROPERTY SUPERVISOR');
insert into nufm_role values ('ROLE_ADMIN','ADMIN','ADMIN');
insert into nufm_role values ('ROLE_CONTRACTOR','CONTRACTOR','CONTRACTOR');

--insert user
insert into nufm_user values ('ahmadkhouja@gmail.com','Ahmad Khouja','ahmadkhouja@gmail.com','$2a$11$gX3qXpymejq7rBwYA/qrqucYbeqNITEBGsjypDqcdEnKz8WPbnC8K','+96171846002','Lebanon','2020-12-12 01:24:23','2020-12-12 01:24:23');

--insert membership for user
insert into nufm_membership values ('membership1','ahmadkhouja@gmail.com','ROLE_ADMIN','2020-12-12 01:24:23');
insert into nufm_membership values ('membership2','ahmadkhouja@gmail.com','ROLE_PROPERTY_OWNER','2020-12-12 01:24:23');