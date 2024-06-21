package com.senac.CondoConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	ComunicadoService comunicadoservice; 
	@Autowired
	UsuarioService userservice;
	@PostMapping(value ="/newcomunicado/{id}") //retorna 201
	public ResponseEntity<Object> saveComunicado(@RequestBody @Valid ComunicadoRecord comunicadodto, @PathVariable("id") int id) {
		
		Optional<UsuarioModel> userModel = userservice.findById(id);
		var comunicadomodel = new ComunicadoModel();
		BeanUtils.copyProperties(comunicadodto, comunicadomodel);
		comunicadomodel.setUsuario(userModel.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(comunicadoservice.save(comunicadomodel));
	}
	
	@GetMapping(value ="/comunicado/{id}")
	public ResponseEntity<Object> getComunicadoDetails(@PathVariable("id") int id) {
		Optional<ComunicadoModel> comunicado = comunicadoservice.findById(id);
		
		if(!comunicado.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comunicado not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(comunicado.get());
	}
	
	@DeleteMapping(value = "/deletecomunicado/{id}")
	public ResponseEntity<Object> deleteComunicado(@PathVariable("id") int id ){
		Optional<ComunicadoModel> blogappModelOptional = comunicadoservice.findById(id);
		
		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comunicado not found.");
		}
		comunicadoservice.delete(blogappModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted sucefully");
	}
	
	@PutMapping(value ="/putcomunicadoa/{id}")
	public ResponseEntity<Object> putComunicado(@RequestBody @Valid ComunicadoRecord comunicadodto,@PathVariable("id") int id){
		Optional<ComunicadoModel> blogappModelOptional = comunicadoservice.findById(id);

		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comunicado not found.");
		}
		var comunicadoModel = new ComunicadoModel();
		BeanUtils.copyProperties(comunicadodto, comunicadoModel, "id", "usuario");
		comunicadoModel.setId(blogappModelOptional.get().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(comunicadoservice.save(comunicadoModel));
	}
	@GetMapping(value = "/comunicadolist")
	public ResponseEntity<List<ComunicadoModel>> getComunicadoList(){
		List<ComunicadoModel> comunicado = comunicadoservice.getListComunicado();
		
		if (comunicado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comunicado);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(comunicado);
	}

	@GetMapping(value = "/comuniadouser/{id}")
	public ResponseEntity<List<ComunicadoModel>> getComunicadoUser(@PathVariable("id") int id){
		List<ComunicadoModel> comunicado = comunicadoservice.findByUser(id);
		
		if (comunicado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comunicado);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(comunicado);
	}
	
}
