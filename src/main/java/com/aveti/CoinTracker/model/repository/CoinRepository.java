package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer> {
}
