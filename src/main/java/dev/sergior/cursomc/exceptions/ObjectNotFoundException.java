package dev.sergior.cursomc.exceptions;

public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String s) {
        super(s);
    }

    public ObjectNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ObjectNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
