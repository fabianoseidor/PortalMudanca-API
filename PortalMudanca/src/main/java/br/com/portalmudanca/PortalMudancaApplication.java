package br.com.portalmudanca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.portalmudanca.model")
@ComponentScan(basePackages = {"br.com.*"})
@EnableJpaRepositories(basePackages = {"br.com.portalmudanca.repository"})
@EnableTransactionManagement
public class PortalMudancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalMudancaApplication.class, args);
	}

}
