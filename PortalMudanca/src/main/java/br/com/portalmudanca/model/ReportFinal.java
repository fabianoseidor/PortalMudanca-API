package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "REPORT_FINAL")
@SequenceGenerator(name = "seq_report_final", sequenceName = "seq_report_final", allocationSize = 1, initialValue = 1)
public class ReportFinal implements Serializable{

	private static final long serialVersionUID = -3021784576154221465L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_report_final")
	@Column(name = "ID_REPORT_FINAL", nullable = false)
	private Long id_report_final;
	
	@Column(name = "REPORT_FINAL", nullable = false, length = 5000)
	private String report_final;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUD_CAT_PAD"))
	private Mudanca mudanca;

	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}


	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
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
				+ dt_criacao + ", mudanca=" + mudanca + "]";
	}
}
