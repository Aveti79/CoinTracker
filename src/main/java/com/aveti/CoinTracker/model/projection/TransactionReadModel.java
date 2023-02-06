package com.aveti.CoinTracker.model.projection;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.util.HashMap;

public class TransactionReadModel {

    private TransactionTypeEnum type;
    private LocalDateTime transactionTime;
    private double buyAmount;
    private Currency buyCurrency;
    private String buyCurrencyLogo;
    private double sellAmount;
    private Currency sellCurrency;
    private String sellCurrencyLogo;
    private double feeAmount;
    private Currency feeCurrency;
    private String feeCurrencyLogo;
    private String comment;

    public TransactionReadModel(Transaction source, HashMap<String, String> logos) {
        type = source.getType();
        transactionTime = source.getTransactionTime();
        buyAmount = source.getBuyAmount();
        buyCurrency = source.getBuyCurrency();
        buyCurrencyLogo = logos.get("buyCurrencyLogo");
        sellAmount = source.getSellAmount();
        sellCurrency = source.getSellCurrency();
        sellCurrencyLogo = logos.get("sellCurrencyLogo");
        feeAmount = source.getFeeAmount();
        feeCurrency = source.getFeeCurrency();
        feeCurrencyLogo = logos.get("feeCurrencyLogo");
        comment = source.getComment();
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    void setType(final TransactionTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    void setTransactionTime(final LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getBuyAmount() {
        return buyAmount;
    }

    void setBuyAmount(final double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Currency getBuyCurrency() {
        return buyCurrency;
    }

    void setBuyCurrency(final Currency buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getBuyCurrencyLogo() {
        return buyCurrencyLogo;
    }

    void setBuyCurrencyLogo(final String buyCurrencyLogo) {
        this.buyCurrencyLogo = buyCurrencyLogo;
    }

    public double getSellAmount() {
        return sellAmount;
    }

    void setSellAmount(final double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Currency getSellCurrency() {
        return sellCurrency;
    }

    void setSellCurrency(final Currency sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public String getSellCurrencyLogo() {
        return sellCurrencyLogo;
    }

    void setSellCurrencyLogo(final String sellCurrencyLogo) {
        this.sellCurrencyLogo = sellCurrencyLogo;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    void setFeeAmount(final double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Currency getFeeCurrency() {
        return feeCurrency;
    }

    void setFeeCurrency(final Currency feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public String getFeeCurrencyLogo() {
        return feeCurrencyLogo;
    }

    void setFeeCurrencyLogo(final String feeCurrencyLogo) {
        this.feeCurrencyLogo = feeCurrencyLogo;
    }

    public String getComment() {
        return comment;
    }

    void setComment(final String comment) {
        this.comment = comment;
    }
}
