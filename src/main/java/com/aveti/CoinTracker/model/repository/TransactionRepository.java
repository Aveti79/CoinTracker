package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionBySellCurrencyIdOrderByTransactionTime(@Param("sellCurrency") String currency);

    List<Transaction> findTransactionByBuyCurrencyIdOrderByTransactionTime(@Param("buyCurrency") String currency);

    @Query("select t from Transaction t where t.buyCurrency.id=:currency or t.sellCurrency.id=:currency")
    List<Transaction> findTransactionsWithThisCurrencyId(@Param("currency") String currency);

    @Query("select sum(sellAmount) from Transaction where sellCurrency.id=:sellCurrency")
    Double getSumOfSellAmount(@Param("sellCurrency") String currency);

    @Query("select sum(buyAmount) from Transaction where buyCurrency.id=:buyCurrency")
    Double getSumOfBuyAmount(@Param("buyCurrency") String currency);

    @Query("select distinct(buyCurrency) from Transaction")
    List<Currency> findDistinctBuyCurrencies();

}