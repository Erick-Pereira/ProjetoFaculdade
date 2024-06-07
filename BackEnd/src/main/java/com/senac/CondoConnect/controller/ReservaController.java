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

import com.senac.CondoConnect.Model.ReservaModel;
import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.dtos.ReservaRecord;
import com.senac.CondoConnect.service.ReservaService;
import com.senac.CondoConnect.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class ReservaController {

	@Autowired
	ReservaService reservaservice; 
	@Autowired
	UsuarioService usuarioservice; 
	
	@PostMapping(value ="/newreserva/{iduser}") //retorna 201
	public ResponseEntity<Object> saveReserva(@RequestBody @Valid ReservaRecord reservadto, @PathVariable("iduser") int iduser) {
		
		Optional<UsuarioModel> usuariomodel = usuarioservice.findById(iduser);
		var reservamodel = new ReservaModel();
		BeanUtils.copyProperties(reservadto, reservamodel);
		reservamodel.setUsuario(usuariomodel.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaservice.save(reservamodel));
	}
	
	@GetMapping(value = "/reserva")
	public ResponseEntity<List<ReservaModel>> getPosts(){
		List<ReservaModel> reserva = reservaservice.findAll();
		
		if (reserva.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(reserva);
	}
	
	@GetMapping(value = "/reservausuario/{id}")
	public ResponseEntity<List<ReservaModel>> getRecervasUser(@PathVariable("id") int id){
		List<ReservaModel> reserva = reservaservice.findByUser(id);
		
		if (reserva.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
			
		}
		
			return ResponseEntity.status(HttpStatus.OK).body(reserva);
	}
	
	@GetMapping(value ="/reserva/{id}")
	public ResponseEntity<Object> getReservaDetails(@PathVariable("id") int id) {
		Optional<ReservaModel> reserva = reservaservice.findById(id);
		
		if(!reserva.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reserva not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(reserva.get());
	}
	
	@DeleteMapping(value = "/deletereserva/{id}")
	public ResponseEntity<Object> deleteReserva(@PathVariable("id") int id ){
		Optional<ReservaModel> blogappModelOptional = reservaservice.findById(id);
		
		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reserva not found.");
		}
		reservaservice.delete(blogappModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted sucefully");
	}
	
	@PutMapping(value ="/putreserva/{id}")
	public ResponseEntity<Object> putReserva(@RequestBody @Valid ReservaRecord reservadto,@PathVariable("id") int id){
		Optional<ReservaModel> blogappModelOptional = reservaservice.findById(id);

		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reserva not found.");
		}
		var reservaModel = new ReservaModel();
		BeanUtils.copyProperties(reservadto, reservaModel);
		reservaModel.setId(blogappModelOptional.get().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaservice.save(reservaModel));
	}
	@GetMapping(value = "/reservames/{mes}")
	public ResponseEntity<List<ReservaModel>> getRecervasmes(@PathVariable("mes") int mes){
		List<ReservaModel> reserva = reservaservice.findByMes(mes);
		
		if (reserva.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
			
		}
		
			return ResponseEntity.status(HttpStatus.OK).body(reserva);
	}
}