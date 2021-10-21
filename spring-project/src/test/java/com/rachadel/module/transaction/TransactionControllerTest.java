package com.rachadel.module.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.repository.AccountRepository;
import com.rachadel.module.transaction.controller.dto.TransactionDTO;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.domain.enumeration.OperationType;
import com.rachadel.module.transaction.repository.TransactionRepository;

/**
 * @author Manoel Rachadel Neto
 * @since  16 de out. de 2021
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	@MockBean
	private AccountRepository accountRepository;
	
//	@Autowired
//	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config{
		
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder();//.basicAuthentication("teste", "teste");
		}
	}
		
	@Test // Salvar Transaction. Quando a conta não existe deve retornar o status 400 
	public void saveTransactionWhenAccountNotExistsShouldReturnStatus400() {
 		Date now = new Date();
		Transaction transaction = new Transaction(2L, new Account(6L), new BigDecimal(1000), OperationType.CASH_PURCHASE, now);
		BDDMockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		TransactionDTO transactionDTO = new TransactionDTO(transaction);		
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/transactions", transactionDTO, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test // Salvar Transaction. Quando o valor não positivo deve retornar status 400
	public void saveTransactionWhenNotPositiveAmountShouldReturnStatus400() {	
 		Date now = new Date();
		Transaction transaction = new Transaction(1L, new Account(1L), new BigDecimal(0), OperationType.CASH_PURCHASE, now);
		BDDMockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		TransactionDTO transactionDTO = new TransactionDTO(transaction);		
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/transactions", transactionDTO, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test // Salvar Transaction. Quando tipo de operação inválido deve retornar o status 400
	public void saveTransactionWhenInvalidOperationTypeShouldReturnStatus400() {
 		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05");
 		this.accountRepository.save(account);
 		
 		Date now = new Date();
		Transaction transaction = new Transaction(1L, account, new BigDecimal(1000), OperationType.CASH_PURCHASE, now);
		BDDMockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		TransactionDTO transactionDTO = new TransactionDTO(transaction);		
		transactionDTO.setOperationTypeId(39);
		ResponseEntity<String> response = restTemplate.postForEntity("/v1/transactions", transactionDTO, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}	
	

	@Test // Salvar Account. Quando o e-mail incorreto retornar com o status 400
	public void saveTransationalBathTest() {
		var account = new Account(2L, "conta de teste", "manoelch13@gmail.com", "023.646.545-72", new BigDecimal(600));

		final ResponseEntity<Account> responseEntity = restTemplate.exchange("http://localhost:8080/v1/accounts",
				HttpMethod.PUT, this.heades(account), Account.class);
		
		assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));

		List<Thread> lstThread = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			var t1 = new Thread() {
				public void run() {
					transactionWithdraw(account);
				}
			};
			lstThread.add(t1);
		}
		for (Thread thread : lstThread) {
			thread.start();
		}
	}

	private HttpEntity<Account> heades(Account account) {
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("application", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		return new HttpEntity<Account>(account,headers);
	}

	private void transactionWithdraw(Account account) {
		var quantia = new BigDecimal(250.00);
		var transaction = new Transaction(null, account, quantia, OperationType.WITHDRAW, new Date());

		TransactionDTO dto = new TransactionDTO(transaction);
		ResponseEntity<String> respSave = restTemplate.postForEntity("http://localhost:8080/v1/transactions", dto,
				String.class);

		switch (respSave.getStatusCode().value()) {
		case 200:
			System.err.println(new Date() + " | SAQUE EFETUADO: " + quantia);
			break;
		case 400:
			System.err.println(new Date() + " | SAQUE RECUSADO: " + quantia);
		default:
			System.err.println(new Date() + " | SAQUE RECUSADO: " + quantia);
			break;
		}
	}
	
}
