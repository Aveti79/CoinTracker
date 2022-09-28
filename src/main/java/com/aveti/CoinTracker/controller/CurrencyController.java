package com.aveti.CoinTracker.controller;

import com.aveti.CoinTracker.logic.CoinService;
import com.aveti.CoinTracker.model.CurrencyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {

    private CoinService coinService;

    public CurrencyController(final CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/coinNameAutocomplete")
    public List<CurrencyDTO> coinNameAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return coinService.coinAutocomplete(term);
    }
}
