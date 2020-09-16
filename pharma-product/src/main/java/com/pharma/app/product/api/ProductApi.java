package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.model.ProductRepository;
import com.pharma.app.product.service.ProductService;
import com.pharma.app.product.util.ErrorsDetails;
import com.pharma.app.product.util.ResourceNotFoundException;
import com.pharma.app.product.playload.ApiResponse;
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
//@Timed("pharma.product")
//@RequiredArgsConstructor
@Slf4j
@CrossOrigin(exposedHeaders = "errors, content-type")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ApiResponse getProducts() {
		List<Product> products = productService.getAllProducts();
        return new ApiResponse(
			true,
			HttpStatus.OK,
			"Products List",
			products
        );
    }

    @PostMapping(value = "/products")
    public ApiResponse processCreationForm(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        ErrorsDetails errorsDetails = new ErrorsDetails();
        if (bindingResult.hasErrors() || (productRequest == null)) {
            errorsDetails.addAllErrors(bindingResult);
             return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Product not created",
                    errorsDetails.toJSON()
            );
        }
        Product product = productService.save(new Product(), productRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Product Created successfully",
                product
        );
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
        Product product = productService.findProductById(productId);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        Product updatedProduct = productService.save(product, productRequest);
        return new ResponseEntity<Product>(updatedProduct, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetails> findProduct(@PathVariable("productId") int productId) {
		Product product = productService.findProductById(productId);
		if(product == null){
			return new ResponseEntity<ProductDetails>(HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<ProductDetails>(new ProductDetails(product), HttpStatus.OK);
    }


}
