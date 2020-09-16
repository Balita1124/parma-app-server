package com.pharma.app.product.service;

import com.pharma.app.product.api.ProductRequest;
import com.pharma.app.product.model.Product;
import java.util.List;

public interface ProductService {
    Product save(Product product, ProductRequest productRequest);
	Product findProductById(int productId);
	List<Product> getAllProducts();
	
}
