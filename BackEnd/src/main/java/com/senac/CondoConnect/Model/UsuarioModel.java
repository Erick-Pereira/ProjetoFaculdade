package com.senac.CondoConnect.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.senac.CondoConnect.Enum.RoleName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Usuario")
public class UsuarioModel implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private String nomeUsuario;
	@Column(nullable=false)
	private String apartamentoUsuario;
	@Column(nullable= false, unique=true)
	private String username;
	@Column(nullable= false)
	private String password;
	@OneToMany
	private List<ComunicadoModel> comunicadolist;
	@OneToMany
	private List<ReservaModel> reservalist;
	@OneToMany
	private List<AchadoModel> achadolist;
	@Enumerated(EnumType.STRING)
	@Column
    private RoleName role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == RoleName.ROLE_ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApartamentoUsuario() {
		return apartamentoUsuario;
	}
	public void setApartamentoUsuario(String apartamentoUsuario) {
		this.apartamentoUsuario = apartamentoUsuario;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RoleName getRole() {
		return role;
	}
	public void setRole(RoleName role) {
		this.role = role;
	}
	
}