package com.aveti.CoinTracker.model;

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

    public GainTableRow() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(final double averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }

    public double getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(final double summaryPrice) {
        this.summaryPrice = summaryPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(final double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getPriceChange24h() {
        return priceChange24h;
    }

    public void setPriceChange24h(final double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(final double actualValue) {
        this.actualValue = actualValue;
    }

    public double getPercentageValueChange() {
        return percentageValueChange;
    }

    public void setPercentageValueChange(final double percentageValueChange) {
        this.percentageValueChange = percentageValueChange;
    }

    public double getUnrealizedGains() {
        return unrealizedGains;
    }

    public void setUnrealizedGains(final double unrealizedGains) {
        this.unrealizedGains = unrealizedGains;
    }

    public double getRealizedGains() {
        return realizedGains;
    }

    public void setRealizedGains(final double realizedGains) {
        this.realizedGains = realizedGains;
    }
}
