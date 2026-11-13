package com.idat.evcontinua03.service;

import com.idat.evcontinua03.exception.DocumentoDuplicadoException;
import com.idat.evcontinua03.exception.PacienteNoEncontradoException;
import com.idat.evcontinua03.model.Paciente;
import com.idat.evcontinua03.repository.IPacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {
    private final IPacienteRepository _pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        _pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Paciente> getPacientes() {
        return _pacienteRepository.findAll();
    }

    @Override
    public Paciente getPacienteById(Long id) {
        return _pacienteRepository.findById(id)
                .orElseThrow(()-> new PacienteNoEncontradoException(id));
    }

    @Override
    public Paciente savePaciente(Paciente paciente) {
        if (_pacienteRepository.existsByDocumento(paciente.getDocumento())) {
            throw new DocumentoDuplicadoException(paciente.getDocumento());
        }
        paciente.setId(null);
        return _pacienteRepository.save(paciente);
    }

    @Override
    public Paciente updatePaciente(Long id, Paciente paciente) {
        getPacienteById(id);
        if (_pacienteRepository.existsByDocumentoAndIdNot(paciente.getDocumento(), id)) {
            throw new DocumentoDuplicadoException(paciente.getDocumento());
        }
        paciente.setId(id);
        return _pacienteRepository.save(paciente);
    }

    @Override
    public void deletePaciente(Long id) {
        Paciente paciente = getPacienteById(id);
        paciente.setActivo(false);
        _pacienteRepository.delete(paciente);
    }

    @Override
    public boolean existsPacienteByDocumento(String documento) {
        return _pacienteRepository.existsByDocumento(documento);
    }

    @Override
    public boolean existsPacienteByDocumentoAndIdNot(String documento, Long id) {
        return _pacienteRepository.existsByDocumentoAndIdNot(documento, id);
    }
}
