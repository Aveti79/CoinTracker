package com.aveti.CoinTracker.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Currency {

    @Id
    private String id;
    private String symbol;
    private String name;
    private String type;

    @Override
    public String toString() {
        return getName();
    }

    public static List<Currency> getAllSupportedFiat() {
        List<Currency> fiatList = new ArrayList<>();
        fiatList.add(new Currency("usd", "usd", "US Dollar", "FIAT"));
        fiatList.add(new Currency("eur", "eur", "Euro", "FIAT"));
        fiatList.add(new Currency("pln", "pln", "Polish Zloty", "FIAT"));
        fiatList.add(new Currency("rub", "rub", "Russian Ruble", "FIAT"));
        fiatList.add(new Currency("krw", "krw", "South Korean Won", "FIAT"));
        fiatList.add(new Currency("cny", "cny", "Chinese Yuan", "FIAT"));
        fiatList.add(new Currency("jpy", "jpy", "Japanese Yen", "FIAT"));
        fiatList.add(new Currency("aud", "aud", "Australian Dollar", "FIAT"));
        fiatList.add(new Currency("chf", "chf", "Swiss Franc", "FIAT"));
        fiatList.add(new Currency("gbp", "gbp", "British Pound Sterling", "FIAT"));
        fiatList.add(new Currency("try", "try", "Turkish Lira", "FIAT"));
        fiatList.add(new Currency("uah", "uah", "Ukrainian Hryvnia", "FIAT"));
        return fiatList;
    }
}
