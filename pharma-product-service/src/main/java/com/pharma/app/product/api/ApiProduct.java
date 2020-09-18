package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.playload.ApiResponse;
import com.pharma.app.product.playload.error.ErrorSection;
import com.pharma.app.product.playload.product.ProductRequest;
import com.pharma.app.product.playload.product.ProductResponse;
import com.pharma.app.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("api")
public class ApiProduct {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/products", name = "Liste des produits")
    public ApiResponse getAllProducts(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<Product> productPage = productService.getPaginatedProducts(page, size, sort, direction, keyword);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPageStats(productPage);
        productResponse.setItems(productPage.getContent());
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Products List",
                productResponse
        );
    }

    @GetMapping(value = "/products/{productId}", name = "Avoir dun produit")
    public ApiResponse getProduct(@PathVariable(value = "productId") Integer productId) {
        Product currentProduct = productService.findProductById(productId);
        if (currentProduct == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le produit avec l'id = " + productId + " est introuvable",
                    null
            );
        }
        return new ApiResponse(
                true,
                HttpStatus.OK,
                currentProduct.getCode() + " - " + currentProduct.getName(),
                currentProduct
        );
    }

    @PostMapping(value = "/products", name = "Creation d'un produit")
    public ApiResponse createProduct(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) {
        Product existProductByCode = productService.findProductByCode(productRequest.getCode());
        Product existProductByName = productService.findProductByName(productRequest.getName());
        if (existProductByCode != null) {
            bindingResult.rejectValue("code", "error.product", "le code existe deja");
        }
        if (existProductByName != null) {
            bindingResult.rejectValue("name", "error.product", "le nom existe dejae");
        }
        if (productRequest.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            bindingResult.rejectValue("price", "error.product", "le prix de vente doit etre positif");
        }
        if (productRequest.getValidityDate().toInstant().isBefore(new Date().toInstant()) || productRequest.getValidityDate().toInstant().equals(new Date().toInstant())) {
            bindingResult.rejectValue("validityDate", "error.product", "La date de validité doit etre superieur au date du jour");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(productRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        Product product = productService.create(productRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Produit créé avec succes",
                product
        );
    }


    @PutMapping(value = "/products/{productId}", name = "Mise à jour d'un produit")
    public ApiResponse updateProduct(@PathVariable(value = "productId") Integer productId, @RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) {
        Product currentProduct = productService.findProductById(productId);
        if (currentProduct == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le produit avec l'id = " + productId + " est introuvable",
                    new ErrorSection(productRequest, null)
            );
        }

        Product existProductByCode = productService.findProductByCodeAndId(productRequest.getCode(), currentProduct.getId());
        Product existProductByName = productService.findProductByNameAndId(productRequest.getName(), currentProduct.getId());
        if (existProductByCode != null) {
            bindingResult.rejectValue("code", "error.product", "le code existe deja");
        }
        if (existProductByName != null) {
            bindingResult.rejectValue("name", "error.product", "le nom existe deja");
        }
        if (productRequest.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            bindingResult.rejectValue("price", "error.product", "le prix de vente doit etre positif");
        }
        if (productRequest.getValidityDate().toInstant().isBefore(new Date().toInstant()) || productRequest.getValidityDate().toInstant().equals(new Date().toInstant())) {
            bindingResult.rejectValue("validityDate", "error.product", "La date de validité doit etre superieur au date du jour");
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(productRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation",
                    es
            );
        }
        if (bindingResult.hasErrors()) {
            ErrorSection es = new ErrorSection(productRequest, bindingResult.getAllErrors());
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la mise à jour",
                    es
            );
        }
        Product product = productService.update(currentProduct, productRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Produit modifié avec succes",
                product
        );
    }

    @DeleteMapping(value = "/products/{productId}", name = "Suppression d'un produit")
    public ApiResponse deleteProduct(@PathVariable(value = "productId") Integer productId) {
        Product currentProduct = productService.findProductById(productId);
        if (currentProduct == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le produit avec l'id = " + productId + " est introuvable",
                    null
            );
        }
        productService.delete(currentProduct);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Produit supprimé avec succes",
                null
        );
    }
}
