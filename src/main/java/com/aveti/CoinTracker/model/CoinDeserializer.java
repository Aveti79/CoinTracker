package com.aveti.CoinTracker.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CoinDeserializer extends StdDeserializer<Coin> {

    public CoinDeserializer() {
        this(null);
    }

    public CoinDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Coin deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.fieldNames().next();
        double price = node.get(name).get("usd").asDouble();
        double priceChange24h = node.get(name).get("usd_24h_change").asDouble();

        return new Coin(name, price, priceChange24h);
    }
}