package NaNSsoGong.MrDaeBakDining;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        Info info = new Info()
                .title("MrDaeBak Dinner Service")
                .description("Software Engineering Project by Team.NaNSsoGong")
                .version("V1.0")
                .contact(new Contact()
                        .name("Front WEB")
                        .url("https://uos.ac.kr"))
                .license(new License()
                        .name("Apache License Version 2.0")
                        .url("http://www.apache.org/license/LICENSE-2.0"));

        SecurityScheme auth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicAuth", auth))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .info(info);
    }
}