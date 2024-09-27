package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "GRUPO_CLIENTE")
@SequenceGenerator(name = "seq_grupo_cliente", sequenceName = "seq_grupo_cliente", allocationSize = 1, initialValue = 1)
public class GrupoCliente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grupo_cliente")
	@Column(name = "ID_GRUPO_CLIENTE")
	private Long id_grupo_cliente;
	
	@Column(name = "NOME_GRUPO", nullable = false, length = 100)
	private String nome_grupo;
	
	@Column(name = "DSC_GRUPO", nullable = false, length = 100)
	private String dsc_grupo;

	
	@JsonIgnore
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }
    
	@PreUpdate
    public void preUpdate() {
		dt_criacao =  LocalDateTime.now();
    }

	public Long getId_grupo_cliente() {
		return id_grupo_cliente;
	}

	public void setId_grupo_cliente(Long id_grupo_cliente) {
		this.id_grupo_cliente = id_grupo_cliente;
	}

	public String getNome_grupo() {
		return nome_grupo;
	}

	public void setNome_grupo(String nome_grupo) {
		this.nome_grupo = nome_grupo;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public String getDsc_grupo() {
		return dsc_grupo;
	}

	public void setDsc_grupo(String dsc_grupo) {
		this.dsc_grupo = dsc_grupo;
	}

	@Override
	public String toString() {
		return "GrupoCliente [id_grupo_cliente=" + id_grupo_cliente + ", nome_grupo=" + nome_grupo + ", dsc_grupo="
				+ dsc_grupo + ", dt_criacao=" + dt_criacao + "]";
	}	
}
