package com.senac.CondoConnect.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senac.CondoConnect.Model.ReservaModel;

public interface ReservaRepository extends JpaRepository<ReservaModel, UUID>{
	
	@Query(nativeQuery = true, value = "select * from reserva where usuario_id = :id")
    List<ReservaModel> findByUser(int id);
	
	@Query(nativeQuery = true, value = "select * from reserva where id = :id_re")
	Optional<ReservaModel> findById(int id_re);

	@Query(nativeQuery = true, value = "select * from reserva where month(data) = :mes")
	List<ReservaModel> findByMes(int mes);
}