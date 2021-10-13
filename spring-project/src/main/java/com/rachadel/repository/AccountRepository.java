package com.rachadel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rachadel.domain.Account;

/**
 * @author Manoel Rachadel Neto	
 * @since  12 de out. de 2021
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByDocumentNumber(String documentNumber);
}
