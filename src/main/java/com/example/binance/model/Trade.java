package com.example.binance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "trade")
@Getter
@Setter
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

    @Column (name = "is_buyer_marker")
    private boolean isBuyerMarker;

    @ManyToOne
    @JsonBackReference(value = "symbol-trade")
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    public Trade() {
    }

}
