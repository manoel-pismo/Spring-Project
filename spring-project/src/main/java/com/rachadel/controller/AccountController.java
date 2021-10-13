package com.rachadel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rachadel.domain.Account;
import com.rachadel.service.AccountService;

/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping()
	public Account save(@RequestBody @Valid Account account) {
		return accountService.save(account);
	}
	
	@GetMapping()
	public List<Account> findAll(){
		return accountService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findByDocumentNumber(@PathVariable Long id) {
		return ResponseEntity.ok().body(accountService.findById(id));
	}

}
