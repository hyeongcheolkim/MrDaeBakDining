package NaNSsoGong.MrDaeBakDining;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    @Bean
    Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

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
