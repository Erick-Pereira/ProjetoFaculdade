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
@CrossOrigin( origins  = "*")
public class AchadoController {
@Autowired
	AchadoService achadoservice; 
	@Autowired
	UsuarioService usuarioservice; 
	
	@PostMapping(value ="/newachado/{id}") //retorna 201
	public ResponseEntity<Object> saveAchado(@RequestBody @Valid AchadoRecord achadodto, @PathVariable("id") int id) {
		
		Optional<UsuarioModel> usuariomodel = usuarioservice.findById(id);
		var achadomodel = new AchadoModel();
		BeanUtils.copyProperties(achadodto, achadomodel);
		achadomodel.setUsuario(usuariomodel.get());
		//return ResponseEntity.status(HttpStatus.CREATED).body(achadomodel);
		return ResponseEntity.status(HttpStatus.CREATED).body(achadoservice.save(achadomodel));
	}
	
	@GetMapping(value = "/achado")
	public ResponseEntity<List<AchadoModel>> getAchado(){
		List<AchadoModel> achado = achadoservice.findAll();
		
		if (achado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(achado);
	}
	
	@GetMapping(value ="/achado/{id}")
	public ResponseEntity<Object> getAchadoDetails(@PathVariable("id") int id) {
		Optional<AchadoModel> achado = achadoservice.findById(id);
		
		if(!achado.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("achado not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(achado.get());
	}
	
	@DeleteMapping(value = "/deleteachado/{id}")
	public ResponseEntity<Object> deleteAchado(@PathVariable("id") int id ){
		Optional<AchadoModel> blogappModelOptional = achadoservice.findById(id);
		
		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("achado not found.");
		}
		achadoservice.delete(blogappModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted sucefully");
	}
	
	@PutMapping(value = "/putachado/{id}")
    public ResponseEntity<Object> putAchado(@RequestBody @Valid AchadoRecord achadodto, @PathVariable("id") int id) {
        Optional<AchadoModel> achadoModelOptional = achadoservice.findById(id);
   
        if (!achadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("achado not found.");
        }
   
        AchadoModel achadoModel = achadoModelOptional.get();
        BeanUtils.copyProperties(achadodto, achadoModel, "id", "usuario");  // Preserve o ID e o usuário
   
        AchadoModel updatedAchado = achadoservice.save(achadoModel);
   
        return ResponseEntity.status(HttpStatus.OK).body(updatedAchado);
    }  
	@GetMapping(value = "/achadolist")
	public ResponseEntity<List<AchadoModel>> getAchadoList(){
		List<AchadoModel> achado = achadoservice.getListAchado();
		
		if (achado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(achado);
	}
	@GetMapping(value = "/achadolistuser/{id}")
	public ResponseEntity<List<AchadoModel>> getAchadoUser(@PathVariable("id") int id){
		List<AchadoModel> achado = achadoservice.getAchadoUser(id);
		
		if (achado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(achado);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(achado);
	}
}