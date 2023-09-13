package com.example.binance.repository;

import com.example.binance.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {

    Symbol findBySymbolName(String symbolName);

}
