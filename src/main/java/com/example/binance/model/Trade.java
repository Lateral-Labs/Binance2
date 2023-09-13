package com.example.binance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "trade_id_in_binance", unique = true)
    private Long tradeIdInBinance;

    @Column (name = "price")
    private Double price;

    @Column (name="quantity")
    private Double qty;

    @Column (name = "quote_qty")
    private Double quoteQty;

    @Column (name = "time")
    private Date time;

    @Column
    private boolean isBuyerMarker;

    @ManyToOne
    @JsonBackReference(value = "symbol-trade")
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    public Trade() {
    }

    public Long getId() {
        return id;
    }

    public Long getTradeIdInBinance() {
        return tradeIdInBinance;
    }

    public void setTradeIdInBinance(Long tradeIdInBinance) {
        this.tradeIdInBinance = tradeIdInBinance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getQuoteQty() {
        return quoteQty;
    }

    public void setQuoteQty(Double quoteQty) {
        this.quoteQty = quoteQty;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isBuyerMarker() {
        return isBuyerMarker;
    }

    public void setBuyerMarker(boolean buyerMarker) {
        isBuyerMarker = buyerMarker;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
