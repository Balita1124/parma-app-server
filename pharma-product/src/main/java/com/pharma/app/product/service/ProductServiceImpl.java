package com.pharma.app.product.service;

import com.pharma.app.product.api.ProductRequest;
import com.pharma.app.product.model.Product;
import com.pharma.app.product.model.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product save(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCode(productRequest.getCode());
        product.setValidity_date(productRequest.getValidity_date());
        product.setPrice(productRequest.getPrice());
        log.info("Saving product {}", product);
        return productRepository.save(product);
    }
	@Override
    public Product findProductById(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            //throw new ResourceNotFoundException("Product " + productId + " not found");
			return null;
        }
        return product.get();
    }
	@Override
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
}
