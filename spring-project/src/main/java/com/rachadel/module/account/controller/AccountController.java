package com.rachadel.module.account.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.service.AccountService;

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
	public ResponseEntity<?> save(@RequestBody @Valid Account account) {
		return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);		
	}
	
	@GetMapping()
	public ResponseEntity<Page<?>> findAll(Pageable pageable){
		return new ResponseEntity<>(accountService.findAll(pageable), HttpStatus.OK); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
	}
}
