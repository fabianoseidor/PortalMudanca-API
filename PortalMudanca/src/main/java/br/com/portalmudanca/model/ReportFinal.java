package br.com.portalmudanca.model;

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
@Table(name = "REPORT_FINAL")
@SequenceGenerator(name = "seq_report_final", sequenceName = "seq_report_final", allocationSize = 1, initialValue = 1)
public class ReportFinal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_report_final")
	@Column(name = "ID_REPORT_FINAL", nullable = false)
	private Long id_report_final;
	
	@Column(name = "REPORT_FINAL", nullable = false, length = 5000)
	private String report_final;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;


	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}


	public Long getId_report_final() {
		return id_report_final;
	}


	public void setId_report_final(Long id_report_final) {
		this.id_report_final = id_report_final;
	}


	public String getReport_final() {
		return report_final;
	}


	public void setReport_final(String report_final) {
		this.report_final = report_final;
	}


	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}


	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id_report_final);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportFinal other = (ReportFinal) obj;
		return Objects.equals(id_report_final, other.id_report_final);
	}


	@Override
	public String toString() {
		return "ReportFinal [id_report_final=" + id_report_final + ", report_final=" + report_final + ", dt_criacao="
				+ dt_criacao + "]";
	}

    
	
}
