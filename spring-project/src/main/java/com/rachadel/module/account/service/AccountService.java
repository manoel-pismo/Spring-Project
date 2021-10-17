package com.rachadel.module.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rachadel.config.error.exception.ResourceNotFountException;
import com.rachadel.config.error.exception.ValidationErrorException;
import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.repository.AccountRepository;


/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@Service
@Transactional
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	// salvar
	public Account save(Account account) {
		account.setId(0L);
		this.verifyAccountNotExistsByDocumentNumber(account.getDocumentNumber());
		return accountRepository.save(account);
	}
	
	// encontrar todos
	public Page<Account> findAll(Pageable pageable){
		return accountRepository.findAll(pageable);
	}
	
	// encontrar por id
	public Optional<Account> findById(Long id) {
		this.verifyAccountExistsById(id);
		return accountRepository.findById(id);
	}
	
	// verificar se a conta tem saldo para retirada
	public void verifyAccountHasBalanceForWithdral(BigDecimal balance, BigDecimal withdrawalAmount) {
		if (balance.compareTo(withdrawalAmount) == 0) {  // -1 -- < menor que | -- 0 igual que | -- 1 maior que  
			throw new ValidationErrorException("account not have balance for withdral."
														+ " balance: "+ balance
														+ " withdrawa amount: "+ withdrawalAmount);
		}
	}
	
	// verificar se a conta tem saldo para compra
	public void verifyAccountHasBalanceForPurchase(BigDecimal balance, BigDecimal purchaseAmount) {
		if (balance.compareTo(purchaseAmount) == 0) {  // -1 -- < menor que | -- 0 igual que | -- 1 maior que  
			throw new ValidationErrorException("not have balance for purchase."
														+ " balance: "+ balance
														+ " purchase amount: "+ purchaseAmount);
		}
	}

	// verificar se a conta não existe por número de documento
	public void verifyAccountNotExistsByDocumentNumber(String documentNumber) {		
		if (accountRepository.findByDocumentNumber(documentNumber).isPresent()) {
			throw new ValidationErrorException("there is an account with 'documentNumber': " + documentNumber);
		}
	}	
	
	// verificar se a conta existe por ID
	public void verifyAccountExistsById(Long id) {		
		if (accountRepository.findById(id).isEmpty()) {
			throw new ResourceNotFountException("there is no account with 'id': " + id);
		}
	}
}