package com.senac.CondoConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.CondoConnect.Model.ComunicadoModel;
import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.dtos.ComunicadoRecord;
import com.senac.CondoConnect.service.ComunicadoService;
import com.senac.CondoConnect.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class ComunicadoController {

    @Autowired
    ComunicadoService comunicadoService;
    
    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value ="/newcomunicado/{id}")
    public ResponseEntity<Object> saveComunicado(@RequestBody @Valid ComunicadoRecord comunicadoDto, @PathVariable("id") int id) {
        Optional<UsuarioModel> usuarioModel = usuarioService.findById(id);
        if (usuarioModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario not found.");
        }

        var comunicadoModel = new ComunicadoModel();
        BeanUtils.copyProperties(comunicadoDto, comunicadoModel);
        comunicadoModel.setUsuario(usuarioModel.get());
        comunicadoModel.setData(comunicadoDto.data());

        return ResponseEntity.status(HttpStatus.CREATED).body(comunicadoService.save(comunicadoModel));
    }

    @GetMapping(value ="/comunicado/{id}")
    public ResponseEntity<Object> getComunicadoDetails(@PathVariable("id") int id) {
        Optional<ComunicadoModel> comunicado = comunicadoService.findById(id);
        
        if (!comunicado.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunicado not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(comunicado.get());
    }

    @DeleteMapping(value = "/deletecomunicado/{id}")
    public ResponseEntity<Object> deleteComunicado(@PathVariable("id") int id) {
        Optional<ComunicadoModel> comunicadoModelOptional = comunicadoService.findById(id);
        
        if (!comunicadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunicado not found.");
        }
        comunicadoService.delete(comunicadoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

    @PutMapping(value ="/putcomunicado/{id}")
    public ResponseEntity<Object> putComunicado(@RequestBody @Valid ComunicadoRecord comunicadoDto, @PathVariable("id") int id) {
        Optional<ComunicadoModel> comunicadoModelOptional = comunicadoService.findById(id);

        if (!comunicadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunicado not found.");
        }

        ComunicadoModel comunicadoModel = comunicadoModelOptional.get();
        BeanUtils.copyProperties(comunicadoDto, comunicadoModel, "id", "usuario");
        comunicadoModel.setData(comunicadoDto.data());

        ComunicadoModel updatedComunicado = comunicadoService.save(comunicadoModel);

        return ResponseEntity.status(HttpStatus.OK).body(updatedComunicado);
    }

    @GetMapping(value = "/comunicadolist")
    public ResponseEntity<List<ComunicadoModel>> getComunicadoList() {
        List<ComunicadoModel> comunicado = comunicadoService.getListComunicado();
        
        if (comunicado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comunicado);
        }
        return ResponseEntity.status(HttpStatus.OK).body(comunicado);
    }

    @GetMapping(value = "/comunicadouser/{id}")
    public ResponseEntity<List<ComunicadoModel>> getComunicadoUser(@PathVariable("id") int id) {
        List<ComunicadoModel> comunicado = comunicadoService.findByUser(id);
        
        if (comunicado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comunicado);
        }
        return ResponseEntity.status(HttpStatus.OK).body(comunicado);
    }
}
