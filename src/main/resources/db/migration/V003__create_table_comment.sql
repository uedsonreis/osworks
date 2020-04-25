create table comment (
	id serial not null,
	description varchar(255) not null,
	send_date timestamp not null,
	service_order_id integer not null,
	primary key (id)
);

alter table comment add constraint fk_comment_service_order
foreign key (service_order_id) references service_order (id);