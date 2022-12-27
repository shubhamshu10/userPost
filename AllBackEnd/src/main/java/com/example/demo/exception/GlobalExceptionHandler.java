package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
	   String message = e.getMessage();
	   ApiResponse res= new ApiResponse(message,false);
	   return new ResponseEntity<ApiResponse>(res,HttpStatus.NOT_FOUND);
   }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentExceptionHandler(MethodArgumentNotValidException e){
		Map<String, String> resp = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message=error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);		
	}
	@ExceptionHandler(ApiException.class)
	   public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException e){
		   String message = e.getMessage();
		   ApiResponse res= new ApiResponse(message,true);
		   return new ResponseEntity<ApiResponse>(res,HttpStatus.BAD_REQUEST);
	   }
}
