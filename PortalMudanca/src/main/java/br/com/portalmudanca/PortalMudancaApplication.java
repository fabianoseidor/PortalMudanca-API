package br.com.portalmudanca;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;

import javax.mail.MessagingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EntityScan(basePackages = "br.com.portalmudanca.model")
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.portalmudanca.repository"})
@EnableTransactionManagement
//@EnableWebMvc
public class PortalMudancaApplication implements AsyncConfigurer, WebMvcConfigurer{
		
	public static void main(String[] args) throws ExceptionCustomizada, UnsupportedEncodingException, MessagingException{
		
//		System.out.println( new BCryptPasswordEncoder().encode("123") );
		SpringApplication.run(PortalMudancaApplication.class, args);
	}
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();
		
		return executor;
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedHeaders("*")
		.allowedMethods("*")
		.exposedHeaders("*");
		
		//WebMvcConfigurer.super.addCorsMappings(registry);
	}

}
