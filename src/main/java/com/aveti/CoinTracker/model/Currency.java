package com.aveti.CoinTracker.model;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public abstract class Currency {

    @Id
    private String id;
    private String symbol;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getSymbol().toUpperCase() + " " + getName();
    }
}
