package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CRITICIDADE")
@SequenceGenerator(name = "seq_criticidade", sequenceName = "seq_criticidade", allocationSize = 1, initialValue = 1)
public class Criticidade implements Serializable{

	private static final long serialVersionUID = 4618126659048867283L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_criticidade")
	@Column(name = "ID_CRITICIDADE")
    private Long id_criticidade;
	
	@Column(name = "CRITICIDADE", length = 200, nullable = false)
    private String criticidade;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dt_criacao;
	
	@Column(name = "OBS", length = 5000)
    private String obs;
	
	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}

	
	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}


	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	public Long getId_criticidade() {
		return id_criticidade;
	}

	public void setId_criticidade(Long id_criticidade) {
		this.id_criticidade = id_criticidade;
	}

	public String getCriticidade() {
		return criticidade;
	}

	public void setCriticidade(String criticidade) {
		this.criticidade = criticidade;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_criticidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criticidade other = (Criticidade) obj;
		return Objects.equals(id_criticidade, other.id_criticidade);
	}

	@Override
	public String toString() {
		return "Criticidade [id_criticidade=" + id_criticidade + ", criticidade=" + criticidade + ", dt_criacao="
				+ dt_criacao + ", obs=" + obs + "]";
	}	
}
