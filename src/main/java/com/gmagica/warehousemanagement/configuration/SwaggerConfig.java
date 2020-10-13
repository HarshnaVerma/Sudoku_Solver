package com.gmagica.warehousemanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${owner-name}")
    String ownerName;
    @Value("${swagger-title}")
    String title;
    @Value("${swagger-description}")
    String description;
    @Value("${swagger-api-version}")
    String apiVersion;
    @Value("${swagger-api-license}")
    String license;
    @Value("${swagger-api-term}")
    String termOfUse;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_12)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gmagica.warehousemanagement.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termOfUse)
                .license(license)
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact(ownerName, "", ""))
                .version(apiVersion)
                .build();
    }
}
