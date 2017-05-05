drop table if exists topic;

create table topic (id bigint  primary key, title varchar(30), description varchar(30),created_on timestamp,last_modified_on timestamp);

insert into topic (id,title, description,created_on,last_modified_on) values (1,'Security Network', '安全网络','2017-04-17','2017-04-17');
insert into topic (id,title, description,created_on,last_modified_on) values (2,'Java', '一门编程语言','2017-04-17','2017-04-17');
insert into topic (id,title, description,created_on,last_modified_on) values (3,'SAP', 'ERP软件提供商','2017-04-17','2017-04-17');
