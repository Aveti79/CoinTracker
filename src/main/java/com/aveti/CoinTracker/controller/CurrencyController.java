package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CurrencyService;
import com.aveti.CoinTracker.model.CurrencyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/coinNameAutocomplete")
    public List<CurrencyDTO> coinNameAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return currencyService.currencyAutocomplete(term);
    }
}
