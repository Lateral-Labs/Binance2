package com.example.binance.repository;

import com.example.binance.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade,Long> {
 Trade findByTradeIdInBinance(Long id);

}
