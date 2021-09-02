-- Audit Table

-- public.master_allergy definition

-- Drop table

-- DROP TABLE public.master_allergy;

CREATE TABLE public.master_allergy (
	allergy_id serial NOT NULL,
	allergy_name varchar(100) NULL,
	allergy_type varchar(100) NULL,
	clinical_info varchar(1000) NULL,
	CONSTRAINT master_allergy_pkey PRIMARY KEY (allergy_id)
);


-- public.master_diagnosis definition

-- Drop table

-- DROP TABLE public.master_diagnosis;

CREATE TABLE public.master_diagnosis (
	code int4 NOT NULL,
	description varchar(1000) NULL,
	is_depricated bool NULL,
	CONSTRAINT master_diagnosis_pkey PRIMARY KEY (code)
);


-- public.master_medication definition

-- Drop table

-- DROP TABLE public.master_medication;

CREATE TABLE public.master_medication (
	drug_id serial NOT NULL,
	drug_name varchar(100) NULL,
	drug_generic_name varchar(100) NULL,
	drug_brand_name varchar(100) NULL,
	drug_form varchar(100) NULL,
	drug_strength varchar(100) NULL,
	CONSTRAINT master_medication_pkey PRIMARY KEY (drug_id)
);


-- public.master_procedure definition

-- Drop table

-- DROP TABLE public.master_procedure;

CREATE TABLE public.master_procedure (
	code int4 NOT NULL,
	description varchar(1000) NULL,
	is_depricated bool NULL,
	CONSTRAINT master_procedure_pkey PRIMARY KEY (code)
);


-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
	role_id serial NOT NULL,
	role_name varchar(50) NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (role_id)
);


create type gender_type as enum('MALE', 'FEMALE', 'OTHER');

-- public.patient_details definition

-- Drop table

-- DROP TABLE public.patient_details;

CREATE TABLE public.patient_details (
	patient_id serial NOT NULL,
	title varchar(20) NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	email varchar(50) NULL,
	username varchar(100) NULL,
	"password" varchar(255) NULL,
	birth_date date NULL,
	race varchar(50),
	ethnicity varchar(50),
	languages varchar(50),
	address varchar(100),
	deleted bool NULL DEFAULT false,
	active bool NULL DEFAULT true,
	created_on timestamp NULL,
	updated_on timestamp NULL,
	CONSTRAINT patient_details_pkey PRIMARY KEY (patient_id)
);

alter table patient_details add column gender gender_type;


-- public.patient_diagnosis definition

-- Drop table

-- DROP TABLE public.patient_diagnosis;

CREATE TABLE public.patient_diagnosis (
	patient_id int4 NULL,
	diagnosis_id int4 NULL,
	CONSTRAINT patient_diagnosis_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT patient_diagnosis_fk_1 FOREIGN KEY (diagnosis_id) REFERENCES master_diagnosis(code)
);


-- public.patient_medication definition

-- Drop table

-- DROP TABLE public.patient_medication;

CREATE TABLE public.patient_medication (
	patient_id int4 NULL,
	medication_id int4 NULL,
	CONSTRAINT patient_medication_fk FOREIGN KEY (medication_id) REFERENCES master_medication(drug_id),
	CONSTRAINT patient_medication_fk_1 FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id)
);


-- public.patient_procedure definition

-- Drop table

-- DROP TABLE public.patient_procedure;

CREATE TABLE public.patient_procedure (
	patient_id int4 NULL,
	procedure_id int4 NULL,
	CONSTRAINT patient_procedure_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT patient_procedure_fk_1 FOREIGN KEY (procedure_id) REFERENCES master_procedure(code)
);


-- public.staff_details definition

-- Drop table

-- DROP TABLE public.staff_details;

CREATE TABLE public.staff_details (
	staff_id serial NOT NULL,
	title varchar(50) NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	email varchar(50) NULL,
	username varchar(100) NULL,
	"password" varchar(255) NULL,
	birth_date date NULL,
	role_id int4 NULL,
	emp_id int4 NULL,
	deleted bool NULL DEFAULT false,
	active bool NULL DEFAULT true,
	created_on timestamp NULL,
	updated_on timestamp NULL,
	CONSTRAINT staff_details_emp_id_key UNIQUE (emp_id),
	CONSTRAINT staff_details_pkey PRIMARY KEY (staff_id),
	CONSTRAINT staff_details_fk FOREIGN KEY (role_id) REFERENCES roles(role_id)
);


-- public.appointment definition

-- Drop table

-- DROP TABLE public.appointment;

CREATE TABLE public.appointment (
	appointment_id serial NOT NULL,
	meeting_title varchar(255) NULL,
	description varchar(1000) NULL,
	physician_id int4 NULL,
	patient_id int4 NULL,
	appointment_date date NULL,
	appointment_time time NULL,
	reason varchar(1000) NULL,
	CONSTRAINT appointment_pkey PRIMARY KEY (appointment_id),
	CONSTRAINT appointment_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT appointment_fk_1 FOREIGN KEY (physician_id) REFERENCES staff_details(emp_id)
);


-- public.emergency_details definition

-- Drop table

-- DROP TABLE public.emergency_details;

CREATE TABLE public.emergency_details (
	emergency_id serial NOT NULL,
	patient_id int4 NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	relationship varchar(50) NULL,
	email varchar(50) NULL,
	contact bigint NULL,
	address varchar(255) NULL,
	active bool NULL,
	CONSTRAINT emergency_details_pkey PRIMARY KEY (emergency_id),
	CONSTRAINT emergency_details_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id)
);


-- public.patient_allergies definition

-- Drop table

-- DROP TABLE public.patient_allergies;

CREATE TABLE public.patient_allergies (
	patient_id int4 NULL,
	allergy_id int4 NULL,
	CONSTRAINT patient_allergies_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT patient_allergies_fk_1 FOREIGN KEY (allergy_id) REFERENCES master_allergy(allergy_id)
);


CREATE TABLE public.patient_visit (
	visit_id int4 NOT NULL,
	appointment_id int4 NULL,
	height int4 NULL,
	weight int4 NULL,
	blood_pressure int4 NULL,
	body_pressure int4 NULL,
	respiration_rate int4 NULL,
	CONSTRAINT patient_visit_pk PRIMARY KEY (visit_id),
	CONSTRAINT patient_visit_fk FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);


CREATE SEQUENCE hospital_staff_seq  START 1;

CREATE SEQUENCE hospital_patient_seq START 1;

CREATE SEQUENCE patient_appointment_seq START 1;

CREATE SEQUENCE patient_visit_seq START 1;

CREATE SEQUENCE patient_emergency_details_seq START 1;

-- Auto-generated SQL script #202108302020
INSERT INTO public.roles (role_id,role_name)
	VALUES (2,'Physician');
INSERT INTO public.roles (role_id,role_name)
	VALUES (3,'Nurse');
INSERT INTO public.roles (role_id,role_name)
	VALUES (1,'Admin');
INSERT INTO public.roles (role_id,role_name)
	VALUES (4,'Patient');

Alter table patient_details drop column gender ;
Alter table patient_details add column gender varchar(20) ;