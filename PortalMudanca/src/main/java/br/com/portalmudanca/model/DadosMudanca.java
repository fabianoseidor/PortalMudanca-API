package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@SequenceGenerator(name = "seq_dados_mudanca", sequenceName = "seq_dados_mudanca", allocationSize = 1, initialValue = 1)
@Table(name = "DADOS_MUDANCA")
public class DadosMudanca implements Serializable{


	private static final long serialVersionUID = 1194808759928751453L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dados_mudanca")
	@Column(name = "ID_DADOS_MUDANCA")
	private Long id_dados_mudanca;
	
	@Column(name = "DT_INICIO", columnDefinition = "DATE")
	private LocalDate dt_inicio;

	@Column(name = "HR_INICIO", columnDefinition = "TIME")
	private LocalTime hr_inicio;
	
	@Column(name = "DT_FINAL", columnDefinition = "DATE")
	private LocalDate dt_final;
	
	@Column(name = "HR_FINAL", columnDefinition = "TIME")
	private LocalTime hr_final;
	
	@Column(name = "DSC_MUDANCA", length = 5000)
	private String dsc_mudanca;
	
	@Column(name = "JUSTIFICATIVA_MUDANCA", length = 5000)
	private String justificativa_mudanca;

	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = false, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_DADOS_MUDANCA_MUD"))
	private Mudanca mudanca;

	
	public Long getId_dados_mudanca() {
		return id_dados_mudanca;
	}

	public void setId_dados_mudanca(Long id_dados_mudanca) {
		this.id_dados_mudanca = id_dados_mudanca;
	}

	public LocalDate getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(LocalDate dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public LocalTime getHr_inicio() {
		return hr_inicio;
	}

	public void setHr_inicio(LocalTime hr_inicio) {
		this.hr_inicio = hr_inicio;
	}

	public LocalDate getDt_final() {
		return dt_final;
	}

	public void setDt_final(LocalDate dt_final) {
		this.dt_final = dt_final;
	}

	public LocalTime getHr_final() {
		return hr_final;
	}

	public void setHr_final(LocalTime hr_final) {
		this.hr_final = hr_final;
	}

	public String getDsc_mudanca() {
		return dsc_mudanca;
	}

	public void setDsc_mudanca(String dsc_mudanca) {
		this.dsc_mudanca = dsc_mudanca;
	}

	public String getJustificativa_mudanca() {
		return justificativa_mudanca;
	}

	public void setJustificativa_mudanca(String justificativa_mudanca) {
		this.justificativa_mudanca = justificativa_mudanca;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_dados_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosMudanca other = (DadosMudanca) obj;
		return Objects.equals(id_dados_mudanca, other.id_dados_mudanca);
	}

	@Override
	public String toString() {
		return "DadosMudanca [id_dados_mudanca=" + id_dados_mudanca + ", dt_inicio=" + dt_inicio + ", hr_inicio="
				+ hr_inicio + ", dt_final=" + dt_final + ", hr_final=" + hr_final + ", dsc_mudanca=" + dsc_mudanca
				+ ", justificativa_mudanca=" + justificativa_mudanca + "]";
	}
	
}
