package com.example.demo_inventory_2.repo;

import com.example.demo_inventory_2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findProductByTitle(String tile);

    Product findProductByProductId(Long productId);
}
