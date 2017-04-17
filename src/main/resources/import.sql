drop table if exists topic;

create table topic (id bigint auto_increment primary key, title varchar(30), description varchar(30),created_on timestamp,last_modified_on timestamp);

insert into topic (title, description,created_on,last_modified_on) values ('Security Network', '安全网络','2017-04-17','2017-04-17');
insert into topic (title, description,created_on,last_modified_on) values ('Java', '一门编程语言','2017-04-17','2017-04-17');
insert into topic (title, description,created_on,last_modified_on) values ('SAP', 'ERP软件提供商','2017-04-17','2017-04-17');
