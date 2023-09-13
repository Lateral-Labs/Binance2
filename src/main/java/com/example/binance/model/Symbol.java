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

}
