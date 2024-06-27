package com.senac.CondoConnect.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="Moradores")
public class UsuarioModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private String nomeUsuario;
	@Column(nullable=false)
	private String apartamentoUsuario;
	@Column(nullable= false, unique=true)
	private String emailUsuario;
	@Column(nullable= false)
	private String senhaUsuario;
	@OneToMany
	private List<ComunicadoModel> comunicadolist;
	@OneToMany
	private List<ReservaModel> reservalist;
	@OneToMany
	private List<AchadoModel> achadolist;

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getApartamentoUsuario() {
		return apartamentoUsuario;
	}
	public void setApartamentoUsuario(String apartamentoUsuario) {
		this.apartamentoUsuario = apartamentoUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getSenhaUsuario() {
		return senhaUsuario;
	}
	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}
	public List<ComunicadoModel> getComunicadolist() {
		return comunicadolist;
	}
	public void setComunicadolist(List<ComunicadoModel> comunicadolist) {
		this.comunicadolist = comunicadolist;
	}
	public List<ReservaModel> getReservalist() {
		return reservalist;
	}
	public void setReservalist(List<ReservaModel> reservalist) {
		this.reservalist = reservalist;
	}

    public List<AchadoModel> getAchadolist() {
        return achadolist;
    }
    public void setAchadolist(List<AchadoModel> achadolist) {
        this.achadolist = achadolist;
    }	

}