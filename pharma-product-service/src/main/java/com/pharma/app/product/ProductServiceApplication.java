package com.pharma.app.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/***
 * @author Rico Fauchard
 * @Date : 16-09-2020
 */

@SpringBootApplication
//@EntityScan(basePackageClasses = {
//        ProductServiceApplication.class,
//        Jsr310JpaConverters.class
//})
public class ProductServiceApplication {
//    @PostConstruct
//    void init() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
