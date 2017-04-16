drop table if exists city;
drop table if exists hotel;

create table city (id int auto_increment primary key, name varchar(30), state varchar(30), country varchar(30));
create table hotel (city int, name varchar(30), address varchar(30), zip varchar(30));

insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
insert into hotel(city, name, address, zip) values (1, 'Conrad Treasury Place', 'William & George Streets', '4001')