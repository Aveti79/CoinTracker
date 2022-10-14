package com.aveti.CoinTracker.util;

import com.aveti.CoinTracker.model.Coin;
import com.aveti.CoinTracker.model.CoinList;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CoinDeserializer extends StdDeserializer<Coin> {

    public CoinDeserializer() {
        this(null);
    }

    public CoinDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public CoinList deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        List<Coin> coinsList = new ArrayList<>();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> jsonCoinList = node.elements();
        while (jsonCoinList.hasNext()) {
            final JsonNode element = jsonCoinList.next();
            String id = element.get("id").asText();
            String symbol = element.get("symbol").asText();
            String name = element.get("name").asText();
            coinsList.add(new Coin(id, symbol, name));
        }

        return new CoinList(coinsList);
    }
}