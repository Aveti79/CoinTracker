drop table if exists fiat_currencies;
create table fiat_currencies (
        id varchar (64) not null,
        symbol varchar (32),
        name varchar (64)
);