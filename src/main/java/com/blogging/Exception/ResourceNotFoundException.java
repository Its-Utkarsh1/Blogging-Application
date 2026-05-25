package com.blogging.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String e){
        super(e);
    }

    public ResourceNotFoundException(){
        super("resource not found");
    }

}
