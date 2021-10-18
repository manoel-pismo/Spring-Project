package com.rachadel.config.error;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author Manoel Rachadel Neto
 * @since  14 de out. de 2021
 */

@SuperBuilder
@Getter
public class ResponseErrorDetail {
	
	private String title;
	private String detail;	
	private String developerMessage;

}
