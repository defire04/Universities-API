package com.example.config;//package com.example.config;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Loyalty System Api",
//                description = "Loyalty System", version = "1.0.0",
//                contact = @Contact(
//                        name = "Struchkov Mark",
//                        email = "mark@struchkov.dev",
//                        url = "https://mark.struchkov.dev"
//                )
//        )
//)
//public class OpenApiConfig {
//
//}


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.api.controllers"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//}
