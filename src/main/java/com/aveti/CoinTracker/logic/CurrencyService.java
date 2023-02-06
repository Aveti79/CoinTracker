package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private String[][] autocompleteList;
    private String lastKeyword;

    CurrencyService(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public String[][] currencyAutocomplete(String keyword) {
        final String finalKeyword = keyword.toLowerCase();
        String[][] resultList;

        if (lastKeyword!=null) {
            if (keyword.startsWith(lastKeyword.substring(0,1)) && autocompleteList!=null) {
                resultList = Arrays.stream(autocompleteList)
                        .filter((currency) ->
                            currency[0].contains(finalKeyword) ||
                            currency[1].toLowerCase().contains(finalKeyword) ||
                            currency[2].contains(finalKeyword))
                        .limit(20)
                        .toArray(String[][]::new);
                lastKeyword=finalKeyword;
                return resultList;
            } else if (autocompleteList!=null){
                autocompleteList = null;
            }
        }
        autocompleteList = currencyRepository.findCurrencyByKeyword(finalKeyword.substring(0,1));
        lastKeyword=keyword;
        return Arrays.stream(autocompleteList).limit(20).toArray(String[][]::new);
    }
}
