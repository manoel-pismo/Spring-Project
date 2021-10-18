package com.rachadel.module.account.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manoel Rachadel Neto
 * @since 12 de out. de 2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Accounts") 
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank()
	@Column(length = 100)
	private String name;
	
	@NotBlank
	@Email
	@Column(length = 50)
	private String email;

	@NotBlank
	@CPF 
	@Column(unique = true, nullable = false, length = 14)
	private String documentNumber;
	
	@NotNull
	private BigDecimal availableCreditLimit;

	public Account(Long id) {
		this.id = id;
	}

	public Account(Long id, @NotBlank String name, @NotBlank @Email String email,
			@NotBlank @CPF String documentNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.documentNumber = documentNumber;
	}
	
}
