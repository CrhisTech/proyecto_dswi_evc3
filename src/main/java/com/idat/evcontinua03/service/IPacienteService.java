package com.idat.evcontinua03.service;

import com.idat.evcontinua03.model.Paciente;

import java.util.List;

public interface IPacienteService {
    public List<Paciente> getPacientes();
    public Paciente getPacienteById(Long id);
    public Paciente savePaciente(Paciente paciente);
    public Paciente updatePaciente(Long id, Paciente paciente);
    public void deletePaciente(Long id);
    public boolean existsPacienteByDocumento(String documento);
    public boolean existsPacienteByDocumentoAndIdNot(String documento, Long id);
}
