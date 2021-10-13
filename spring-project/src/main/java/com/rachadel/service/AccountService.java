package com.rachadel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rachadel.domain.Account;
import com.rachadel.repository.AccountRepository;


/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account save(@RequestBody Account account) {
		return accountRepository.save(account);
	}
	
	public List<Account> findAll(){
		return accountRepository.findAll();
	}
	
	public Optional<Account> findById(Long id) {
		return accountRepository.findById(id);
	}

}
