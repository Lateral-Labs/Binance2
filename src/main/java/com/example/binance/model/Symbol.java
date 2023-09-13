package com.example.binance.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "symbol")
@Getter
@Setter
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "symbol_id")
    private Long id;

    @Column (name = "symbol_name")
    private String symbolName;

    @Column (name = "bid_price")
    private Double bidPrice;

    @Column (name = "bid_quantity")
    private Double bidQty;

    @Column (name = "ask_price")
    private Double askPrice;

    @Column (name = "ask_quantity")
    private Double askQty;

    @Column (name = "time")
    private Date time;

    @OneToMany(mappedBy = "symbol", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "symbol-trade")
    private List<Trade> trades;

    public Symbol() {
    }

    public Long getId() {
        return id;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Double getBidQty() {
        return bidQty;
    }

    public void setBidQty(Double bidQty) {
        this.bidQty = bidQty;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getAskQty() {
        return askQty;
    }

    public void setAskQty(Double askQty) {
        this.askQty = askQty;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
