package com.idat.evcontinua03.controller;

import com.idat.evcontinua03.model.Paciente;
import com.idat.evcontinua03.service.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private IPacienteService _pacienteService;
    public PacienteController(IPacienteService pacienteService) {
        _pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(_pacienteService.getPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable Long id){
        return ResponseEntity.ok(_pacienteService.getPacienteById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPaciente(@Valid @RequestBody Paciente paciente){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(_pacienteService.savePaciente(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  actualizarPaciente(@PathVariable Long id, @Valid @RequestBody Paciente paciente){
        return ResponseEntity.ok(_pacienteService.updatePaciente(id,paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){

        _pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }
}
