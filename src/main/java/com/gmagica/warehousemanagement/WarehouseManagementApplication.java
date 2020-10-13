package com.gmagica.warehousemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@ComponentScan("com")
@SpringBootApplication
@EntityScan("com")
@EnableConfigurationProperties
public class WarehouseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseManagementApplication.class, args);
	}
	@Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:errors/messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }
}
