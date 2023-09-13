package com.example.binance.service;

import com.example.binance.DTO.SymbolResponseDTO;
import com.example.binance.model.Symbol;
import com.example.binance.repository.SymbolRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SymbolService {
    private SymbolRepository symbolRepository;

    private static final String ALL_SYMBOLS_URL = "https://fapi.binance.com/fapi/v1/ticker/bookTicker";

    @Autowired
    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public List<SymbolResponseDTO> getAllSymbolsName() throws IOException, InterruptedException {

        List<Symbol> symbols = symbolRepository.findAll();

        if (symbols.isEmpty()) {
            symbols = getSymbolsFromBinanceAndSaveSymbolsToDatabase();
        }
        return collectNameSymbols(symbols);
    }

    private List<Symbol> getSymbolsFromBinanceAndSaveSymbolsToDatabase() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ALL_SYMBOLS_URL))
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body().toString();

        ObjectMapper objectMapper = new ObjectMapper();

        List<Symbol>symbolList = new ArrayList<>();

        for (JsonNode symbolJson : objectMapper.readTree(responseBody)) {
            symbolList.add(convertFromJsonToSymbol(symbolJson));
        }
        return symbolList;
    }

    private List<SymbolResponseDTO> collectNameSymbols(List<Symbol> symbols) {
        List<SymbolResponseDTO> nameSymbols = new ArrayList<>();
        for (Symbol symbol : symbols) {
            SymbolResponseDTO symbolResponseDTO = new SymbolResponseDTO(symbol.getSymbolName());
            nameSymbols.add(symbolResponseDTO);
        }
        return nameSymbols;
    }

    public Symbol convertFromJsonToSymbol(JsonNode symbolJson) {
        Symbol newSymbol = new Symbol();
        newSymbol.setSymbolName(symbolJson.path("symbol").asText());
        newSymbol.setBidPrice(symbolJson.path("bidPrice").asDouble());
        newSymbol.setBidQty(symbolJson.path("bidQty").asDouble());
        newSymbol.setAskPrice(symbolJson.path("askPrice").asDouble());
        newSymbol.setAskQty(symbolJson.path("askQty").asDouble());
        Long sec = symbolJson.path("time").asLong();

        Date date2 = new Date(sec);
        newSymbol.setTime(date2);
        return symbolRepository.save(newSymbol);
    }
}
