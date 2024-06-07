package com.senac.CondoConnect.Model;

import jakarta.persistence.*;
import java.time.LocalDate; // Importe LocalDate

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

    @Column(nullable= false)
    private LocalDate data; 
    
    @ManyToOne
    private UsuarioModel usuario;
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTituloComunicado() {
        return tituloComunicado;
    }
    public void setTituloComunicado(String tituloComunicado) {
        this.tituloComunicado = tituloComunicado;
    }
    public String getDescricaoComunicado() {
        return descricaoComunicado;
    }
    public void setDescricaoComunicado(String descricaoComunicado) {
        this.descricaoComunicado = descricaoComunicado;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public UsuarioModel getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
