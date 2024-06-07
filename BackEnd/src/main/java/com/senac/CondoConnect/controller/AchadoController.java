package com.senac.CondoConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.CondoConnect.Model.AchadoModel;
import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.dtos.AchadoRecord;
import com.senac.CondoConnect.service.AchadoService;
import com.senac.CondoConnect.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AchadoController {
    @Autowired
    AchadoService achadoService;
    
    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/newachado/{id}")
    public ResponseEntity<Object> saveAchado(@RequestBody @Valid AchadoRecord achadoDto, @PathVariable("id") int id) {
        Optional<UsuarioModel> usuarioModel = usuarioService.findById(id);
        if (usuarioModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario not found.");
        }
        
        var achadoModel = new AchadoModel();
        BeanUtils.copyProperties(achadoDto, achadoModel);
        achadoModel.setUsuario(usuarioModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(achadoService.save(achadoModel));
    }

    @GetMapping(value = "/achado")
    public ResponseEntity<List<AchadoModel>> getAchado() {
        List<AchadoModel> achado = achadoService.findAll();

        if (achado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
        }
        return ResponseEntity.status(HttpStatus.OK).body(achado);
    }

    @GetMapping(value = "/achado/{id}")
    public ResponseEntity<Object> getAchadoDetails(@PathVariable("id") int id) {
        Optional<AchadoModel> achado = achadoService.findById(id);

        if (!achado.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Achado not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(achado.get());
    }

    @DeleteMapping(value = "/deleteachado/{id}")
    public ResponseEntity<Object> deleteAchado(@PathVariable("id") int id) {
        Optional<AchadoModel> achadoModelOptional = achadoService.findById(id);

        if (!achadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Achado not found.");
        }
        achadoService.delete(achadoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @PutMapping(value = "/putachado/{id}")
    public ResponseEntity<Object> putAchado(@RequestBody @Valid AchadoRecord achadoDto, @PathVariable("id") int id) {
        Optional<AchadoModel> achadoModelOptional = achadoService.findById(id);

        if (!achadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Achado not found.");
        }

        AchadoModel achadoModel = achadoModelOptional.get();
        BeanUtils.copyProperties(achadoDto, achadoModel, "id", "usuario");

        AchadoModel updatedAchado = achadoService.save(achadoModel);

        return ResponseEntity.status(HttpStatus.OK).body(updatedAchado);
    }

    @GetMapping(value = "/achadolist")
    public ResponseEntity<List<AchadoModel>> getAchadoList() {
        List<AchadoModel> achado = achadoService.getListAchado();

        if (achado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
        }
        return ResponseEntity.status(HttpStatus.OK).body(achado);
    }

    @GetMapping(value = "/achadolistuser/{id}")
    public ResponseEntity<List<AchadoModel>> getAchadoUser(@PathVariable("id") int id) {
        List<AchadoModel> achado = achadoService.getAchadoUser(id);

        if (achado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
        }
        return ResponseEntity.status(HttpStatus.OK).body(achado);
    }
}
