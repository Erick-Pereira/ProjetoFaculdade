package com.senac.CondoConnect.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.CondoConnect.Enum.RoleName;
import com.senac.CondoConnect.Model.UsuarioModel;
import com.senac.CondoConnect.Security.TokenService;
import com.senac.CondoConnect.dtos.AuthenticationDto;
import com.senac.CondoConnect.dtos.ResponseDto;
import com.senac.CondoConnect.dtos.UsuarioRecord;
import com.senac.CondoConnect.repository.UsuarioRepository;
import com.senac.CondoConnect.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    EmailService emailService;
    
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

 //   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto body){
        UsuarioModel user = this.repository.findByEmail(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
       }

 //   @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsuarioRecord body){
        Optional<UsuarioModel> user = this.repository.findByEmail(body.username());

        if(user.isEmpty()) {
            UsuarioModel newUser = new UsuarioModel();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.username());
            newUser.setApartamentoUsuario(body.apartamentoUsuario());
            newUser.setNomeUsuario(body.nomeUsuario());
            newUser.setRole(RoleName.ROLE_USER);
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            System.out.println(emailService.emailSand(body.username(), "Cadastro realizado no site CondoConnect", "Muito obrigado pelo cadastro em nossa plataforma. Caso você não reconheça o cadastro sujerimos a troca imediata de sua senha."));
            return ResponseEntity.ok(new ResponseDto(newUser.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
