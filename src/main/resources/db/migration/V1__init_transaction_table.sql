drop table if exists transactions;
create table transactions (
        id int primary key auto_increment,
        type varchar (100) not null,
        transactionTime datetime,
        buyAmount double precision,
        buyCurrency varchar (12),
        sellAmount double precision,
        sellCurrency varchar (12),
        feeAmount double precision,
        feeCurrency varchar (12),
        comment varchar (250)
);