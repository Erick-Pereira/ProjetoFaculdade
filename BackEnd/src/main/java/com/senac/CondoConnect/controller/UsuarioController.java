package com.senac.CondoConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.Security.TokenService;
import com.senac.CondoConnect.dtos.AuthenticationDto;
import com.senac.CondoConnect.dtos.ResponseDto;
import com.senac.CondoConnect.dtos.UsuarioRecord;

import com.senac.CondoConnect.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins  = "*")
//@RequiredArgsConstructor
public class UsuarioController {

	//AuthenticationManager authenticationManager;

    //private final PasswordEncoder passwordEncoder;
    //private final TokenService tokenService;
	
	@Autowired
	UsuarioService usuarioservice;
	
	@PostMapping(value ="/newusuario") //retorna 201
	public ResponseEntity<Object> savePost(@RequestBody @Valid UsuarioRecord usuariodto) {
		
		var usermodel = new UsuarioModel();
		BeanUtils.copyProperties(usuariodto, usermodel);
		usermodel.setPassword(new BCryptPasswordEncoder().encode(usuariodto.password()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usermodel));
	}
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/usuario")
	public ResponseEntity<List<UsuarioModel>> getPosts(){
		List<UsuarioModel> user = usuarioservice.findAll();
		
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
			
		}
			return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	@GetMapping(value ="/usuario/{id}")
	public ResponseEntity<Object> getUsuarioDetails(@PathVariable("id") int id) {
		Optional<UsuarioModel> usuario = usuarioservice.findById(id);
		
		if(!usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
	}
	
	@DeleteMapping(value = "/deleteusuario/{id}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable("id") int id ){
		Optional<UsuarioModel> blogappModelOptional = usuarioservice.findById(id);
		
		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario not found.");
		}
		usuarioservice.delete(blogappModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted sucefully");
	}
	
	@PutMapping(value ="/putusuario/{id}")
	public ResponseEntity<Object> putUsuario(@RequestBody @Valid UsuarioRecord usuariodto,@PathVariable("id") int id){
		Optional<UsuarioModel> blogappModelOptional = usuarioservice.findById(id);

		if(!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario not found.");
		}
		var usuarioModel = new UsuarioModel();
		BeanUtils.copyProperties(usuariodto, usuarioModel);
		usuarioModel.setId(blogappModelOptional.get().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuarioModel));
	}
/*	@PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto body){
        UsuarioModel user = this.usuarioservice.findByEmail(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    } */
	
}
