package com.aveti.CoinTracker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type; //todo: maybe add enum class for type
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionTime;
    private double buyAmount;
    private String buyCurrency; //todo: rethink Coin class
    private double sellAmount;
    private String sellCurrency;
    private double feeAmount;
    private String feeCurrency;
    private String comment;

    public Transaction() {
    }

    public Transaction(final String type,
                       final LocalDateTime transactionTime,
                       final double buyAmount,
                       final String buyCurrency,
                       final double sellAmount,
                       final String sellCurrency) {
        this.type = type;
        this.transactionTime = transactionTime;
        this.buyAmount = buyAmount;
        this.buyCurrency = buyCurrency;
        this.sellAmount = sellAmount;
        this.sellCurrency = sellCurrency;
    }

    public Transaction(final String type,
                final LocalDateTime transactionTime,
                final double buyAmount,
                final String buyCurrency,
                final double sellAmount,
                final String sellCurrency,
                final double feeAmount,
                final String feeCurrency,
                final String comment) {
        this.type = type;
        this.transactionTime = transactionTime;
        this.buyAmount = buyAmount;
        this.buyCurrency = buyCurrency;
        this.sellAmount = sellAmount;
        this.sellCurrency = sellCurrency;
        this.feeAmount = feeAmount;
        this.feeCurrency = feeCurrency;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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
}
