package com.senac.CondoConnect.Model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

    @Entity
    @Table(name="Achado")
public class AchadoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable= false)
	private String descricaoAchado;
	@Column(nullable= false)
	private String tituloAchado;
    @Column(nullable = false)
    private LocalDate data;
	@ManyToOne
	private UsuarioModel usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricaoAchado() {
        return descricaoAchado;
    }

    public void setDescricaoAchado(String descricaoAchado) {
        this.descricaoAchado = descricaoAchado;
    }

    public String getTituloAchado() {
        return tituloAchado;
    }

    public void setTituloAchado(String tituloAchado) {
        this.tituloAchado = tituloAchado;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public LocalDate  getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


	}

