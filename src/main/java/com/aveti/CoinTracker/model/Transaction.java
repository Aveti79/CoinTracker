package com.aveti.CoinTracker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type; //todo: maybe add enum class for type
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

    void setId(final int id) {
        this.id = id;
    }

    String getType() {
        return type;
    }

    void setType(final String type) {
        this.type = type;
    }

    LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    void setTransactionTime(final LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    double getBuyAmount() {
        return buyAmount;
    }

    void setBuyAmount(final double buyAmount) {
        this.buyAmount = buyAmount;
    }

    String getBuyCurrency() {
        return buyCurrency;
    }

    void setBuyCurrency(final String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    double getSellAmount() {
        return sellAmount;
    }

    void setSellAmount(final double sellAmount) {
        this.sellAmount = sellAmount;
    }

    String getSellCurrency() {
        return sellCurrency;
    }

    void setSellCurrency(final String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    double getFeeAmount() {
        return feeAmount;
    }

    void setFeeAmount(final double feeAmount) {
        this.feeAmount = feeAmount;
    }

    String getFeeCurrency() {
        return feeCurrency;
    }

    void setFeeCurrency(final String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    String getComment() {
        return comment;
    }

    void setComment(final String comment) {
        this.comment = comment;
    }
}
