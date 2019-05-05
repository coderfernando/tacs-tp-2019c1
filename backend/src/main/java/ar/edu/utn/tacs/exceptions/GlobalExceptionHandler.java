package ar.edu.utn.tacs.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({VenuesNotFoundException.class, MissingParametersException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof VenuesNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            VenuesNotFoundException unfe = (VenuesNotFoundException) ex;
            return handleVenuesNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof MissingParametersException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMissingParametersException((MissingParametersException) ex, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }


    private ResponseEntity<ApiError> handleVenuesNotFoundException(VenuesNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage() == null ? "No venues were found with the given parameters" : ex.getMessage();

        List<String> errors = Collections.singletonList(message);

        return handleExceptionInternal(ex, new ApiError("No venues math with the criteria", errors), headers, status, request);
    }

    private ResponseEntity<ApiError> handleMissingParametersException(MissingParametersException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ApiError("Missing mandatory parameters", ex.getMissedParams()), headers, status, request);
    }


    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
