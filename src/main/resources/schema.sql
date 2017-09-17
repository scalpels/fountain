drop table if exists topic;

create table topic (id bigint auto_increment primary key,
                    title varchar(30),
                    description varchar(30),
                    created_by varchar(50),
                    created_on timestamp null,
                    last_modified_by varchar(50),
                    last_modified_on timestamp null);
