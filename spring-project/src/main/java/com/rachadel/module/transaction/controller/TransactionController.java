package com.rachadel.module.transaction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rachadel.module.transaction.controller.dto.TransactionDTO;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.service.TransactionService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping()
	@ApiOperation(value = "Save given Transaction", response = TransactionDTO.class)
	@Transactional
	public ResponseEntity<?> save(@RequestBody @Valid TransactionDTO transactionDTO) {
		this.transactionService.save(transactionDTO.toModel(transactionDTO));
		return ResponseEntity.ok().build();
	}
	
	@GetMapping()
	@ApiOperation(value = "Return a list with all Transactions", response = Transaction[].class)
	public ResponseEntity<Page<?>> findAll(Pageable pageable){
		return ResponseEntity.ok(transactionService.findAll(pageable)); 
	}
}
