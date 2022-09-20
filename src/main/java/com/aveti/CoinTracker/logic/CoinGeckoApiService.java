package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CoinList;
import com.aveti.CoinTracker.model.repository.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinGeckoApiService {

    private final CoinRepository coinRepository;

    CoinGeckoApiService(final CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public void updateCoinsList(CoinList list) {
        coinRepository.saveAll(list.getCoinList());
    }

    public List<Coin> getCoinList() {
        return coinRepository.findAll();
    }
}
