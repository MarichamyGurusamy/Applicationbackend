package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDao extends CrudRepository<Product, Integer> {

    public List<Product> findAll(Pageable pageable);
}
