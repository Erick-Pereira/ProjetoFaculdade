package com.senac.CondoConnect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.CondoConnect.Model.ReservaModel;
import com.senac.CondoConnect.repository.ReservaRepository;
@Service
public class ReservaService {

	 @Autowired
	    ReservaRepository reservaRepository;
	    public List<ReservaModel> findAll(){ // vai retornar uma lista de Posts
	        return reservaRepository.findAll();
	    }
	    public Optional<ReservaModel> findById(int id){ // vai retornar um unico   // Post passando o id
	        return reservaRepository.findById(id);
	    }
	    public ReservaModel save(ReservaModel post){ // vai salvar um Post no Banco
	        return reservaRepository.save(post);
	    }
	    public void delete(ReservaModel post){ // vai excluir um post tem menu de contexto
	    	reservaRepository.delete(post);
	    }
	    public List<ReservaModel> findByUser(int id){ // vai retornar uma lista de Posts
	        return reservaRepository.findByUser(id);
	    }
	    public List<ReservaModel> findByMes(int mes){ // vai retornar uma lista de Posts
	        return reservaRepository.findByMes(mes);
	    }
}