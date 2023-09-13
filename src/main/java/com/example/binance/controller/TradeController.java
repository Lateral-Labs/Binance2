package com.example.binance.controller;

import com.example.binance.model.Trade;
import com.example.binance.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("trade")
public class TradeController {

    private TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/recentTrades/{symbol}/{limit}")
    public ResponseEntity<List<Trade>> getAllRecentTrades(@PathVariable("symbol") String symbol, @PathVariable("limit") String limit) throws IOException, InterruptedException {
        return status(HttpStatus.OK).body(tradeService.getAllRecentTrades(symbol, limit));
    }

}
