package com.idp.demo.configuration;


import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerSpecConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .paths(Predicate.not(PathSelectors.regex("/error.*")))
        .build();
  }

  // @Primary
  // @Bean
  // public SwaggerResourcesProvider swaggerResourcesProvider(
  // InMemorySwaggerResourcesProvider defaultResourcesProvider) {
  // return () -> {
  // List<SwaggerResource> resources = new ArrayList<>();
  // Arrays.asList("default").forEach(resourceName -> resources.add(loadResource(resourceName)));
  // return resources;
  // };
  // }
  //
  // private SwaggerResource loadResource(String resource) {
  // SwaggerResource wsResource = new SwaggerResource();
  // wsResource.setName(resource);
  // wsResource.setSwaggerVersion("2.0");
  // wsResource.setLocation("/swagger-apis/" + resource + "/openapi.yaml");
  // return wsResource;
  // }

}
