package NaNSsoGong.MrDaeBakDining;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class MrDaeBakDiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrDaeBakDiningApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:5173", "https://mr-daebak.netlify.app", "https://mrdaebakservice.kro.kr")
						.allowedHeaders("*")
						.allowedMethods("*")
						.allowCredentials(true);
			}
		};
	}

	@Bean
	public OpenAPI api() {
		Info info = new Info()
				.title("MrDaeBak Dinner Service")
				.description("Software Engineering Project by Team.NaNSsoGong")
				.version("V1.0")
				.contact(new Contact()
						.name("Front WEB")
						.url("https://mr-daebak.netlify.app/"))
				.license(new License()
						.name("Apache License Version 2.0")
						.url("http://www.apache.org/license/LICENSE-2.0"));

		SecurityScheme auth = new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.COOKIE)
				.name("JSESSIONID");

		Server local = new Server();
		local.setDescription("local");
		local.setUrl("http://localhost:8080");

		Server prod = new Server();
		prod.setDescription("prod");
		prod.setUrl("https://mrdaebakservice.kro.kr");

		return new OpenAPI()
//				.servers(List.of(local, prod))
				.components(new Components().addSecuritySchemes("basicAuth", auth))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				.info(info);
	}
}
