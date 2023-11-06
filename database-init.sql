CREATE TABLE client.Company (
   company_id serial primary key,
   company_name varchar(100) unique Not Null,
   company_website varchar(250),
   joindate timestamp not null
);

CREATE TABLE client.Contact (
   contact_id serial primary key,
   contact_firstname varchar(50) not null,
   contact_lastname varchar(50) not null,
   contact_email varchar(50) unique not null,
   contact_phone varchar(50),
   company_id int not null,
   foreign key (company_id) references Company (company_id)
)

CREATE TABLE employee.Department (
   department_id serial primary key,
   department_name varchar(50) unique not null
);

CREATE TABLE employee.Employee (
   employee_id serial primary key,
   employee_firstname varchar(50) not null,
   employee_lastname varchar(50) not null,
   employee_email varchar(50) unique not null,
   employee_phone varchar(50),
   employee_location_country varchar(50) not null,
   employee_location_city varchar(50) not null,
   department_id int,
   employee_role varchar(50) not null,
   employee_manager int,
   employee_startdate timestamp not null,
   employee_birthday timestamp,
   foreign key (department_id) references Department (department_id)
);

CREATE TABLE employee.Employee_AUTH (
   employee_id serial primary key,
   employee_password varchar(50) not null,
   employee_email varchar(50) unique not null,
   foreign key (employee_id) references employee.Employee (employee_id)
);

CREATE TABLE collaboration.Team (
   team_id serial primary key,
   team_name varchar(50) unique not null,
   team_creator int not null,
   team_creationdate timestamp not null,
   team_description varchar(500),
   team_type varchar(50) not null,
   team_department_id int
);

CREATE TABLE collaboration.Team_Member (
   team_member_id serial primary key,
   team_id int not null,
   employee_id int not null,
   joindate timestamp not null,
   role varchar(50) not null,
   foreign key (team_id) references Team (team_id)
);

CREATE TABLE collaboration.Announcement (
   announcement_id serial primary key,
   team_id int not null,
   announcement_creator int not null,
   announcement_creationdate timestamp not null,
   announcement_content varchar(500) not null
);

CREATE TABLE collaboration.Announcement_Seen (
   announcement_id int not null,
   employee_id int not null,
   primary key (announcement_id, employee_id),
   foreign key (announcement_id) references Announcement (announcement_id)
);

CREATE TABLE collaboration.Event (
   event_id serial primary key,
   event_creation_team_id int not null,
   event_creator int not null,
   event_creationdate timestamp not null,
   event_type varchar(50) not null,
   event_title varchar(100) not null,
   event_description varchar(500),
   event_expired bool not null,
   event_last_updatedate timestamp not null,
   foreign key (event_creation_team_id) references Team (team_id)
);

CREATE TABLE collaboration.Document_Event (
   document_event_id serial primary key,
   event_event_id int not null,
   document_link varchar(100),
   deadline timestamp,
   foreign key (event_event_id) references collaboration.Event(event_id)
);

CREATE TABLE collaboration.Meeting_Event (
   meeting_event_id serial primary key,
   event_event_id int not null,
   meeting_virtual bool not null,
   meeting_location varchar(100),
   meeting_starttime timestamp,
   meeting_endtime timestamp,
   meeting_note_link varchar(100),
   meeting_agenda_link varchar(100),
   meeting_type varchar(50),
   foreign key (event_event_id) references collaboration.Event(event_id)
);

CREATE TABLE collaboration.Activity_Event (
   activity_event_id serial primary key,
   event_event_id int not null,
   activity_virtual bool not null,
   activity_location varchar(100),
   activity_starttime timestamp,
   activity_endtime timestamp,
   foreign key (event_event_id) references collaboration.Event(event_id)
);

CREATE TABLE collaboration.Event_Collaboration (
   event_collaboration_id primary key,
   event_id int not null,
   team_id int not null,
   invitedate timestamp not null,
   accept_status bool not null,
   team_role varchar(50) not null,
   foreign key (event_id) references collaboration.Event (event_id),
   foreign key (team_id) references collaboration.Team (team_id)
);

