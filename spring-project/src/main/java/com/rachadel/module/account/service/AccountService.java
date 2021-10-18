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
		account.setId(null);
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
	
	public void verifyBalanceAccount(Long id, BigDecimal quantia) {
		Account account = this.findById(id).get();		
		if (quantia.compareTo(account.getAvailableCreditLimit()) == 1) {  //-1 menor que| 0 igual | 1 maior que, 
			throw new ValidationErrorException("Saldo insuficiente");
		}		
	}
	
	public void efetuarPagamento(Long id, BigDecimal quantia) {
		Account account = this.findById(id).get();
		BigDecimal valor = account.getAvailableCreditLimit().add(quantia);
		account.setAvailableCreditLimit(valor);
		accountRepository.save(account);		
	}
	
	
	public void efetuarSaque(Long id, BigDecimal quantia) {
		this.verifyBalanceAccount(id, quantia);
		Account account = this.findById(id).get();
		BigDecimal valor = account.getAvailableCreditLimit().subtract(quantia);
		account.setAvailableCreditLimit(valor);
		accountRepository.save(account);		
	}	
}
