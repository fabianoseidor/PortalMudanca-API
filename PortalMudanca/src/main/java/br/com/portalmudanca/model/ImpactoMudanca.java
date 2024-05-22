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
@Table(name = "IMPACTO_MUDANCA")
@SequenceGenerator(name = "seq_impacto_mudanca", sequenceName = "seq_impacto_mudanca", allocationSize = 1, initialValue = 1)
public class ImpactoMudanca implements Serializable{

	private static final long serialVersionUID = -3306711753055965599L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_impacto_mudanca")
	@Column(name = "ID_IMPACTO_MUDANCA")	
	private Long id_impacto_mudanca;
	
	@Column(name = "IMPACTO_MUDANCA", nullable = false, length = 200)
	private String impacto_mudanca;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@Column(name = "OBS", length = 5000)
	private String obs;
	
	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}

	public Long getId_impacto_mudanca() {
		return id_impacto_mudanca;
	}

	public void setId_impacto_mudanca(Long id_impacto_mudanca) {
		this.id_impacto_mudanca = id_impacto_mudanca;
	}

	public String getImpacto_mudanca() {
		return impacto_mudanca;
	}

	public void setImpacto_mudanca(String impacto_mudanca) {
		this.impacto_mudanca = impacto_mudanca;
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
		return Objects.hash(id_impacto_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImpactoMudanca other = (ImpactoMudanca) obj;
		return Objects.equals(id_impacto_mudanca, other.id_impacto_mudanca);
	}

	@Override
	public String toString() {
		return "ImpactoMudanca [id_impacto_mudanca=" + id_impacto_mudanca + ", impacto_mudanca=" + impacto_mudanca
				+ ", dt_criacao=" + dt_criacao + ", obs=" + obs + "]";
	}

}
