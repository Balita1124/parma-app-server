package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.model.ProductRepository;
import com.pharma.app.product.service.ProductService;
import com.pharma.app.product.util.ErrorsDetails;
import com.pharma.app.product.util.ResourceNotFoundException;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Timed("pharma.product")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(exposedHeaders = "errors, content-type")
public class ProductApi {

    private final ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> processCreationForm(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        ErrorsDetails errorsDetails = new ErrorsDetails();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (productRequest == null)) {
            errorsDetails.addAllErrors(bindingResult);
            headers.add("errors", errorsDetails.toJSON());
            return new ResponseEntity<Product>(headers, HttpStatus.BAD_REQUEST);
        }
        Product product = productService.save(new Product(), productRequest);
        headers.setLocation(ucBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Product>(product, headers, HttpStatus.CREATED);
    }

    @PutMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Product> processUpdateForm(@RequestBody @Valid ProductRequest productRequest,BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        ErrorsDetails errorsDetails = new ErrorsDetails();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (productRequest == null)) {
            errorsDetails.addAllErrors(bindingResult);
            headers.add("errors", errorsDetails.toJSON());
            return new ResponseEntity<Product>(headers, HttpStatus.BAD_REQUEST);
        }
        int productId = productRequest.getId();
        Product product = findProductById(productId);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        Product updatedProduct = productService.save(product, productRequest);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/{productId}")
    public ProductDetails findProduct(@PathVariable("productId") int productId) {
        return new ProductDetails(findProductById(productId));
    }

    private Product findProductById(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Product " + productId + " not found");
        }
        return product.get();
    }
}
