package com.pharma.app.product.service;

import com.pharma.app.product.api.ProductRequest;
import com.pharma.app.product.model.Product;

public interface ProductService {
    Product save(Product product, ProductRequest productRequest);
}
