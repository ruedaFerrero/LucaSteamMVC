create database LucaSteam;
use LucaSteam;

create table Games(
	id int unsigned not null auto_increment,
    name varchar(255) not null,
    platform varchar(255) not null,
    year int unsigned not null,
    genre varchar(255) not null,
    publisher varchar(255) not null,
    eu_sales double not null,
    primary key (id)
)