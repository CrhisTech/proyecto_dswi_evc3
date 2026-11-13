package com.idat.evcontinua03.exception;

public class PacienteNoEncontradoException extends RuntimeException {
    public PacienteNoEncontradoException(Long id) {
        super("Paciente no encontrado: " + id);
    }
}
