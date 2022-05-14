package com.example.demo_inventory_2.repo;

import com.example.demo_inventory_2.domain.StockTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTotalRepo extends JpaRepository<StockTotal, Long> {

    StockTotal findStockTotalByProductIdAndStatus(Long productId, Long status);
    StockTotal findStockTotalByStockTotalIdAndStatus(Long stockTotalId,Long status);

}
