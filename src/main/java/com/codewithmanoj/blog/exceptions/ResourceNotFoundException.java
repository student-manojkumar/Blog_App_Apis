package com.codewithmanoj.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResourceNotFoundException extends RuntimeException {
	String resoureceName;
	String filedName;
	long fieldValue;
	public ResourceNotFoundException(String resoureceName, String filedName, long fieldValue) {
		super(String.format("%s  not found with  %s : %s", resoureceName,filedName,fieldValue));
		this.resoureceName = resoureceName;
		this.filedName = filedName;
		this.fieldValue = fieldValue;
	}
	
    
}
