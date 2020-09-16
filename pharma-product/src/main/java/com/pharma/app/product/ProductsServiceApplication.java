package com.pharma.app.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
 * @author Rico Fauchard
 * @Date : 16-09-2020
 */

//@EnableDiscoveryClient
@SpringBootApplication
public class ProductsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }
}
