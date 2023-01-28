package com.aveti.CoinTracker.model.projection;

import com.aveti.CoinTracker.model.Currency;
import com.aveti.CoinTracker.model.Transaction;
import com.aveti.CoinTracker.model.validation.IsValidCurrency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TransactionWriteModel {

    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionTime;
    private double buyAmount;
    @NotBlank(message = "Currency field can't be empty!")
    @IsValidCurrency()
    private String buyCurrency;
    private double sellAmount;
    @NotBlank(message = "Currency field can't be empty!")
    @IsValidCurrency()
    private String sellCurrency;
    private double feeAmount;
    @IsValidCurrency()
    private String feeCurrency;
    private String comment;
    private double sellValueInUsd;
    private double buyValueInUsd;

    public TransactionWriteModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(final LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(final double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(final String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(final double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(final String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(final double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(final String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public double getSellValueInUsd() {
        return sellValueInUsd;
    }

    public void setSellValueInUsd(final double sellValueInUsd) {
        this.sellValueInUsd = sellValueInUsd;
    }

    public double getBuyValueInUsd() {
        return buyValueInUsd;
    }

    public void setBuyValueInUsd(final double buyValueInUsd) {
        this.buyValueInUsd = buyValueInUsd;
    }

    public Transaction toTransaction(Currency buyCurrency, Currency sellCurrency, Currency feeCurrency) {
        Transaction result = new Transaction();
        result.setType(type);
        result.setTransactionTime(transactionTime);
        result.setBuyAmount(buyAmount);
        result.setBuyCurrency(buyCurrency);
        result.setSellAmount(sellAmount);
        result.setSellCurrency(sellCurrency);
        result.setFeeAmount(feeAmount);
        result.setFeeCurrency(feeCurrency);
        result.setComment(comment);
        result.setSellValueInUsd(sellValueInUsd);
        result.setBuyValueInUsd(buyValueInUsd);
        return result;
    }

}
