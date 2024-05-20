package br.com.socialfit.social_fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.socialfit.social_fit.repositories")
public class SocialFitApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialFitApplication.class, args);
	}
}

