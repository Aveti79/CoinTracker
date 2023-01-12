package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findById(String currency);

    boolean existsById(String currency);

    @Query("select c from Currency c where c.id like concat(:keyword,'%') or c.symbol like concat(:keyword,'%') order by length(c.symbol), length(c.id), c.symbol")
    List<Currency> findCurrencyByKeyword(@Param("keyword") String keyword);
}
