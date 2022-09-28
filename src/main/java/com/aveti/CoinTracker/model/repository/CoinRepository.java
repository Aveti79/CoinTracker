package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer> {

    Coin findById(String currency);

    boolean existsById(String currency);

    @Query("select c from Coin c where c.id like concat(:keyword,'%') or c.symbol like concat(:keyword,'%')")
    List<Coin> findCoinsByKeyword(@Param("keyword") String keyword);
}
