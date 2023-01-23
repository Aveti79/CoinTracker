package com.aveti.CoinTracker.model.repository;

import com.aveti.CoinTracker.model.CoinDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinDetailsRepository extends JpaRepository<CoinDetails, Integer> {
}
