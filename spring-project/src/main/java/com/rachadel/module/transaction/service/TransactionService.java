package com.rachadel.module.transaction.service;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rachadel.config.error.exception.ValidationErrorException;
import com.rachadel.module.account.repository.AccountRepository;
import com.rachadel.module.account.service.AccountService;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.repository.TransactionRepository;


/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountService accountService;	
	
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	// Salvar
	public Transaction save(@RequestBody Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	// Efetuar Saque
	public void makeWithdral(@NotNull BigDecimal valor, @NotNull Long accountId) {
		var account = accountService.findById(accountId).get();
		
		this.verifyWithdrawalOperationValid(valor);
		this.accountService.verifyAccountHasBalanceForWithdral(account.getBalance(), valor);
		
		account.setBalance(account.getBalance().subtract(valor));
		accountService.save(account);
	}
	
	// Efetuar Compra
	public void makePurchase(@NotNull BigDecimal valor, @NotNull Long accountId) {
		var account = accountService.findById(accountId).get();
		
		this.verifyPurchaseOperationValid(valor);
		this.accountService.verifyAccountHasBalanceForWithdral(account.getBalance(), valor);
		
		account.setBalance(account.getBalance().subtract(valor));
		accountService.save(account);
	}
	
	// verificar operação de retirada válida 
	public void verifyWithdrawalOperationValid(@NotNull BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) == 1) {  // -1 -- < menor que | -- 0 igual que | -- 1 maior que  
			throw new ValidationErrorException("Withdrawal type operations must be registered with a negative amount");
		}		
	}
	
	// verificar operação de compra válida
	public void verifyPurchaseOperationValid(@NotNull BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) == 1) {  // -1 -- < menor que | -- 0 igual que | -- 1 maior que  
			throw new ValidationErrorException("Purchase-type transactions must be registered with a negative value");
		}		
	}	
	
	/*
	- Transações de tipo compra e saque são registradas com valor negativo
	- transações de pagamento são registradas com valor positivo.
	*/	
	
	
	//	if (valor != null && valor.compareTo(account.getBalance()) >= 0) {  // -1 -- < menor que | -- 0 igual que | -- 1 maior que  
	//	throw new ValidationErrorException("Verificando se tem saldo para saque: " + valor);
	//}
}
