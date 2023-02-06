package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findById(String currency);

    boolean existsById(String currency);

    @Query(nativeQuery = true, value = "SELECT c.id, c.name, c.symbol, c.type, cd.image " +
            "FROM CURRENCIES c left join CURRENCY_DETAILS cd on c.id=cd.id " +
            "where c.id LIKE concat(:keyword,'%') OR c.symbol LIKE concat(:keyword,'%') " +
            "order by  c.TYPE desc, cd.MARKET_CAP desc, LENGTH(c.symbol)")
    String[][] findCurrencyByKeyword(@Param("keyword") String keyword);
}