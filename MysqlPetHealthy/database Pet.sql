use pethealthymanager;
drop database pethealthymanager;
create database pethealthymanager;

-- table user --
drop table user;
create table user(
  id bigint auto_increment primary key,
  user nvarchar(100) not null,
  password nvarchar(100) not null,
  role_id bigint not null,
  address nvarchar(255) not null,
  phone_number nvarchar(50) not null,
  email nvarchar(100) not null,
  birthday date not null
);
alter table user add first_name nvarchar(255);
alter table user add last_name nvarchar(255);

-- table role --
drop table role;
create table role(
	id bigint auto_increment primary key,
    name nvarchar(255) not null,
    code nvarchar(255) not null
);

select * from user;

-- nối 2 bảng user và role lại với nhau với mối quan hệ 1 - n --
alter table user add constraint fk_role foreign key(role_id) references role(id);