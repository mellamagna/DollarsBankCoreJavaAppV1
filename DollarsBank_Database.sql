drop database if exists dollarsbank;

create database dollarsbank;
use dollarsbank;

create table account (
	user_id varchar(50) not null unique,
    password varchar(50) not null,
    primary key (user_id)
);
#describe account;
    
insert into account(user_id, password) values('aurora1','1234'); 
insert into account(user_id, password) values('mlamagna','1234'); 
    
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
	values('aurora1', 'Aurora', '322 Valais Dr', '2011319322');
insert into customer(user_id, name, address, number) 
	values('mlamagna', 'Mel Lamagna', '1200 Test Ave', '1234567890');

create table savings_account (
	account_id int auto_increment,
    user_id varchar(50) not null unique,
	balance long,
    primary key(account_id),
    foreign key(user_id) references account(user_id)
);

insert into savings_account(user_id, balance)
	values('aurora1', 19322000);
insert into savings_account(user_id, balance)
	values('mlamagna', 55000);

create table transaction
(
	transaction_id int auto_increment,
	user_id varchar(50) not null,
    trans_date date not null,
	amount long,
    primary key(transaction_id),
	foreign key(user_id) references account(user_id)
);

insert into transaction(user_id, trans_date, amount)
	values('aurora1', current_date() - interval 9 day, 322000);
insert into transaction(user_id, trans_date, amount)
	values('mlamagna', current_date() - interval 8 day, 200);
insert into transaction(user_id, trans_date, amount)
	values('mlamagna', current_date() - interval 3 day, -1200);




