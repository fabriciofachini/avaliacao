package br.com.cooperforte.avaliacao.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${api-version:#{null}}")
	private String apiVersion;

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2) //
				.useDefaultResponseMessages(false) //
				.select().apis(RequestHandlerSelectors.any()) //
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build() //
				.apiInfo(apiInfo()) //
				.protocols(protocols()) //
				.securitySchemes(securitySchemes()) //
				.securityContexts(securityContexts());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder() //
				.title("API - Avaliacao") //
				.description("Avaliacao") //
				.version(apiVersion == null ? "1.0.0" : apiVersion) //
				.build();
	}

	private Set<String> protocols() {

		Set<String> protocols = new HashSet<>();
		protocols.add("http");
		protocols.add("https");
		return protocols;
	}

	private List<? extends SecurityScheme> securitySchemes() {
		List<SecurityScheme> authorizationTypes = Arrays.asList(new ApiKey("token", "Authorization", "header"));
		return authorizationTypes;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = Arrays.asList(SecurityContext.builder().forPaths(PathSelectors.any())
				.securityReferences(securityReferences()).build());
		return securityContexts;
	}

	private List<SecurityReference> securityReferences() {
		List<SecurityReference> securityReferences = Arrays
				.asList(SecurityReference.builder().reference("token").scopes(new AuthorizationScope[0]).build());
		return securityReferences;
	}

}
