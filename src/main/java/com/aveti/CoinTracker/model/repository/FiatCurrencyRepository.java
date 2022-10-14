package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.FiatCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FiatCurrencyRepository extends JpaRepository<FiatCurrency, Integer> {

    boolean existsById(String string);

    @Query("select f from FiatCurrency f where f.id like concat(:keyword,'%') or lower(f.name) like concat('%',:keyword,'%')")
    List<FiatCurrency> findCurrenciesByKeyword(String keyword);
}
