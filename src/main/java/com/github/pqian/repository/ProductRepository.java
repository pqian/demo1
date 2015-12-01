package com.github.pqian.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.pqian.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
