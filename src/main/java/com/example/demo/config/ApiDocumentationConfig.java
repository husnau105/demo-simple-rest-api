package com.example.demo.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Demo Application",
                version = "V2.0",
                title = "Simple Demo API",
                contact = @Contact(
                        name = "Husna Ucar",
                        email = "xx@xx.com",
                        url = "http://localhost"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS}
)
public interface ApiDocumentationConfig {
}
