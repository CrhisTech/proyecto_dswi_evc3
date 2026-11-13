package com.idat.evcontinua03.exception;

public class DocumentoDuplicadoException extends RuntimeException {
    public DocumentoDuplicadoException(String documento) {
        super("Ya existe un paciente con el documento: "+documento);
    }
}
