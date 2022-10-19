package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionByBuyCurrency(@Param("buyCurrency") String currency);

    List<Transaction> findTransactionBySellCurrency(@Param("sellCurrency") String currency);

    @Query("select sum(buyAmount) from Transaction where buyCurrency=:buyCurrency")
    Double getSumOfBuyAmount(@Param("buyCurrency") String currency);

    @Query("select sum (sellAmount) from Transaction where buyCurrency=:buyCurrency")
    Double getSumOfSellAmount(@Param("buyCurrency") String currency);

    @Query("select sum(sellAmount)/sum(buyAmount) from Transaction where buyCurrency=:buyCurrency")
    Double getUnitCostOfCurrency(@Param("buyCurrency") String currency);

    @Query(nativeQuery = true, value="select distinct buy_Currency from Transactions")
    List<String> findDistinctBuyCurrencies();

}