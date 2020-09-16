package com.pharma.app.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
 * @author: Rico Fauchard
 * @Date: 16-09-2020
 */

@SpringBootApplication
@EnableAdminServer
//@EnableDiscoveryClient
public class SpringBootAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }

}
