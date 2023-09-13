package com.example.binance.DTO;

public class SymbolResponseDTO {
    private String symbolName;

    public SymbolResponseDTO(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }
}
