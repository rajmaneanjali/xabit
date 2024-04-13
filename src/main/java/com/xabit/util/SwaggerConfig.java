package com.xabit.util;



import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
      final char ch[ ]= {65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 115, 32, 66, 121, 32, 65, 110, 106, 97, 108, 105, 32, 82, 97, 106, 109, 97,110,101};
      final char ch1[]={104,116,116,112,58,47,47,119,119,119,46,106,97,118,97,112,97,116,105,108,46,99,111,109,47};
      final char ch2[ ]={115,97,99,104,105,110,64,106,97,118,97,112,97,116,105,108,46,99,111,109};

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                new String(ch),
                "Online Management My Tool",
                "1.0",
                "Terms of service",
                new Contact("MyProj+", new String(ch1), new String(ch2)),
                "", "", new ArrayList<>());
        return apiInfo;
    }
}
