package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.CoinPrice;
import com.aveti.CoinTracker.model.CurrencyList;
import com.aveti.CoinTracker.model.repository.CoinDetailsRepository;
import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
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

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        apiService = new CoinGeckoApiService(currencyRepository, coinDetailsRepository, restTemplate);
        mapper = new ObjectMapper();

    }

    @Test
    void should_UpdateCoinsList() {
        //given
        CurrencyList mockCurrencyList = mock(CurrencyList.class);
        //when
        apiService.updateCoinsList(mockCurrencyList);
        //then
        verify(currencyRepository).saveAll(mockCurrencyList.getCoinList());
    }

    @Test
    void shouldReturn_CoinsListFromDatabase() {
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

    @Test
    void getCoinHistoricalPriceInSpecifiedCurrencyTest() {
        //given
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(prepareValidJsonNode());
        double expected = 2.29085435498536;
        //when
        var result = apiService.getCoinHistoricalPriceInSpecifiedCurrency("cardano", "2021-06-21T17:36:38", "usd");
        //then
        assertThat(result).isEqualTo(expected);
    }

    private JsonNode prepareValidJsonNode() {
        try {
            InputStream jsonText = ClassLoader.getSystemClassLoader().getResourceAsStream("coin_historical_price_example_response.json");
            return mapper.readTree(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}