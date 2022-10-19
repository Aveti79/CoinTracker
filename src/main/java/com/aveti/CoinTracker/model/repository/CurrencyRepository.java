package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findById(String currency);

    boolean existsById(String currency);

    @Query("select c from Currency c where c.id like concat(:keyword,'%') or c.symbol like concat(:keyword,'%')")
    List<Currency> findCurrencyByKeyword(@Param("keyword") String keyword);
}
