package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.FiatCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiatCurrencyRepository extends JpaRepository<FiatCurrency, Integer> {

    boolean existsById(String string);
}
