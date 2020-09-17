package com.pharma.app.product.service;

import com.pharma.app.product.playload.ProductRequest;
import com.pharma.app.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product save(Product product, ProductRequest productRequest);
	Product findProductById(int productId);
    Product findProductByName(String name);
    Product findProductByCode(String code);
    Page<Product> findAllProducts(String key, Pageable p);
}
