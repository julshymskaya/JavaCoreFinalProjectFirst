create table record_log
(
	id SERIAL PRIMARY KEY,
	date_time timestamp NOT NULL,
	file_name varchar(256) NOT NULL,
	message varchar(1024) NOT NULL,
	status varchar(1024) NOT NULL
);