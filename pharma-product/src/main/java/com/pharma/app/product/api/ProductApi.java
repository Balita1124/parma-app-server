package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.model.ProductRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Timed("pharma.product")
@RequiredArgsConstructor
@Slf4j
public class ProductApi {
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product processCreationForm(@RequestBody ProductRequest productRequest) {
        return save(new Product(), productRequest);
    }

    @PutMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@RequestBody ProductRequest productRequest) {
        int productId = productRequest.getId();
        Product product = findProductById(productId);
        save(product, productRequest);
    }

    @GetMapping("/products/{productId}")
    public ProductDetails findProduct(@PathVariable("productId") int productId) {
        return new ProductDetails(findProductById(productId));
    }


    private Product save(final Product product, final ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCode(productRequest.getCode());
        product.setValidity_date(productRequest.getValidity_date());
        product.setPrice(productRequest.getPrice());
        log.info("Saving product {}", product);
        return productRepository.save(product);
    }

    private Product findProductById(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Product " + productId + " not found");
        }
        return product.get();
    }
}
