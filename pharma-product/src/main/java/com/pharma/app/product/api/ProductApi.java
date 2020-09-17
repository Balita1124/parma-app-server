package com.pharma.app.product.api;

import com.pharma.app.product.model.Product;
import com.pharma.app.product.playload.*;
import com.pharma.app.product.service.ProductService;
import com.pharma.app.product.util.ErrorsDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Slf4j
@CrossOrigin(exposedHeaders = "errors, content-type")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Liste des produits", response = ApiResponse.class)
    @GetMapping("/products")
    public ApiResponse getProductsByPage(
            @ApiParam(value = "") @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @ApiParam(value = "between 1 to 10") @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String type,
            @RequestParam(value = "key", required = false) String key
    ) {
        Sort sortable = ("desc".equals(type)) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        ProductResponse productResponse = new ProductResponse();
        Page<Product> products = productService.findAllProducts(key ,pageable);
        productResponse.setPageStats(products);
        productResponse.setItems(products.getContent());
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Liste des produits",
                productResponse
        );
    }

    @ApiOperation(value = "Ajouter un nouveau produit", response = ApiResponse.class)
    @PostMapping(value = "/products")
    public ApiResponse processCreationForm(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        ErrorsDetails errorsDetails = new ErrorsDetails();
        Product productByCode = productService.findProductByCode(productRequest.getCode());
        Product productByName = productService.findProductByName(productRequest.getName());
        if (productByCode != null) {
            bindingResult.rejectValue("code", "error.product", "le code doit etre unique");
        }
        if (productByName != null) {
            bindingResult.rejectValue("name", "error.product", "le nom doit etre unique");
        }
        if(productRequest.getValidityDate().toInstant().isBefore(new Date().toInstant()) || productRequest.getValidityDate().toInstant().equals(new Date().toInstant())){
            bindingResult.rejectValue("validityDate", "error.product", "La date de validité doit etre superieur au date du jour");
        }
        if (bindingResult.hasErrors() || (productRequest == null)) {
            errorsDetails.addAllErrors(bindingResult);
            System.out.println(errorsDetails);
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la creation du produit",
                    errorsDetails
            );
        }
        Product product = productService.save(new Product(), productRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Product crée avex succes",
                product
        );
    }

    @ApiOperation(value = "Modifier un produit", response = ApiResponse.class)
    @PutMapping("/products/{productId}")
    public ApiResponse processUpdateForm(@PathVariable("productId") Integer productId, @RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        ErrorsDetails errorsDetails = new ErrorsDetails();
        Product productByCode = productService.findProductByCode(productRequest.getCode());
        Product productByName = productService.findProductByName(productRequest.getName());
//        if (productByCode != null && !productByCode.getId().equals(productRequest.getId())) {
//            bindingResult.rejectValue("code", "error.product", "le code doit etre unique");
//        }
//        if (productByName != null && !productByName.getId().equals(productRequest.getId())) {
//            bindingResult.rejectValue("name", "error.product", "le nom doit etre unique");
//        }
        if (bindingResult.hasErrors() || (productRequest == null)) {
            errorsDetails.addAllErrors(bindingResult);
            System.out.println(errorsDetails);
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Erreur lors de la modification du produit",
                    errorsDetails
            );
        }
        Product product = productService.findProductById(productId);
        if (product == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le produit n'existe pas",
                    null
            );
        }
        Product updatedProduct = productService.save(product, productRequest);
        return new ApiResponse(
                true,
                HttpStatus.OK,
                "Le produit a eté modifié",
                updatedProduct
        );
    }

    @ApiOperation(value = "Trouver un produit", response = ApiResponse.class)
    @GetMapping("/products/{productId}")
    public ApiResponse findProduct(@PathVariable("productId") int productId) {
        Product product = productService.findProductById(productId);
        if (product == null) {
            return new ApiResponse(
                    false,
                    HttpStatus.OK,
                    "Le produit n'existe pas",
                    null
            );
        }
        return new ApiResponse(
                true,
                HttpStatus.OK,
                product.getCode() + " - " + product.getName(),
                product
        );
    }


}
