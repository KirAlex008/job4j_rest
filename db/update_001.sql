create table person (
                        id serial primary key not null,
                        login varchar(2000),
                        password varchar(2000),
                        emp_id int
);

insert into person (login, password, emp_id) values ('name1', '123', 1);
insert into person (login, password, emp_id) values ('name2', '123', 1);
insert into person (login, password, emp_id) values ('name3', '123', 2);

create table employee (
                        id serial primary key not null,
                        name varchar(2000),
                        surname varchar(2000),
                        inn varchar(200),
                        hire timestamp without time zone not null default now()
);

insert into employee (name, surname, inn) values ('empname1', 'empsurname1', '1111');
insert into employee (name, surname, inn) values ('empname2', 'empsurname2', '2222');
