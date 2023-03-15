package com.aveti.CoinTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "currency_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoinDetails {

    @Id
    private String id;
    private String symbol;
    private String name;
    @Column(length = 512)
    private String image;
    @JsonProperty("current_price")
    private Double currentPrice;
    @JsonProperty("market_cap")
    private Long marketCap;
    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;
    @JsonProperty("fully_diluted_valuation")
    private Long fullyDilutedValuation;
    @JsonProperty("total_volume")
    private Long totalVolume;
    @JsonProperty("high_24h")
    private Double high24h;
    @JsonProperty("low_24h")
    private Double low24h;
    @JsonProperty("price_change_24h")
    private Double priceChange24h;
    @JsonProperty("price_change_percentage_24h")
    @ColumnDefault("0")
    private Double priceChangePercentage24h;
    @JsonProperty("market_cap_change_24h")
    private Long marketCapChange24h;
    @JsonProperty("market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h;
    @JsonProperty("circulating_supply")
    private Double circulatingSupply;
    @JsonProperty("total_supply")
    private Double totalSupply;
    @JsonProperty("max_supply")
    private Double maxSupply;
    private Double ath;
    @JsonProperty("ath_change_percentage")
    private Double athChangePercentage;
    @JsonProperty("ath_date")
    private String athDate;
    private Double atl;
    @JsonProperty("atl_change_percentage")
    private Double atlChangePercentage;
    @JsonProperty("atl_date")
    private String atlDate;
    @JsonProperty("last_updated")
    private String lastUpdated;
    @JsonProperty("price_change_percentage_1h_in_currency")
    @ColumnDefault("0")
    public Double priceChangePercentage1h;
    @JsonProperty("price_change_percentage_7d_in_currency")
    @ColumnDefault("0")
    public Double priceChangePercentage7d;
}