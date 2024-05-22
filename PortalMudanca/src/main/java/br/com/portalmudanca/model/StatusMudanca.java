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

@Entity
@SequenceGenerator(name = "seq_status_mudanca", sequenceName = "seq_status_mudanca", allocationSize = 1, initialValue = 1)
public class StatusMudanca implements Serializable{

	private static final long serialVersionUID = -901819689699713052L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_status_mudanca")
	@Column(name = "ID_STATUS")
	private Long id_status;
	
	@Column(name = "STATUS", nullable = false, length = 45)
	private String status;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@Column(name = "obs", length = 5000)
	private String obs;	
	
	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}

	public Long getId_status() {
		return id_status;
	}

	public void setId_status(Long id_status) {
		this.id_status = id_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return Objects.hash(id_status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusMudanca other = (StatusMudanca) obj;
		return Objects.equals(id_status, other.id_status);
	}

	@Override
	public String toString() {
		return "StatusMudanca [id_status=" + id_status + ", status=" + status + ", dt_criacao=" + dt_criacao + ", obs="
				+ obs + "]";
	}

}
