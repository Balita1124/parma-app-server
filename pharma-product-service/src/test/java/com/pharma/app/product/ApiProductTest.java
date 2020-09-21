package com.pharma.app.product;

import com.pharma.app.product.api.ApiProduct;
import com.pharma.app.product.model.Product;
import com.pharma.app.product.playload.product.ProductRequest;
import com.pharma.app.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Rico Fauchard
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiProduct.class)
public class ApiProductTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ProductService productService;

    @Test
    void shouldGetAProduct() throws Exception {

        Product product = setupProduct();

        given(productService.findProductById(2)).willReturn(product);


        mvc.perform(get("/api/products/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("000002 - EFFERALGAN"))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.name").value("EFFERALGAN"))
                .andExpect(jsonPath("$.data.code").value("000002"))
                .andExpect(jsonPath("$.data.validityDate").value("20-09-2020"))
                .andExpect(jsonPath("$.data.price").value(2000.00));
    }
    @Test
    void shouldPostAProduct() throws Exception {

        Product product = setupProduct();

        given(productService.findProductById(2)).willReturn(product);


        mvc.perform(get("/api/products/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("000002 - EFFERALGAN"))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.name").value("EFFERALGAN"))
                .andExpect(jsonPath("$.data.code").value("000002"))
                .andExpect(jsonPath("$.data.validityDate").value("20-09-2020"))
                .andExpect(jsonPath("$.data.price").value(2000.00));
    }

    private Product setupProduct() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
        Product product = new Product("EFFERALGAN", "000002", formatter.parse("20-09-2020"), BigDecimal.valueOf(2000));
        product.setId(2);
        return product;
    }

    ProductRequest setupProductRequest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("EFFERALGAN");
        productRequest.setCode("000002");
        productRequest.setValidityDate(formatter.parse("20-09-2020"));
        productRequest.setPrice(BigDecimal.valueOf(2000));
        return productRequest;
    }
}
