package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinDetails;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.repository.CoinDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinDetailsService {

    private final CoinDetailsRepository detailsRepository;
    private final CoinGeckoApiService apiService;

    CoinDetailsService(final CoinDetailsRepository detailsRepository, final CoinGeckoApiService apiService) {
        this.detailsRepository = detailsRepository;
        this.apiService = apiService;
    }

    public String getCurrencyLogo(Currency currency) {
        if (currency.getType().equals("COIN")) {
            var coinDetail = detailsRepository.findById(currency.getId());
            if (coinDetail.isPresent()) {
                return coinDetail.get().getImage();
            } else {
                return Optional.ofNullable(apiService.getSingleCoinDetailsFromAPI(currency.getId())).map(CoinDetails::getImage).orElse("");
            }
        }
        else {
            return "icons/" + currency.getId() + "_icon.webp";
        }
    }
}
