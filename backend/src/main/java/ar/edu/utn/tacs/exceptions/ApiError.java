package ar.edu.utn.tacs.exceptions;

import java.util.List;

public class ApiError {

    private String message;
    private List<String> details;

    public ApiError(String message, List<String> errorMessages) {
        this.details = errorMessages;
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }


}
