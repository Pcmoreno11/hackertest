package com.hotelbeds.supplierintegrations.hackertest.dto;

public enum Action {
	SIGNIN_SUCCESS("SIGNIN_SUCCESS"), SIGNIN_FAILURE("SIGNIN_FAILURE");
	
    private String value; 
  
    public String getValue(){ 
        return this.value; 
    } 
  
    private Action(String value){ 
        this.value = value; 
    } 
}
