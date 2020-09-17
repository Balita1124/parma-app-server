package com.pharma.app.product.service;

import com.pharma.app.product.playload.ProductRequest;
import com.pharma.app.product.model.Product;
import com.pharma.app.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        product.setValidityDate(productRequest.getValidityDate());
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
    public Page<Product> findAllProducts(String key, Pageable page) {
        if (key == null) {
            return productRepository.findAll(org.springframework.data.domain.Example.of(new Product()), page);
        }
        return productRepository.findByNameLikeOrCodeLike(key, page);
    }


    @Override
    public Product findProductByName(String name) {
        return productRepository.findFirstByName(name);
    }

    @Override
    public Product findProductByCode(String code) {
        Product product = productRepository.findFirstByCode(code);
        return product;
    }
}
