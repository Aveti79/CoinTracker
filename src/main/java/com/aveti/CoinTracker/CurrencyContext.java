package com.aveti.CoinTracker;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CurrencyContext implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyContext.class);

    private final CurrencyRepository currencyRepository;

    CurrencyContext(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!currencyRepository.existsById("usd")) {
            logger.info("Fiat currency values not found in database, adding it");
            currencyRepository.saveAll(Currency.getAllSupportedFiat());
        }
    }

}
