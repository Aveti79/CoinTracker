package com.aveti.CoinTracker;

import com.aveti.CoinTracker.model.FiatCurrency;
import com.aveti.CoinTracker.model.repository.FiatCurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CurrencyContext implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyContext.class);

    private final FiatCurrencyRepository fiatRepository;

    CurrencyContext(final FiatCurrencyRepository fiatRepository) {
        this.fiatRepository = fiatRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!fiatRepository.existsById("usd")) {
            logger.info("Fiat currency values not found in database, adding it");
            fiatRepository.saveAll(FiatCurrency.getAllSupportedFiat());
        }
    }

}
