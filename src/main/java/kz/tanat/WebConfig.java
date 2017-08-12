package kz.tanat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Конфигурационный класс приложения.
 *
 * @author Tanat
 * @since 15.07.2017.
 */
@Configuration
@EnableSwagger2
public class WebConfig {
	@Bean
	public Docket newsApi() {
		// http://localhost:8080/swagger-ui.html#/
		return new Docket(SWAGGER_2)
				.groupName("employees")
				.apiInfo(new ApiInfoBuilder()
						.title("Employee REST API")
						.contact(new Contact("Alpenov Tanat",
								"https://github.com/Researcher86/ddd_demo",
								"researcher2286@gmail.com"))
						.version("1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.basePackage("kz.tanat.web"))
				.build();
	}

}
