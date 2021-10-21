package com.rachadel;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringProjectApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(SpringProjectApplication.class, args);
	}

// # TESTES #    
//	@Bean(name = "transactionManager")
//	@Autowired
//
//	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager) {
//		System.out.println("start HibernateTransactionManager");
//		final PlatformTransactionManager platformTransactionManager = new JpaTransactionManager(entityManager);
//
//		System.out.println("finish HibernateTransactionManager");
//		return platformTransactionManager;
//	}

}
