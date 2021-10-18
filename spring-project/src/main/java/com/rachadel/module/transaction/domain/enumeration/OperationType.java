package com.rachadel.module.transaction.domain.enumeration;

import java.util.Iterator;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.rachadel.config.error.exception.ValidationErrorException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** <pre> 
 * CASH_PURCHASE            --> [0]COMPRA_A_VISTA 
 * PURCHASE_IN_INSTALLMENTS --> [1]COMPRA PARCELADA
 * WITHDRAW                 --> [2]SAQUE
 * PAYMENT                  --> [3]PAGAMENTO</pre>
 * 
 * @author Manoel Rachadel Neto
 * @since 12 de out. de 2021
 */

@Getter
@AllArgsConstructor
public enum OperationType {

	CASH_PURCHASE(0, "COMPRA A VSTA"), PURCHASE_IN_INSTALLMENTS(1, "COMPRA PARCELADA"), WITHDRAW(2, "SAQUE"),
	PAYMENT(3, "PAGAMENTO");

	private Integer id;
	private String description;
		
	
	
	public static OperationType getById(Integer id) {
		if (id == null) return null;

		for (OperationType operationType : OperationType.values()) {
			if (id == operationType.getId()) {
				return operationType;
			}
		}
		
		throw new ValidationErrorException("invalid operation type. id: " + id);
	}
}
