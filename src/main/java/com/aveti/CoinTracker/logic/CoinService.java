package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CurrencyDTO;
import com.aveti.CoinTracker.model.repository.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {

    private final CoinRepository coinRepository;

    CoinService(final CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public List<CurrencyDTO> coinAutocomplete(String keyword) {
        List<Coin> suggestionsList = coinRepository.findCoinsByKeyword(keyword);
        List<CurrencyDTO> currencyDTOList = new ArrayList<>();

        for (Coin coin : suggestionsList) {
            CurrencyDTO dto = new CurrencyDTO();
            dto.setLabel(coin.getSymbol().toUpperCase() + " " + coin.getName() + " (" + coin.getClass().getSimpleName() + ")");
            dto.setValue(coin.getId());
            currencyDTOList.add(dto);
        }
        return currencyDTOList;
    }
}
