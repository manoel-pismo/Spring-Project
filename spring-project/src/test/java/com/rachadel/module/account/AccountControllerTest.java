package com.rachadel.module.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.util.Assert.*;

import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.repository.AccountRepository;
import com.rachadel.module.transaction.repository.TransactionRepository;

/**
 * @author Manoel Rachadel Neto
 * @since 16 de out. de 2021
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private TransactionRepository transactionRepository;

//	@Autowired
//	private MockMvc mockMvc;

	@TestConfiguration
	static class Config {

		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder();// .basicAuthenticatipon("teste", "teste");
		}
	}
//
	@Test // Salvar Account. Quando o correto deve retornar o status 200 
	public void saveAccountWhenCorrectShouldReturnStatus200() {
		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05", new BigDecimal(140.00));
		BDDMockito.when(accountRepository.save(account)).thenReturn(account);
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/accounts", account, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	
	@Test // Salvar Account. Quando o número do documento null deve retornar com o status 400 
	public void saveAccountWhenNullDocumentNumberShouldReturnStatus400() {
		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "", new BigDecimal(140.00));
		BDDMockito.when(accountRepository.save(account)).thenReturn(account);
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/accounts", account, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}	
	
	@Test // Salvar Account. Quando o número do documento incorreto retornar com o status 400 
	public void saveAccountWhenIncorrectDocumentNumberShouldReturnStatus400() {
		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "280.381.369-08" , new BigDecimal(140.00));
		BDDMockito.when(accountRepository.save(account)).thenReturn(account);
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/accounts", account, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}		
	
	@Test // listar contas quando correto deve retornar o código de status200
	public void listAccountsWhenCorrectShouldReturnStatusCode200() {
		List<Account> accounts = asList(new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05", new BigDecimal(140.00)),
										new Account(2L, "Maria Joaquina", "maria.joaquina@gmail.com", "187.068.858-96" , new BigDecimal(140.00)));
		BDDMockito.when(accountRepository.findAll()).thenReturn(accounts);
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/accounts", String.class);
		response.getBody();
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test // obter contas por id deve retornar o código de status 200 
	public void getAccountsByIdShouldReturnStatusCode200() {
		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05" , new BigDecimal(140.00));
		Optional<Account> obj = Optional.of(account);
		BDDMockito.when(accountRepository.findById(1L)).thenReturn(obj);		
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/accounts/1", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test // obter contas por id deve retornar o código de status 404 
	public void getAccountsByIdShouldReturnStatusCode404() {
		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05" , new BigDecimal(140.00));
		Optional<Account> obj = Optional.of(account);
		BDDMockito.when(accountRepository.findById(1L)).thenReturn(obj);		
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/accounts/1", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}	

}
