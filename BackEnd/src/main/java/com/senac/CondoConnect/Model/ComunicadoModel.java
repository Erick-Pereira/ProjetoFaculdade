package com.senac.CondoConnect.Model;


import jakarta.persistence.*;

@Entity
@Table(name="Comunicado")
public class ComunicadoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable= false)
	private String tituloComunicado;
	@Column(nullable= false)
	private String descricaoComunicado;
	@ManyToOne
	private UsuarioModel usuario;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String gettituloComunicado() {
		return tituloComunicado;
	}
	public void settituloComunicado(String tituloComunicado) {
		this.tituloComunicado = tituloComunicado;
	}
	public String getdescricaoComunicado() {
		return descricaoComunicado;
	}
	public void setdescricaoComunicado(String descricaoComunicado) {
		this.descricaoComunicado = descricaoComunicado;
	}
	public UsuarioModel getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}
	
	
}
