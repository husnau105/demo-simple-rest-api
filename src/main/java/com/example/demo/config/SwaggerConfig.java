package com.example.demo.config;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration() {
       Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
//        List<ResponseMessage> responseMessages = Arrays.asList(
//                new ResponseMessageBuilder().code(400).message("Bad request message from docket configuration").responseModel(new ModelRef("Error")).build(),
//                new ResponseMessageBuilder().code(500).message("Internal Server Error from docket configuration").responseModel(new ModelRef("Error")).build(),
//                new ResponseMessageBuilder().code(401).message("Unauthorized from docket").responseModel(new ModelRef("Error")).build(),
//                new ResponseMessageBuilder().code(403).message("Forbidden from docket").responseModel(new ModelRef("Error")).build());
////                new ResponseMessageBuilder().code(404).message("NotFound from docket").responseModel(new ModelRef("Error")).build())
//
//        docket.globalResponseMessage(RequestMethod.GET , responseMessages);
//        docket.globalResponseMessage(RequestMethod.POST , responseMessages);
//        docket.globalResponseMessage(RequestMethod.DELETE, responseMessages);
        return docket;
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Customer and Products API",
                "Sample API for learning purpose",
                "1.0",
                "Available for me :)",
                new springfox.documentation.service.Contact("husna"," ", "husna@dekatechs.com"),
                "licence",
                "licence url",
                Collections.emptyList());


    }
}
