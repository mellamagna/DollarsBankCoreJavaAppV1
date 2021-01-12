drop database if exists dollarsbank;

create database dollarsbank;
use dollarsbank;

create table account (
	user_id varchar(50) not null unique,
    password varchar(50) not null,
    primary key (user_id)
);
#describe account;
    
insert into account(user_id, password)
values
	('aurora1','1234'),
    ('mlamagna','1234'); 
    
create table customer (
	customer_id int auto_increment,
	user_id varchar(50) not null unique,
    name varchar(50) not null,
    address varchar(100),
	number char(10),
    primary key(customer_id),
    foreign key(user_id) references account(user_id)
);

insert into customer(user_id, name, address, number) 
values
	('aurora1', 'Aurora', '322 Valais Dr', '2011319322'),
    ('mlamagna', 'Mel Lamagna', '1200 Test Ave', '1234567890');
    
create table checking_account (
	account_id int auto_increment,
    user_id varchar(50) not null unique,
	balance long,
    primary key(account_id),
    foreign key(user_id) references account(user_id)
);

insert into checking_account(user_id, balance)
values
	('aurora1', 322322),
    ('mlamagna', 780);

create table savings_account (
	account_id int auto_increment,
    user_id varchar(50) not null unique,
	balance long,
    primary key(account_id),
    foreign key(user_id) references account(user_id)
);

insert into savings_account(user_id, balance)
values
	('aurora1', 19322000),
    ('mlamagna', 55000);
    
create table transaction_type
(
	id integer primary key,
    type varchar(20) not null unique
);

insert into transaction_type(id, type)
values
	(1, 'Deposit'),
    (2, 'Withdrawal'),
	(3, 'Transfer');
    
create table transaction_source
(
	id integer primary key,
    type varchar(20) not null unique
);

insert into transaction_source(id, type)
values
	(1, 'Checking'),
    (2, 'Savings');

create table transaction
(
	transaction_id int auto_increment,
	user_id varchar(50) not null,
    trans_date date not null,
    type int not null default 1,
    source int not null default 1,
	amount long,
    primary key(transaction_id),
	foreign key(user_id) references account(user_id)
);
insert into transaction(user_id, trans_date, type, source, amount)
values
    ('aurora1', current_date() - interval 9 day, 1, 2, 322000),
	('mlamagna', current_date() - interval 8 day, 1, 1, 200),
	('mlamagna', current_date() - interval 3 day, 2, 2, -1200);