CREATE TABLE collaboration.Ticket (
   ticket_id serial primary key,
   ticket_creator int not null,
   ticket_creationdate timestamp not null,
   ticket_title varchar(100) not null,
   ticket_description varchar(500) not null,
   ticket_status varchar(50) not null
);

CREATE TABLE collaboration.Ticket_Assign (
   ticket_assign_id serial primary key,
   ticket_ticket_id int not null,
   employee_id int not null,
   team_id int,
   role varchar(50) not null,
   foreign key (ticket_id) references collaboration.Ticket (ticket_id)
);

CREATE TABLE collaboration.Ticket_Log(
   ticket_log_id serial primary key,
   ticket_id int not null,
   ticket_log_creator int not null,
   ticket_log_creationdate timestamp not null,
   ticket_log_content varchar(500) not null,
   foreign key (ticket_id) references collaboration.Ticket (ticket_id)
);

CREATE TABLE account.Account (
   account_id serial primary key,
   account_type varchar(50) not null,
   company_id int unique not null,
   account_current_status varchar(50) not null,
   account_current_responsible_department_id int not null,
   bidding_personnel int,
   sales_personnel int,
   solution_architect_personnel int,
   customer_success_personnel int
   account_creationdate timestamp not null,
   account_last_updatedate timestamp not null
);

CREATE TABLE collaboration.Team_Account (
   team_id int not null,
   account_id int not null,
   primary key (team_id, account_id),
   foreign key (team_id) references Team (team_id)
);

CREATE TABLE account.Document_Category (
   category_id serial primary key,
   category_name varchar(50) unique not null,
   category_type varchar(50) not null,
   category_responsible_team int,
);

CREATE TABLE account.Document (
   document_id serial primary key,
   document_name varchar(100) unique not null,
   category_id int not null,
   account_id int,
   document_link varchar(100) unique,
   document_format varchar(10),
   foreign key (category_id) references account.Document_Category (category_id)
);

INSERT INTO client.Company (company_name, company_website, joindate)
VALUES ('Apple', 'https://www.apple.com', '2023-07-12');
INSERT INTO client.Company (company_name, company_website, joindate)
VALUES ('Tiktok', 'https://www.tiktok.com', '2023-07-25');

INSERT INTO client.Contact (contact_firstname, contact_lastname, contact_email, contact_phone, company_id)
VALUES ('Alice', 'Abbey', 'aa@apple.com', '+1 2820103131', 1);
INSERT INTO client.Contact (contact_firstname, contact_lastname, contact_email, contact_phone, company_id)
VALUES ('Bob', 'Barclay', 'bb@tiktok.com', '+1 8192129321', 2);

INSERT INTO employee.Department (department_name)
VALUES ('Bidding');
INSERT INTO employee.Department (department_name)
VALUES ('Sales');
INSERT INTO employee.Department (department_name)
VALUES ('Solution Architect');
INSERT INTO employee.Department (department_name)
VALUES ('Customer Success');

INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('Celina', 'Cooper', 'cc@impact.com', '+1 9283121312', 'United States', 'Pittsburgh', 1, '1997-05-05', '2022-01-08', 'Manager', null);
INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('David', 'Duncan', 'dd@impact.com', '+1 9283121312', 'United States', 'New York City', 1, '1994-09-26', '2022-01-19', 'Specialist', 1);
INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('Elisa', 'Edwards', 'ee@impact.com', '+1 8281290122', 'United States', 'Seattle', 2, '1998-07-13', '2022-05-01', 'Manager', null);
INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('Fiona', 'Ford', 'ff@impact.com', '+1 6665291030', 'United States', 'Miami', 3, '1999-12-01', '2022-07-15', 'Manager', null);
INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('Georgina', 'Garcia', 'gg@impact.com', '+86 15627990987', 'China', 'Shanghai', 3, '1995-11-11', '2022-11-08', 'Specialist', 4);
INSERT INTO employee.Employee (employee_firstname, employee_lastname, employee_email, employee_phone, employee_location_country, employee_location_city, department_id, employee_birthday, employee_startdate, employee_role, employee_manager)
VALUES ('Helen', 'Hamilton', 'hh@impact.com', '+61 0928312938', 'Australia', 'Adelaide', 4, '1996-02-28', '2022-01-08', 'Manager', null);

INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('1', 'fdavfeqrwqfreqfrq', 'cc@impact.com')
INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('2', 'vfjiodauf89pd8apy8fr', 'dd@impact.com')
INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('3', '8989r0fahvuiroenalvf', 'ee@impact.com')
INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('4', '8wphuopp8pa89pahuioea', 'ff@impact.com')
INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('5', 'uioy7o77yoibhkafrfr6*', 'gg@impact.com')
INSERT INTO employee.Employee_AUTH (employee_id, employee_password, employee_email)
VALUES ('6', 'efhuipwhuiofuoa$#$', 'hh@impact.com')

INSERT INTO collaboration.Team (team_name, team_creator, team_creationdate, team_description, team_type, team_department_id)
VALUES ('Bidding Department Team', 1, '2023-08-01', 'Default team for bidding department', 'corporate', 1);
INSERT INTO collaboration.Team (team_name, team_creator, team_creationdate, team_description, team_type, team_department_id)
VALUES ('Sales Department Team', 3, '2023-08-01', 'Default team for sales department', 'corporate', 2);
INSERT INTO collaboration.Team (team_name, team_creator, team_creationdate, team_description, team_type, team_department_id)
VALUES ('Solution Architect Department Team', 4, '2023-08-01', 'Default team for solution architect department', 'corporate', 3);
INSERT INTO collaboration.Team (team_name, team_creator, team_creationdate, team_description, team_type, team_department_id)
VALUES ('Customer Success Department Team', 6, '2023-08-01', 'Default team for customer success department', 'corporate', 4);
INSERT INTO collaboration.Team (team_name, team_creator, team_creationdate, team_description, team_type, team_department_id)
VALUES ('Test Database Team', 4, '2023-08-05', 'Test team for database initialization', 'technology', null);

INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (1, 1, '2023-08-01', 'owner');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (1, 2, '2023-08-09', 'editor');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (2, 3, '2023-08-01', 'owner');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (3, 4, '2023-08-02', 'owner');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (3, 5, '2023-08-03', 'editor');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (4, 6, '2023-08-01', 'owner');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (5, 4, '2023-08-11', 'owner');
INSERT INTO collaboration.Team_Member (team_id, employee_id, joindate, role)
VALUES (5, 6, '2023-08-11', 'viewer');

INSERT INTO collaboration.Announcement (team_id, announcement_creator, announcement_creationdate, announcement_content)
VALUES (1, 1, '2023-08-01', 'This is the first announcement in bidding team');
INSERT INTO collaboration.Announcement (team_id, announcement_creator, announcement_creationdate, announcement_content)
VALUES (3, 4, '2023-08-02', 'This is the first announcement in solution architect team');
INSERT INTO collaboration.Announcement (team_id, announcement_creator, announcement_creationdate, announcement_content)
VALUES (5, 4, '2023-08-011', 'This is the first announcement in database test team');

INSERT INTO collaboration.Event (event_creation_team_id, event_creator, event_type, event_title, event_description, event_creationdate)
VALUES (1, 2, 'Document', 'September potential client preparation', 'Prepare a list of potential clients to reach out in September', '2023-08-07');
INSERT INTO collaboration.Event (event_creation_team_id, event_creator, event_type, event_title, event_description, event_creationdate)
VALUES (1, 1, 'Meeting', 'Meet with Apple marketing team', 'Second meeting with apple marketing team to discuss pricing', '2023-08-08');
INSERT INTO collaboration.Event (event_creation_team_id, event_creator, event_type, event_title, event_description, event_creationdate)
VALUES (3, 5, 'Document', 'Solution overview for Tiktok', 'Prepare the solution overview for Tiktok', '2023-08-05');
INSERT INTO collaboration.Event (event_creation_team_id, event_creator, event_type, event_title, event_description, event_creationdate)
VALUES (5, 4, 'Activity', 'Business dinner with APEC tech team', 'dinner with APEC tech team on Aug 13', '2023-08-10');

