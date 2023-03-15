package com.aveti.CoinTracker.model;

import com.aveti.CoinTracker.util.CoinPriceDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@JsonDeserialize(using = CoinPriceDeserializer.class)
@Getter @Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class CoinPrice {

    private String name;
    private double price;
    private double priceChange24h;
}
