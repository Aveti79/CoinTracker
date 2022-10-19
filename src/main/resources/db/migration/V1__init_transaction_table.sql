drop table if exists currencies;
create table currencies
(
    id     varchar(64) primary key not null,
    symbol varchar(64),
    name   varchar(64)
);

drop table if exists transactions;
create table transactions
(
    id              int primary key auto_increment,
    type            varchar(64) not null,
    transaction_Time datetime,
    buy_Amount       double precision,
    buy_Currency     varchar(64),
    sell_Amount      double precision,
    sell_Currency    varchar(64),
    fee_Amount       double precision,
    fee_Currency     varchar(64),
    comment         varchar(512),
    foreign key (buy_Currency) references currencies(id),
    foreign key (sell_Currency) references currencies(id),
    foreign key (fee_Currency) references currencies(id)
);