package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.CoinDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinDetailsRepository extends JpaRepository<CoinDetails, Integer> {

    Optional<CoinDetails> findById(String coinId);

}
