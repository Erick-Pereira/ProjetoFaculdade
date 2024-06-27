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

    @PostMapping(value = "/newreserva/{iduser}")
    public ResponseEntity<Object> saveReserva(@RequestBody @Valid ReservaRecord reservadto, @PathVariable("iduser") int iduser) {
    
        Optional<UsuarioModel> usuariomodel = usuarioservice.findById(iduser);
        if (usuariomodel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    
        List<ReservaModel> reservasDoDia = reservaservice.fingByData(reservadto.data());
        boolean isEspacoAReserved = reservasDoDia.stream().anyMatch(reserva -> reserva.getEspaco().equalsIgnoreCase("Salão A"));
        boolean isEspacoBReserved = reservasDoDia.stream().anyMatch(reserva -> reserva.getEspaco().equalsIgnoreCase("Salão B"));
    
        if (isEspacoAReserved && isEspacoBReserved) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ambos os salões já estão reservados no mesmo dia, tente outra data.");
        }
    
        if ((reservadto.espaco().equalsIgnoreCase("Salão A") && isEspacoAReserved) ||
            (reservadto.espaco().equalsIgnoreCase("Salão B") && isEspacoBReserved)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse espaço já está reservado, tente outro salão.");
        }
    
        var reservamodel = new ReservaModel();
        BeanUtils.copyProperties(reservadto, reservamodel);
        reservamodel.setUsuario(usuariomodel.get());
    
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaservice.save(reservamodel));
    }    

    @GetMapping(value = "/reserva")
    public ResponseEntity<List<ReservaModel>> getPosts() {
        List<ReservaModel> reserva = reservaservice.findAll();
        
        if (reserva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(reserva);
    }

    @GetMapping(value = "/reservausuario/{id}")
    public ResponseEntity<List<ReservaModel>> getRecervasUser(@PathVariable("id") int id) {
        List<ReservaModel> reserva = reservaservice.findByUser(id);
        
        if (reserva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(reserva);
    }

    @GetMapping(value = "/reserva/{id}")
    public ResponseEntity<Object> getReservaDetails(@PathVariable("id") int id) {
        Optional<ReservaModel> reserva = reservaservice.findById(id);
        
        if (!reserva.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reserva not found.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(reserva.get());
    }

    @DeleteMapping(value = "/deletereserva/{id}")
    public ResponseEntity<Object> deleteReserva(@PathVariable("id") int id) {
        Optional<ReservaModel> blogappModelOptional = reservaservice.findById(id);
        
        if (!blogappModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reserva not found.");
        }
        
        reservaservice.delete(blogappModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }

	@PutMapping(value = "/putreserva/{id}")
	public ResponseEntity<Object> putReserva(@RequestBody @Valid ReservaRecord reservadto, @PathVariable("id") int id) {
		Optional<ReservaModel> reservaOptional = reservaservice.findById(id);
		
		if (!reservaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva not found.");
		}
		
		ReservaModel reservaExistente = reservaOptional.get();
		
		// Copiar propriedades de reservadto para reservaExistente, excluindo "id" e "usuario"
		BeanUtils.copyProperties(reservadto, reservaExistente, "id", "usuario");
		
		// Salvar a reserva atualizada
		ReservaModel reservaAtualizada = reservaservice.save(reservaExistente);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reservaAtualizada);
	}	

    @GetMapping(value = "/reservames/{mes}")
    public ResponseEntity<List<ReservaModel>> getRecervasmes(@PathVariable("mes") int mes) {
        List<ReservaModel> reserva = reservaservice.findByMes(mes);
        
        if (reserva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reserva);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(reserva);
    }
}
