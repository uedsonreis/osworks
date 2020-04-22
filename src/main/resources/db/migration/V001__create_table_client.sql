create table client (
	id serial not null,
	name varchar(60) not null,
	email varchar(255) not null,
	phone varchar(20),
	primary key (id)
);