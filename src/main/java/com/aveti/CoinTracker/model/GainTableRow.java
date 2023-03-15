package com.aveti.CoinTracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class GainTableRow {

    private Currency currency;
    private String logo;
    private double amount;
    private double averageBuyPrice;
    private double summaryPrice;
    private double actualPrice;
    private double priceChange24h;
    private double actualValue;
    private double percentageValueChange;
    private double unrealizedGains;
    private double realizedGains;
}
