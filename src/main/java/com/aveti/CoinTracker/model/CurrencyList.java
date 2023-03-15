package com.aveti.CoinTracker.model;

import com.aveti.CoinTracker.util.CurrencyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CurrencyDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CurrencyList extends Currency {

    private List<Currency> coinList;
}
