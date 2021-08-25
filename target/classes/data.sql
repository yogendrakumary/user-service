-- public.mst_allergy definition

-- Drop table

-- DROP TABLE public.mst_allergy;

CREATE TABLE public.mst_allergy (
	allergy_id serial NOT NULL,
	allergy_name varchar(100) NULL,
	allergy_type varchar(100) NULL,
	clinical_info varchar(1000) NULL,
	CONSTRAINT mst_allergy_pkey PRIMARY KEY (allergy_id)
);


-- public.mst_diagnosis definition

-- Drop table

-- DROP TABLE public.mst_diagnosis;

CREATE TABLE public.mst_diagnosis (
	code int4 NOT NULL,
	description varchar(1000) NULL,
	is_depricated bool NULL,
	CONSTRAINT mst_diagnosis_pkey PRIMARY KEY (code)
);


-- public.mst_medication definition

-- Drop table

-- DROP TABLE public.mst_medication;

CREATE TABLE public.mst_medication (
	drug_id serial NOT NULL,
	drug_name varchar(100) NULL,
	drug_generic_name varchar(100) NULL,
	drug_brand_name varchar(100) NULL,
	drug_form varchar(100) NULL,
	drug_strength varchar(100) NULL,
	CONSTRAINT mst_medication_pkey PRIMARY KEY (drug_id)
);


-- public.mst_procedure definition

-- Drop table

-- DROP TABLE public.mst_procedure;

CREATE TABLE public.mst_procedure (
	code int4 NOT NULL,
	description varchar(1000) NULL,
	is_depricated bool NULL,
	CONSTRAINT mst_procedure_pkey PRIMARY KEY (code)
);


-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
	role_id serial NOT NULL,
	role_name varchar(30) NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (role_id)
);


-- public.patient_details definition

-- Drop table

-- DROP TABLE public.patient_details;

CREATE TABLE public.patient_details (
	patient_id serial NOT NULL,
	title varchar(30) NULL,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	email varchar(100) NULL,
	username varchar(100) NULL,
	"password" varchar(255) NULL,
	birth_date date NULL,
	role_id int4 NULL,
	deleted bool NULL DEFAULT false,
	active bool NULL DEFAULT true,
	created_on timestamp NULL,
	updated_on timestamp NULL,
	CONSTRAINT patient_details_pkey PRIMARY KEY (patient_id),
	CONSTRAINT patient_details_fk FOREIGN KEY (role_id) REFERENCES roles(role_id)
);


-- public.patient_diagnosis definition

-- Drop table

-- DROP TABLE public.patient_diagnosis;

CREATE TABLE public.patient_diagnosis (
	patient_id int4 NULL,
	diagnosis_id int4 NULL,
	CONSTRAINT patient_diagnosis_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT patient_diagnosis_fk_1 FOREIGN KEY (diagnosis_id) REFERENCES mst_diagnosis(code)
);


-- public.patient_medication definition

-- Drop table

-- DROP TABLE public.patient_medication;

CREATE TABLE public.patient_medication (
	patient_id int4 NULL,
	medication_id int4 NULL,
	CONSTRAINT patient_medication_fk FOREIGN KEY (medication_id) REFERENCES mst_medication(drug_id),
	CONSTRAINT patient_medication_fk_1 FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id)
);


-- public.patient_procedure definition

-- Drop table

-- DROP TABLE public.patient_procedure;

CREATE TABLE public.patient_procedure (
	patient_id int4 NULL,
	procedure_id int4 NULL,
	CONSTRAINT patient_procedure_fk FOREIGN KEY (patient_id) REFERENCES patient_details(patient_id),
	CONSTRAINT patient_procedure_fk_1 FOREIGN KEY (procedure_id) REFERENCES mst_procedure(code)
);


-- public.staff_details definition

-- Drop table

-- DROP TABLE public.staff_details;

CREATE TABLE public.staff_details (
	staff_id serial NOT NULL,
	title varchar(30) NULL,
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	email varchar(100) NULL,
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
	first_name varchar(100) NULL,
	last_name varchar(100) NULL,
	relationship varchar(100) NULL,
	email varchar(100) NULL,
	contact int4 NULL,
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
	CONSTRAINT patient_allergies_fk_1 FOREIGN KEY (allergy_id) REFERENCES mst_allergy(allergy_id)
);

CREATE SEQUENCE hospital_staff_seq  START 1;

CREATE SEQUENCE hospital_patient_seq START 1;

CREATE SEQUENCE patient_appointment_seq START 1;

CREATE SEQUENCE patient_visit_seq START 1;

CREATE SEQUENCE patient_emergency_details_seq START 1;

