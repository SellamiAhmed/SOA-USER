package com.example.UserOFGestion_Avion.service.register.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends RuntimeException {
	private String message;
	public InvalidTokenException(String message){
	 super(message);
	}
}
