package com.bank.account.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apis(ConfigurableEnvironment environment) {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(environment.getProperty("config.host"))
                .groupName(environment.getProperty("spring.application.name"))
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .genericModelSubstitutes(Optional.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bank.account"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SwaggerJacksonModuleRegistrar swaggerJacksonModuleRegistrar() {
        return new SwaggerJacksonModuleRegistrar();
    }

}
