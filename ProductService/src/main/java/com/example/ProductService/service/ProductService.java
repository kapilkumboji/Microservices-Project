package com.example.ProductService.service;

import com.example.ProductService.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product, String user);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}