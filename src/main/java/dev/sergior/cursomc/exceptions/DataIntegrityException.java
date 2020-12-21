package dev.sergior.cursomc.exceptions;

public class DataIntegrityException extends Exception {

    public DataIntegrityException() {
    }

    public DataIntegrityException(String s) {
        super(s);
    }

    public DataIntegrityException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataIntegrityException(Throwable throwable) {
        super(throwable);
    }
}
