package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_MUDANCA")
@SequenceGenerator(name = "seq_tipo_mudanca", sequenceName = "seq_tipo_mudanca", allocationSize = 1, initialValue = 1)
public class TipoMudanca implements Serializable{


	private static final long serialVersionUID = -5699366290086467554L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_mudanca")
	@Column(name = "ID_TIPO_MUDANCA")
	private Long id_tipo_mudanca;
	
	@Column(name = "TIPO_MUDANCA", nullable = false)
	private String tipo_mudanca;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@Column(name = "OBS")
	private String obs;
	

	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}



	public Long getId_tipo_mudanca() {
		return id_tipo_mudanca;
	}


	public void setId_tipo_mudanca(Long id_tipo_mudanca) {
		this.id_tipo_mudanca = id_tipo_mudanca;
	}


	public String getTipo_mudanca() {
		return tipo_mudanca;
	}


	public void setTipo_mudanca(String tipo_mudanca) {
		this.tipo_mudanca = tipo_mudanca;
	}


	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}


	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_tipo_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoMudanca other = (TipoMudanca) obj;
		return Objects.equals(id_tipo_mudanca, other.id_tipo_mudanca);
	}

	@Override
	public String toString() {
		return "TipoMudanca [id_tipo_mudanca=" + id_tipo_mudanca + ", tipo_mudanca=" + tipo_mudanca + ", dt_criacao="
				+ dt_criacao + ", obs=" + obs + "]";
	}

}
