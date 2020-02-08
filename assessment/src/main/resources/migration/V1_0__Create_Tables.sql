/*create Role table */
CREATE TABLE role (
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  name varchar(128)  NOT NULL,
  description varchar(525)  NOT NULL,
  status varchar(16)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  is_hidden varchar(16)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE(unique_key)
);

/*create Permission table */
CREATE TABLE permission(
  id bigint IDENTITY(1, 1)  NOT NULL,
  name varchar(128)  NOT NULL,
  description varchar(525)  NOT NULL,
  code varchar(256)  NOT NULL,
  status varchar(16)  NULL,
  is_hidden varchar(16)  NOT NULL,
  PRIMARY KEY (id)
);

/*create User table */

CREATE TABLE "user"(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  first_name varchar(255)  NULL,
  last_name varchar(255)  NULL,
  email varchar(255)  NULL,
  "password" varchar(255)  NULL,
  phone varchar(16)  NULL,
  "address" text  NULL,
  role_id VARCHAR(32)  NOT NULL REFERENCES  role (unique_key),
  status varchar(16)  NOT NULL,
  last_login_date varchar(50)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  PRIMARY KEY (unique_key)
);

/*create Token table */
CREATE TABLE token(
  id bigint IDENTITY(1, 1)  NOT NULL,
  "user" varchar(32)  NULL REFERENCES "user" (unique_key),
  token varchar(64)  NULL,
  status varchar(16)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  expires_at datetime NULL,
);

/*create Role permission table */
CREATE TABLE role_permission(
  id bigint IDENTITY(1, 1)  NOT NULL,
  role_id bigint  NOT NULL REFERENCES role (id),
  permission_id bigint  NOT NULL REFERENCES permission (id),
  status varchar(16)  NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
);

/*create notification template table */
CREATE TABLE notification_template(
  id bigint IDENTITY(1, 1)  NOT NULL,
  action varchar(32)  NOT NULL,
  channel varchar(32)  NOT NULL,
  template varchar(255)  NOT NULL,
  fields varchar(max)  NOT NULL,
  subject varchar(255)  NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  is_active tinyint  NOT NULL
);

/*create Student table */
CREATE TABLE student(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  last_name TEXT NULL,
  first_name TEXT NULL,
  email TEXT  NOT NULL,
  sex varchar(255)  NULL,
  age varchar(255)  NOT NULL,
  phone varchar(255) NOT NULL,
  form varchar(255) NOT Null,
  religion varchar(255) NOT NULL,
  address varchar(255) NOT Null,
  name_of_next_of_kin varchar(255) NOT NULL,
  phone_number_of_next_of_kin varchar(255) NOT NULL,
  state_of_origin varchar(255) NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
  department varchar(255)  NULL,
  subjects_records varchar(255) NULL,
    PRIMARY KEY (id),
--     subject_record_key varchar(32) NOT NULL references subject_record (unique_key),
  UNIQUE(unique_key)
);

/*create Subject table */
CREATE TABLE subject(
  id bigint IDENTITY(1, 1)  NOT NULL,
  unique_key varchar(32)  NOT NULL,
  subject_name TEXT NOT NULL,
  subject_code TEXT NOT NULL,
  subject_teacher_name TEXT  NOT NULL,
  created_at datetime  NOT NULL,
  updated_at datetime NULL,
    PRIMARY KEY (id),
  UNIQUE(unique_key)
);


/*create SubjectRecord table */
CREATE TABLE subject_record(
id bigint IDENTITY (1,1) NOT NULL,
unique_key varchar(32) NOT NULL,
subject_key varchar(32)NOT  NULL,
student_key varchar(32) NOT NULL,
first_assessment VARCHAR (32)NULL ,
second_assessment varchar(32) NULL ,
third_assessment varchar(32) NULL ,
fourth_assessment varchar(32) NULL ,
examination varchar (32) NULL ,
total_score varchar (32) NULL ,
grade varchar (32) NULL ,
created_at datetime NOT NULL,
updated_at datetime NULL,
 PRIMARY KEY (id),
  UNIQUE(unique_key)
);



