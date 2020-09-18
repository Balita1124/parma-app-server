package com.pharma.app.product.service;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.playload.product.ProductRequest;
import com.pharma.app.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }

    public Page<Product> getPaginatedProducts(Integer page, Integer size, String sort, String direction, String keyword) {
        Sort sortable = ("desc".equals(direction)) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Product> products = null;
        if (keyword != null) {
            products = productRepository.findByNameLikeOrCodeLike(keyword, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products;
    }

    public Product create(ProductRequest productRequest) {
        Product product = this.setProduct(new Product(), productRequest);
        return productRepository.save(product);

    }

    public Product update(Product currentProduct, ProductRequest productRequest) {
        Product product = this.setProduct(currentProduct, productRequest);
        return productRepository.save(currentProduct);

    }

    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product findProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    public Product findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findProductByCodeAndId(String code, Integer id) {
        return productRepository.findByCodeAndId(code, id);
    }

    public Product findProductByNameAndId(String name, Integer id) {
        return productRepository.findByNameAndId(name, id);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public Product setProduct(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCode(productRequest.getCode());
        product.setValidityDate(productRequest.getValidityDate());
        product.setPrice(productRequest.getPrice());
        return product;
    }
}
