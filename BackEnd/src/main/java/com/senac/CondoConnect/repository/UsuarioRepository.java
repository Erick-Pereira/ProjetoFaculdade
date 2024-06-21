package com.senac.CondoConnect.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senac.CondoConnect.Model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID>{
	
	@Query(nativeQuery = true, value = "Select * from usuario where username = :Email")
    Optional<UsuarioModel> findByEmail (String Email);
	
	@Query(nativeQuery = true, value = "Select * from usuario where id = :id_user")
    Optional<UsuarioModel> findById (int id_user);
}