INSERT INTO collaboration.Document_Event (event_event_id, document_link, deadline)
VALUES (1, 'htts://www.googledoc.com/testdocumentevent1', '2023-08-30');
INSERT INTO collaboration.Document_Event (event_event_id, document_link, deadline)
VALUES (3, 'htts://www.googledoc.com/testdocumentevent4', '2023-09-02');

INSERT INTO collaboration.Meeting_Event (event_event_id, meeting_virtual, meeting_location, meeting_starttime, meeting_endtime, meeting_note_link, meeting_agenda_link, meeting_type)
VALUES (2, false, 'C315 Meeting Room', '2023-08-25 10:00:00', '2023-08-25 12:00:00', 'https://www.googledoc.com/testmeetingnote2', 'https://www.googledoc.com/testmeetingagenda2', 'product');

INSERT INTO collaboration.Activity_Event (event_event_id, activity_virtual, activity_location, activity_starttime, activity_endtime)
VALUES (4, true, 'https://www.zoom.com/testactivitylink4', '2023-08-26 15:00:00', '2023-08-26 18:00:00');


INSERT INTO collaboration.Event_Collaboration (event_id, team_id, invitedate, accept_status, team_role)
VALUES (2, 2, '2023-08-08', true, 'collaborator');
INSERT INTO collaboration.Event_Collaboration (event_id, team_id, invitedate, accept_status, team_role)
VALUES (4, 4, '2023-08-11', false, 'collaborator');

INSERT INTO collaboration.Ticket (ticket_creator, ticket_creationdate, ticket_title, ticket_description, ticket_status)
VALUES (5, '2023-08-20', 'Connection Error: cannot get Adidas report', 'Hi, I cannot get access to Adidas weekly report in the report section, it keeps loading with no result.', 'pending');
INSERT INTO collaboration.Ticket (ticket_creator, ticket_creationdate, ticket_title, ticket_description, ticket_status)
VALUES (4, '2023-08-16', 'Requirement need confirmation', 'Heyy, Tiktok would like to change their account type, can you help with the process and reevaluate their requirement?', 'resolved');

INSERT INTO collaboration.Ticket_Assign (ticket_id, employee_id, role)
VALUES (1, 5, 'assignee');
INSERT INTO collaboration.Ticket_Assign (ticket_id, employee_id, role)
VALUES (1, 4, 'superviser');
INSERT INTO collaboration.Ticket_Assign (ticket_id, employee_id, role)
VALUES (2, 3, 'assignee');

INSERT INTO collaboration.Ticket_Log (ticket_id, ticket_log_creator, ticket_log_creationdate, ticket_log_content)
VALUES (2, 3, '2023-08-17', 'On it, we will schedule a meeting to finalize the details, I will update you after that');

INSERT INTO account.Account (account_type, company_id, account_current_status, account_current_responsible_department_id, bidding_personnel, sales_personnel, solution_architect_personnel, customer_success_personnel, account_creationdate, account_last_updatedate)
VALUES ('standard', 1, 'contract signing', 2, 1, 3, 4, 6, '2023/07/19 00:00:00', '2023/07/19 00:00:00');
INSERT INTO account.Account (account_type, company_id, account_current_status, account_current_responsible_department_id, bidding_personnel, sales_personnel, solution_architect_personnel, customer_success_personnel, account_creationdate, account_last_updatedate)
VALUES ('premium', 2, 'technical implementation', 3, 2, 3, 4, 6, '2023/07/31 00:00:00', '2023/07/31 00:00:00');

INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('background check', 'marketing', 1);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('contact revision', 'contract', 2);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('requirement analysis', 'pre-sales', 2);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('solution overview', 'pre-sales', 3);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('technical implementation', 'technical integration', 3);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('test log', 'technical integration', 3);
INSERT INTO account.Document_Category (category_name, category_type, category_responsible_team)
VALUES ('training', 'training', 4);

