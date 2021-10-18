package com.rachadel.module.account.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rachadel.module.account.controller.dto.AccountDTO;
import com.rachadel.module.account.domain.Account;
import com.rachadel.module.account.service.AccountService;

import io.swagger.annotations.ApiOperation;

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
	@ApiOperation(value = "Save given Account", response = AccountDTO.class)
	public ResponseEntity<?> save(@RequestBody @Valid AccountDTO accountDTO) {
		this.accountService.save(accountDTO.toModel(accountDTO));
		return ResponseEntity.ok().build();		
	}
	
	@GetMapping()
	@ApiOperation(value = "Return a list with all Accounts", response = Account[].class)
	public ResponseEntity<Page<?>> findAll(Pageable pageable){
		return ResponseEntity.ok(accountService.findAll(pageable)); 
	}
	
	@ApiOperation(value = "Return Account by given id", response = Account.class)
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.findById(id));
	}
}
