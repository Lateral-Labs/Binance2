package com.example.binance.controller;

import com.example.binance.DTO.SymbolResponseDTO;
import com.example.binance.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("symbol")
public class SymbolController {

    private SymbolService symbolService;

    @Autowired
    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping("symbolsName")
    public ResponseEntity<List<SymbolResponseDTO>> getAllSymbolsName() throws IOException, InterruptedException {
        return status(HttpStatus.OK).body(symbolService.getAllSymbolsName());
    }
}
