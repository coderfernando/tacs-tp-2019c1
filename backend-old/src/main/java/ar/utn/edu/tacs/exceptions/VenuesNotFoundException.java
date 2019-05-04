package ar.utn.edu.tacs.exceptions;

public class VenuesNotFoundException extends Exception {
    public VenuesNotFoundException() {

    }


    public VenuesNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

}
