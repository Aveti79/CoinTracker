package com.aveti.CoinTracker.logic;

import com.aveti.CoinTracker.model.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GainTableServiceTest {

    @Test
    @DisplayName("Should return distinct currencies from buyCurrency column in Transactions")
    void getDistinctCurrencies_from_buyCurrency_in_Transactions() {
        //given
        var mockTaskRepositroy = getMockTransactionRepository();
        //when
        var toTest = new TransactionService(mockTaskRepositroy);
        //then

    }

    private TransactionRepository getMockTransactionRepository() {
        var mockRepo = mock(TransactionRepository.class);
        var mockCurrencies = new ArrayList<String>(List.of("BTC", "PLN", "ADA"));
        when(mockRepo.findDistinctBuyCurrencies()).thenReturn(mockCurrencies);
        return mockRepo;
    }
}
