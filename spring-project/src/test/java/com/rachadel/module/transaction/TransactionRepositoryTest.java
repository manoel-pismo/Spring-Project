package com.rachadel.module.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.repository.AccountRepository;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.domain.enumeration.OperationType;
import com.rachadel.module.transaction.repository.TransactionRepository;

/**
 * @author Manoel Rachadel Neto
 * @since  15 de out. de 2021
 */
//@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransactionRepositoryTest {
	@Autowired
	private TransactionRepository transactionRespository;
	
	@Autowired
	private AccountRepository accountRepository;
	
 	@Test // Salvar Transaction. Deve persistir dados 
	public void saveTransactionShouldPersistData() {
 		Account account = new Account(1L, "Manoel Rachadel Neto", "manoelch13@gmail.com", "080.381.469-05");
 		this.accountRepository.save(account);
 		
 		Date now = new Date();
 		Transaction transaction = new Transaction(1L, account, new BigDecimal(1000), OperationType.CASH_PURCHASE, now);
		transaction = this.transactionRespository.save(transaction);
		
		assertThat(transaction.getId()).isNotNull();
		assertThat(transaction.getAccount().getId()).isEqualTo(1L);
		assertThat(transaction.getAmount()).isEqualTo(new BigDecimal(1000));
		assertThat(transaction.getOperationType()).isEqualTo(OperationType.CASH_PURCHASE);
		assertThat(transaction.getEventDate()).isEqualTo(now);
	}
 	
	@Test // Salvar Transaction. Quando a conta não existe d)eve lançar a exceção de entidade não encontrada 
	public void saveTransactionWhenAccountNotExistsShouldThrowEntityNotFoundException() {	
		Date now = new Date();
		Transaction transaction = new Transaction(1L, new Account(2L), new BigDecimal(1000), OperationType.CASH_PURCHASE,now);
		
		Exception exception = assertThrows(JpaObjectRetrievalFailureException.class, 
				() -> this.transactionRespository.save(transaction));
		System.out.println("Manoel > "+exception.getMessage());
		assertTrue(exception.getMessage().contains("Unable to find"));
	} 	
}