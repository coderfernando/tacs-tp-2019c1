package ar.utn.edu.tacs.exceptions;

import java.util.List;

public class MissingParametersException extends Exception {


    private List<String> missedParams;

    public MissingParametersException(List<String> params) {
        this.missedParams = params;
    }

    public List<String> getMissedParams() {
        return missedParams;
    }
}
