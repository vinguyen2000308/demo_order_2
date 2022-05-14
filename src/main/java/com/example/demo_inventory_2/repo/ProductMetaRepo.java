package com.example.demo_inventory_2.repo;

import com.example.demo_inventory_2.domain.ProductMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMetaRepo extends JpaRepository<ProductMeta, Long> {
}
