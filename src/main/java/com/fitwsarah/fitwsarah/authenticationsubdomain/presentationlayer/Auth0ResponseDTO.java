package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;

public class Auth0ResponseDTO {

    private String message;

    public Auth0ResponseDTO(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