INSERT INTO account.Document (document_name, category_id, account_id, document_link, document_format)
VALUES ('Apple background information collection', 1, 1, 'https://amazons3/collaspace/fakelink1', 'zip');
INSERT INTO account.Document (document_name, category_id, account_id, document_link, document_format)
VALUES ('Apple requirement analysis', 3, 1, 'https://amazons3/collaspace/fakelink2', 'doc');
INSERT INTO account.Document (document_name, category_id, account_id, document_link, document_format)
VALUES ('Apple contract version 1.0', 2, 1, 'https://amazons3/collaspace/fakelink3', 'pdf');
INSERT INTO account.Document (document_name, category_id, account_id, document_link, document_format)
VALUES ('Tictok technical implementation plan 1.0', 5, 2, 'https://amazons3/collaspace/fakelink4', 'md');

/* trigger function for employee id*/
CREATE OR REPLACE FUNCTION employee.EMPLOYEE_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('EMPLOYEE_EMPLOYEE_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION employee.EMPLOYEE_ID()
    OWNER TO postgres;

/* trigger function for event id*/
CREATE OR REPLACE FUNCTION collaboration.EVENT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('EVENT_EVENT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.EVENT_ID()
    OWNER TO postgres;

/* trigger function for document_event id*/
CREATE OR REPLACE FUNCTION collaboration.DOCUMENT_EVENT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('DOCUMENT_EVENT_DOCUMENT_EVENT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.DOCUMENT_EVENT_ID()
    OWNER TO postgres;

/* trigger function for meeting_event id*/
CREATE OR REPLACE FUNCTION collaboration.MEETING_EVENT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('MEETING_EVENT_MEETING_EVENT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.MEETING_EVENT_ID()
    OWNER TO postgres;

/* trigger function for activity_event id*/
CREATE OR REPLACE FUNCTION collaboration.ACTIVITY_EVENT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('ACTIVITY_EVENT_ACTIVITY_EVENT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.ACTIVITY_EVENT_ID()
    OWNER TO postgres;

/* trigger function for event_collaboration id*/
CREATE OR REPLACE FUNCTION collaboration.EVENT_COLLABORATION_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('EVENT_COLLABORATION_EVENT_COLLABORATION_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.EVENT_COLLABORATION_ID()
    OWNER TO postgres;

/* trigger function for team id*/
CREATE OR REPLACE FUNCTION collaboration.TEAM_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('TEAM_TEAM_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.TEAM_ID()
    OWNER TO postgres;

/* trigger function for team member id*/
CREATE OR REPLACE FUNCTION collaboration.TEAM_MEMBER_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('TEAM_MEMBER_TEAM_MEMBER_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.TEAM_ID()
    OWNER TO postgres;


/* trigger function for annoucement id*/
CREATE OR REPLACE FUNCTION collaboration.ANNOUNCEMENT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('ANNOUNCEMENT_ANNOUNCEMENT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION collaboration.ANNOUNCEMENT_ID()
    OWNER TO postgres;

/* trigger function for company id*/
CREATE OR REPLACE FUNCTION client.COMPANY_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('COMPANY_COMPANY_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION client.COMPANY_ID()
    OWNER TO postgres;

/* trigger function for contact id*/
CREATE OR REPLACE FUNCTION client.CONTACT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('CONTACT_CONTACT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

/* trigger function for account id*/

CREATE OR REPLACE FUNCTION account.ACCOUNT_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('ACCOUNT_ACCOUNT_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION account.ACCOUNT_ID()
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION employee.EMPLOYEE_AUTH_ID()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
   New.id:=nextval('EMPLOYEE_AUTH_EMPLOYEE_ID_SEQ');
   Return NEW;
 END;
 
$BODY$;

ALTER FUNCTION employee.EMPLOYEE_AUTH_ID()
    OWNER TO postgres;