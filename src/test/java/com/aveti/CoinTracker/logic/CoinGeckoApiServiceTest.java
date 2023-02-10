package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.repository.CoinDetailsRepository;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinGeckoApiServiceTest {

    @Mock
    CurrencyRepository currencyRepository;
    @Mock
    CoinDetailsRepository coinDetailsRepository;
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    CoinGeckoApiService apiService;

    @BeforeEach
    void setUp() {
        apiService = new CoinGeckoApiService(currencyRepository, coinDetailsRepository, restTemplate);
    }

    @Test
    void ShouldUpdateCoinsList() {
        //given
        CurrencyList mockCurrencyList = mock(CurrencyList.class);
        //when
        apiService.updateCoinsList(mockCurrencyList);
        //then
        verify(currencyRepository).saveAll(mockCurrencyList.getCoinList());
    }

    @Test
    void ShouldReturnCoinsListFromDatabase() {
        //when
        var result = apiService.getCoinsListFromDatabase();
        //then
        verify(currencyRepository).findAll();
        assertThat(result).isInstanceOf(List.class);
    }

    @Test
    void getCoinPriceInfo_GivenCurrencyNotExists_ShouldThrowIllegalArgumentException() {
        //given
        when(currencyRepository.existsById(anyString())).thenReturn(false);
        //when
        //then
        assertThatThrownBy(() -> apiService.getCoinPriceInfo(anyString()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency not found in database");
    }

    @Test
    void getCoinPriceInfo_GivenCurrencyExists_RestTemplateReturnsNull_ShouldThrowNullPointerException() throws Exception {
        //given
        when(currencyRepository.existsById(anyString())).thenReturn(true);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        //when
        var result = catchThrowable(() -> apiService.getCoinPriceInfo(anyString()));
        //then
        assertThat(result).isInstanceOf(NullPointerException.class).hasMessageContaining("No Price found for specified currency");
    }

    @Test
    void getCoinPriceInfo_EverythingIsOk() throws Exception {
        //given
        CoinPrice properObject = new CoinPrice("bitcoin", 12345.67, -2.48);
        when(currencyRepository.existsById(anyString())).thenReturn(true);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(properObject);
        //when
        var expected = apiService.getCoinPriceInfo(anyString());
        //then
        assertThat(expected).isEqualTo(properObject);
    }
}