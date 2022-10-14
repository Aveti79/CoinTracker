package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.CurrencyDTO;
import com.aveti.CoinTracker.model.FiatCurrency;
import com.aveti.CoinTracker.model.repository.CoinRepository;
import com.aveti.CoinTracker.model.repository.FiatCurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    private final CoinRepository coinRepository;
    private final FiatCurrencyRepository fiatRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private List<CurrencyDTO> suggestionsList = new ArrayList<>();
    private String lastKeyword;

    CurrencyService(final CoinRepository coinRepository, final FiatCurrencyRepository fiatRepository) {
        this.coinRepository = coinRepository;
        this.fiatRepository = fiatRepository;
    }

    public List<CurrencyDTO> currencyAutocomplete(String keyword) {
        List<CurrencyDTO> currencyDTOList = new ArrayList<>();

        if (lastKeyword!=null) {
            if (keyword.startsWith(lastKeyword.substring(0,2)) && suggestionsList!=null) {
                for (CurrencyDTO currency : suggestionsList) {
                    if (currency.getLabel().toLowerCase().contains(keyword.toLowerCase()) || currency.getValue().contains(keyword)) {
                        currencyDTOList.add(currency);
                    }
                }
                lastKeyword=keyword;
                return currencyDTOList;
            } else if (suggestionsList!=null){
                suggestionsList.clear();
            }
        }
        for (FiatCurrency currency : fiatRepository.findCurrenciesByKeyword(keyword.toLowerCase())) {
            getResultChangedToDTO(currencyDTOList,currency);
        }
        for (Coin coin : coinRepository.findCoinsByKeyword(keyword.toLowerCase())) {
            getResultChangedToDTO(currencyDTOList, coin);
        }
        suggestionsList.addAll(currencyDTOList);
        lastKeyword=keyword;
        return currencyDTOList;
    }

    private void getResultChangedToDTO(final List<CurrencyDTO> currencyDTOList, final Currency coin) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setLabel(coin.getSymbol().toUpperCase() + " " + coin.getName() + " (" + coin.getClass().getSimpleName() + ")");
        dto.setValue(coin.getId());
        currencyDTOList.add(dto);
    }
}