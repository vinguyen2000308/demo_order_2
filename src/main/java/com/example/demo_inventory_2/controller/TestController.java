package com.example.demo_inventory_2.controller;

import com.example.demo_inventory_2.domain.Product;
import com.example.demo_inventory_2.domain.ProductMeta;
import com.example.demo_inventory_2.domain.StockTotal;
import com.example.demo_inventory_2.repo.ProductMetaRepo;
import com.example.demo_inventory_2.repo.ProductRepo;
import com.example.demo_inventory_2.repo.StockTotalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/inventory")
public class TestController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductMetaRepo productMetaRepo;

    @Autowired
    private StockTotalRepo stockTotalRepo;

    @Transactional
    @PostMapping("/test")
    public void testSave()
    {
        Product product = Product.builder()
                .title("Macbook M1")
                .summary("This is macbook m1")
                .basePrice(37_000_000.0)
                .type("computer")
                .createdUser("Nguyen Vi")
                .createdDate(LocalDateTime.now())
                .status(1l)
                .build();
        Product macbookM1 = productRepo.save(product);


        ProductMeta ram = ProductMeta.builder()
                .productId(macbookM1.getProductId())
                .attKey("ram")
                .attValue("64G")
                .status(1l)
                .createdUser("Nguyen Vi")
                .createdDate(LocalDateTime.now())
                .build();

        ProductMeta cpu = ProductMeta.builder()
                .productId(macbookM1.getProductId())
                .attKey("cpu")
                .attValue("M1")
                .status(1l)
                .createdUser("Nguyen Vi")
                .createdDate(LocalDateTime.now())
                .build();

        productMetaRepo.saveAll(List.of(ram, cpu));


        StockTotal stockTotal = StockTotal.builder()
                .availableQuantity(10l)
                .productId(macbookM1.getProductId())
                .status(1L)
                .createdUser("Nguyen Vi")
                .updatedUser("Nguyen Vi")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        stockTotalRepo.save(stockTotal);
    }
}
