package dev.sergior.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String message, Long timeStamp) {
        super(status, message, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldMessage> errors) {
        this.errors = errors;
    }

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }

}
