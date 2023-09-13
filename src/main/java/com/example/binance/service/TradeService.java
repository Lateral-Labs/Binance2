package com.example.binance.service;

import com.example.binance.model.Symbol;
import com.example.binance.model.Trade;
import com.example.binance.repository.SymbolRepository;
import com.example.binance.repository.TradeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeService {
    private TradeRepository tradeRepository;
    private SymbolRepository symbolRepository;

    private static final String TRADES_URL = "https://fapi.binance.com/fapi/v1/trades?symbol={symbol}&limit={limit}";

    @Autowired
    public TradeService(TradeRepository tradeRepository, SymbolRepository symbolRepository) {
        this.tradeRepository = tradeRepository;
        this.symbolRepository = symbolRepository;
    }

    public List<Trade> getAllRecentTrades(String symbolName, String limit) throws IOException, InterruptedException {
        URI url = new UriTemplate(TRADES_URL).expand(symbolName, limit);

        String responseBody = getResponseBodyOfTradesFromBinance(url);

        ObjectMapper objectMapper = new ObjectMapper();

        Symbol foundSymbol = symbolRepository.findBySymbolName(symbolName);

        List<Trade> recentTradeList = new ArrayList<>();

        for (JsonNode tradeJson : objectMapper.readTree(responseBody)) {
            if (!isANewTradeOfSymbol(tradeJson, foundSymbol)) {
                recentTradeList.add(convertTradeJsonToTradeObjectAndSave(tradeJson, foundSymbol));
            }
        }
        return recentTradeList;
    }

    private static String getResponseBodyOfTradesFromBinance(URI url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body().toString();

        return responseBody;
    }

    private boolean isANewTradeOfSymbol(JsonNode tradeJson, Symbol symbol) {
        Trade foundTrade = tradeRepository.findByTradeIdInBinance(tradeJson.path("id").asLong());
        if (foundTrade.equals(null)) {
            return false;
        }
        return true;
    }

//    private static List<Long> getIdsRegisteredTrades(Symbol symbol) {
//        List<Long> idsRegisteredTrades = symbol.getTrades().stream()
//                .map(trade -> trade.getTradeIdInBinance())
//                .collect(Collectors.toList());
//        return idsRegisteredTrades;
//    }

    private Trade convertTradeJsonToTradeObjectAndSave(JsonNode tradeJson, Symbol symbol) {

        Trade newTrade = new Trade();

        newTrade.setTradeIdInBinance(tradeJson.path("id").asLong());
        newTrade.setPrice(tradeJson.path("price").asDouble());
        newTrade.setQty(tradeJson.path("qty").asDouble());
        newTrade.setQuoteQty(tradeJson.path("quoteQty").asDouble());

        Long sec = tradeJson.path("time").asLong();
        newTrade.setTime(new Date(sec));

        newTrade.setBuyerMarker(tradeJson.path("isBuyerMarker").asBoolean());

        newTrade.setSymbol(symbol);

        return tradeRepository.save(newTrade);
    }
}
