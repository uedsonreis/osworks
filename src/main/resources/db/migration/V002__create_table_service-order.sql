create table service_order (
	id serial not null,
	description varchar(255) not null,
	price decimal(10,2) not null,
	status varchar(20) not null,
	opening_date timestamp not null,
	closing_date timestamp,
	client_id integer not null,
	
	primary key (id)
);

alter table service_order add constraint fk_service_order_client
foreign key (client_id) references client (id);