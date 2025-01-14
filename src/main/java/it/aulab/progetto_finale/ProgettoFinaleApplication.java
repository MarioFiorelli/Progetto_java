package it.aulab.progetto_finale;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement
public class ProgettoFinaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoFinaleApplication.class, args);
	}

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        }
		@Bean
	public ModelMapper instanceModelMapper(){
		ModelMapper mapper = new ModelMapper();
		return mapper;
	}
    
}