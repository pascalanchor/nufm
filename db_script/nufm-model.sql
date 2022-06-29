create table specialization(
    eid varchar(128) primary key,
    name varchar(128) not null
);
--Security part
create table nufm_user(
    eid varchar(128) primary key,--email is the person id
    full_name varchar(128) not null,
    password varchar(128) not null,
    spec_id varchar(128) not null,
    national_id varchar(128) not null,
    phone varchar(128) not null,
    creation_date TIMESTAMP not null,
    constraint fk_specialization_nufm_user
      foreign key(spec_id) 
	  REFERENCES specialization(eid)
);

create table nufm_role(
    eid varchar(128) primary key,
    name  varchar(128) not null,
    description varchar(128) not null
);

create table user_role(
    eid varchar(128) primary key,
    user_id varchar(128) not null,
    role_id varchar(128) not null,
    creation_date TIMESTAMP not null,
    constraint fk_nufm_user_user_role
      foreign key(user_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_nufm_role_user_role
      foreign key(role_id) 
	  REFERENCES nufm_role(eid)   
);
--worker/contractor part
create table attendance(
    eid varchar(128) primary key,
    worker_id varchar(128) not null,
    status varchar(128) not null,
    constraint fk_nufm_user_attendance
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
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
    parent_id varchar(128) not null,
    name varchar(128) not null,
    type_id varchar(128) not null,
    occupant_id varchar(128) not null,
    location varchar(128) not null,
    creation_date TIMESTAMP not null,
    constraint fk_facility_facility
      foreign key(parent_id) 
	  REFERENCES facility(eid),
    constraint fk_nufm_user_facility
      foreign key(occupant_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_facility_type_facility
      foreign key(type_id) 
	  REFERENCES facility_type(eid)
);

create table equipment(
    eid varchar(128) primary key,
    facility_id varchar(128) not null,
    name varchar(128) not null,
    specification varchar(128) not null,
    monitoring_rules varchar(128) not null,
    location varchar(128) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    constraint fk_facility_equipment
      foreign key(facility_id) 
	  REFERENCES facility(eid)
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
	  REFERENCES facility(eid),
    constraint fk_contractor_project
      foreign key(contractor_id) 
	  REFERENCES contractor(eid)
);

create table project_worker(
    eid varchar(128) primary key,
    project_id varchar(128) not null,
    worker_id varchar(128) not null,
    constraint fk_project_project_worker
      foreign key(project_id) 
	  REFERENCES project(eid),
    constraint fk_nufm_user_project
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid)
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
    date_from date not null,
    date_to  date not null,
    status varchar(128) not null,
    comment varchar(128) not null,
    document_id varchar(128) not null,
    constraint fk_facility_task
      foreign key(facility_id) 
	  REFERENCES facility(eid),
    constraint fk_project_task
      foreign key(project_id) 
	  REFERENCES project(eid),
    constraint fk_task_type_task
      foreign key(type_id) 
	  REFERENCES task_type(eid)
);

create table task_request(
    eid varchar(128) primary key,
    sender_id varchar(128) not null,
    receiver_id varchar(128) not null,
    task_id varchar(128) not null,
    status varchar(128) not null,
    date TIMESTAMP not null,
    constraint fk_nufm_user_sender_task_request
      foreign key(sender_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_nufm_user_receiver_task_request
      foreign key(receiver_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_task_task_request
      foreign key(task_id) 
	  REFERENCES task(eid)
);

create table task_notification(
    eid varchar(128) primary key,
    sender_id varchar(128) not null,
    receiver_id varchar(128) not null,
    task_id varchar(128) not null,
    comment varchar(128) not null,
    Document_id varchar(128) not null,
    status varchar(128) not null,
    date TIMESTAMP not null,
    constraint fk_nufm_user_sender_task_notification
      foreign key(sender_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_nufm_user_receiver_task_notification
      foreign key(receiver_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_task_task_notification
      foreign key(task_id) 
	  REFERENCES task(eid)
);

--assigning tasks part

create table worker_task(
    eid varchar(128) primary key,
    worker_id varchar(128) not null,
    task_id varchar(128) not null,
    assigned_date TIMESTAMP not null,
    constraint fk_nufm_user_worker_task
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_task_worker_task
      foreign key(task_id) 
	  REFERENCES task(eid)
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
create table safety_material(
    eid varchar(128) primary key,
    name varchar(128) not null,
    status varchar(128) not null,
    type varchar(128) not null
);

create table safety_worker(
    eid varchar(128) primary key,
    material_id varchar(128) not null,
    worker_id varchar(128) not null,
    constraint fk_nufm_user_safety_worker
      foreign key(worker_id) 
	  REFERENCES nufm_user(eid),
    constraint fk_safety_material_safety_worker
      foreign key(material_id) 
	  REFERENCES safety_material(eid)
);

--invoice
create table invoice(
    eid varchar(128) primary key,
    invoice_to varchar(128) not null,
    deliver_to varchar(128) not null,
    quantity decimal(10,2) not null,
    price decimal(10,2) not null,
    description varchar(128) not null,
    sub_total decimal(10,2) not null,
    sales_tax decimal(10,2) not null,
    invoice_total decimal(10,2) not null,
    status varchar(128) not null,
    date TIMESTAMP not null
);
