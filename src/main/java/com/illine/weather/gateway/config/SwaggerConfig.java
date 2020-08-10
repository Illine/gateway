package com.illine.weather.gateway.config;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

    private static final String PATH_DELIMITER = "/";
    private static final String DEFAULT_API_LOCATION = "/v2/api-docs";
    private static final String DEFAULT_SWAGGER_VERSION = "3.0";

    @Bean
    @Primary
    SwaggerResourcesProvider swaggerResourcesProvider(ZuulProperties properties) {
        return () -> properties.getRoutes().values()
                .stream()
                .map(ZuulProperties.ZuulRoute::getId)
                .map(this::createResource)
                .collect(Collectors.toList());
    }

    private SwaggerResource createResource(String service) {
        var swaggerResource = new SwaggerResource();
        swaggerResource.setName(service);
        swaggerResource.setLocation(UriComponentsBuilder.newInstance().path(PATH_DELIMITER).path(service).path(DEFAULT_API_LOCATION).toUriString());
        swaggerResource.setSwaggerVersion(DEFAULT_SWAGGER_VERSION);
        return swaggerResource;
    }

}