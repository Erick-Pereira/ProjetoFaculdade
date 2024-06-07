package com.senac.CondoConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.CondoConnect.Model.AssembleiaModel;
import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.dtos.AssembleiaRecord;
import com.senac.CondoConnect.service.AssembleiaService;
import com.senac.CondoConnect.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AssembleiaController {

    @Autowired
    AssembleiaService assembleiaService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "newassembleia/{id}")
    public ResponseEntity<Object> saveAssembleia(@RequestBody @Valid AssembleiaRecord assembleiaDto, @PathVariable("id") int id) {
        Optional<UsuarioModel> usuarioModel = usuarioService.findById(id);
        if (usuarioModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario not found.");
        }

        var assembleiaModel = new AssembleiaModel();
        BeanUtils.copyProperties(assembleiaDto, assembleiaModel);
        assembleiaModel.setUsuario(usuarioModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(assembleiaService.save(assembleiaModel));
    }

    @GetMapping(value = "/assembleia")
    public ResponseEntity<List<AssembleiaModel>> getAssembleia() {
        List<AssembleiaModel> assembleia = assembleiaService.findAll();

        if (assembleia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assembleia);
        }
        return ResponseEntity.status(HttpStatus.OK).body(assembleia);
    }

    @GetMapping(value = "/assembleia/{id}")
    public ResponseEntity<Object> getAssembleiaDetails(@PathVariable("id") int id) {
        Optional<AssembleiaModel> assembleia = assembleiaService.findById(id);

        if (!assembleia.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assembleia not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(assembleia.get());
    }

    @DeleteMapping(value = "/deleteassembleia/{id}")
    public ResponseEntity<Object> deleteAssembleia(@PathVariable("id") int id) {
        Optional<AssembleiaModel> assembleiaModelOptional = assembleiaService.findById(id);

        if (!assembleiaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assembleia not found.");
        }
        assembleiaService.delete(assembleiaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @PutMapping(value = "/putassembleia/{id}")
    public ResponseEntity<Object> putAssembleia(@RequestBody @Valid AssembleiaRecord assembleiaDto, @PathVariable("id") int id) {
        Optional<AssembleiaModel> assembleiaModelOptional = assembleiaService.findById(id);

        if (!assembleiaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assembleia not found.");
        }

        AssembleiaModel assembleiaModel = assembleiaModelOptional.get();
        BeanUtils.copyProperties(assembleiaDto, assembleiaModel, "id", "usuario");

        AssembleiaModel updatedAssembleia = assembleiaService.save(assembleiaModel);

        return ResponseEntity.status(HttpStatus.OK).body(updatedAssembleia);
    }

    @GetMapping(value = "/assembleialist")
    public ResponseEntity<List<AssembleiaModel>> getAssembleiaList() {
        List<AssembleiaModel> assembleia = assembleiaService.getListAssembleia();

        if (assembleia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assembleia);
        }
        return ResponseEntity.status(HttpStatus.OK).body(assembleia);
    }

    @GetMapping(value = "/assembleialistuser/{id}")
    public ResponseEntity<List<AssembleiaModel>> getAssembleiaUser(@PathVariable("id") int id) {
        List<AssembleiaModel> assembleia = assembleiaService.getAssembleiaUser(id);

        if (assembleia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assembleia);
        }
        return ResponseEntity.status(HttpStatus.OK).body(assembleia);
    }
}
