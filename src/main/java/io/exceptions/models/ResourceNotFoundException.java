package io.exceptions.models;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String firstName, lastName, text;
    private Object fieldValue;

    public ResourceNotFoundException(String firstName, String lastName, String text, Object fieldValue){
        super(String.format("%s not found with %s : '%s'", firstName,lastName,text,fieldValue));
        this.firstName=firstName;
        this.lastName=lastName;
        this.text=text;
        this.fieldValue=fieldValue;
    }

    public ResourceNotFoundException (String message){
        super(message);
    }
}
