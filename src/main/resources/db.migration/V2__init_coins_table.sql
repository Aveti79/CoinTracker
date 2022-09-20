drop table if exists coins;
create table coins (
        id varchar (64) not null,
        symbol varchar (32),
        name varchar (64)
);