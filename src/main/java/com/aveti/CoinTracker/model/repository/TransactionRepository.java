package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionByBuyCurrency(@Param("buyCurrency") String currency);

    List<Transaction> findTransactionBySellCurrency(@Param("sellCurrency") String currency);

}