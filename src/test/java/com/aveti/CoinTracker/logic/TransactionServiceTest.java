package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    CurrencyRepository currencyRepository;
    @Mock
    CoinGeckoApiService apiService;
    @Mock
    CoinDetailsService detailsService;
    @InjectMocks
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(transactionRepository, currencyRepository,apiService,detailsService);
    }

    @Test
    void getCurrencyAmountConvertedToUSD_GivenCurrencyIsValidCoin_ReturnsAmountConvertedToUSD() {
        //given
        Currency currency = new Currency("cardano", "ADA", "Cardano", "COIN");
        double amount = 20;
        LocalDateTime transactionDate = LocalDateTime.of(2021,9,20,13,14,30);
        given(apiService.getCoinHistoricalPrice(anyString(), anyString())).willReturn(2.30);
        double expected = amount * 2.30;
        //when
        double result = transactionService.getCurrencyAmountConvertedToUSD(currency, amount, transactionDate);
        //then
        verify(apiService, atLeastOnce()).getCoinHistoricalPrice(anyString(),anyString());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getCurrencyAmountConvertedToUSD_GivenArgumentsIsNull_ReturnsAmountConvertedToUSD() {
        //given
        double expected = 0;
        //when
        double result = transactionService.getCurrencyAmountConvertedToUSD(null, 0, null);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getCurrencyAmountConvertedToUSD_GivenCurrencyIsValidFiat_ReturnsAmountConvertedToUSD() {
        //given
        Currency currency = new Currency("pln", "pln", "Polish Zloty", "FIAT");
        double amount = 25;
        LocalDateTime transactionDate = LocalDateTime.of(2022,9,20,15,45,25);
        given(apiService.convertFiatToFiat(anyString(), any(LocalDateTime.class))).willReturn(0.25);
        double expected = amount * 0.25;
        //when
        double result = transactionService.getCurrencyAmountConvertedToUSD(currency, amount, transactionDate);
        //then
        verify(apiService, atLeastOnce()).convertFiatToFiat(anyString(),any(LocalDateTime.class));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getCurrencyAmountConvertedToUSD_GivenCurrencyIsUSD_ReturnsTheSameAmountAsGiven() {
        //given
        Currency currency = new Currency("usd", "usd", "US Dollar", "FIAT");
        double amount = 25;
        LocalDateTime transactionDate = LocalDateTime.of(2022,9,20,15,45,25);
        //when
        double result = transactionService.getCurrencyAmountConvertedToUSD(currency, amount, transactionDate);
        //then
        assertThat(result).isEqualTo(amount);
    }

    @Test
    void getCurrencyFromString_GivenStringIsValid() {
        //given
        Currency currency = new Currency("cardano", "ADA", "Cardano", "COIN");
        given(currencyRepository.findById(anyString())).willReturn(Optional.of(currency));
        //when
        var result = transactionService.getCurrencyFromString("cardano");
        //then
        assertThat(result.getId()).isEqualTo(currency.getId());
    }

    @Test
    void getCurrencyFromString_GivenStringIsNotValid() {
        //given
        given(currencyRepository.findById(anyString())).willReturn(Optional.empty());
        //when
        var result = catchThrowable(() -> transactionService.getCurrencyFromString("foobar"));
        //then
        assertThat(result).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Currency not found");
    }

    @Test
    void getCurrencyFromString_GivenStringIsEmpty() {
        //given
        //when
        var result = catchThrowable(() -> transactionService.getCurrencyFromString(""));
        //then
        assertThat(result).isNull();
    }
}