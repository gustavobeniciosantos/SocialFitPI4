package br.com.socialfit.social_fit;

import br.com.socialfit.social_fit.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"br.com.socialfit.social_fit.repositories", "br.com.socialfit.social_fit.controllers", "br.com.socialfit.social_fit.service"})
@EnableJpaRepositories(basePackages = "br.com.socialfit.social_fit.repositories")
@Import(WebConfig.class)
public class SocialFitApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialFitApplication.class, args);
	}
}
