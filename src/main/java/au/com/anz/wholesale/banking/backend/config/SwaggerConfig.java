package au.com.anz.wholesale.banking.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger configuration.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Docker bean.
     * @return Docker docket.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("banking-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("au.com.anz.wholesale.banking.backend"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * ApiInfo object.
     * @return ApiInfo apiInfo.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Banking API")
                .description("Sample project to view Accounts and Transactions")
                .licenseUrl("sample.demo.com")
                .version("1.0")
                .build();
    }
}
