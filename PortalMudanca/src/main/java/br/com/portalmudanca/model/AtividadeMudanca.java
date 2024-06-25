package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "ATIVIDADE_MUDANCA")
@SequenceGenerator(name = "seq_atividade_mudanca", sequenceName = "seq_atividade_mudanca", allocationSize = 1, initialValue = 1)
public class AtividadeMudanca implements Serializable{

	private static final long serialVersionUID = 9118672086133844820L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atividade_mudanca")
	@Column(name = "ID_ATIVIDADE_MUDANCA")
	private Long id_atividade_mudanca;

	@Column(name = "TITULO_ATIVIDADE_MUDANCA", length = 100, nullable = false)
	private String titulo_atividade_mudanca;
	
	@Column(name = "ATIVIDADE_MUDANCA", length = 5000, nullable = false)
	private String atividade_mudanca;
	
	@Column(name = "DT_TAREFA", columnDefinition = "DATE", nullable = false)
	private LocalDate dt_tarefa;
	
	@Column(name = "DURACAO", columnDefinition = "TIME", nullable = false)
	private LocalTime duracao;

	@Column(name = "DT_INICIO_TAREFA", columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_inicio_tarefa;
	
	@Column(name = "DT_FINAL_TAREFA", columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_final_tarefa;
		
	@Column(name = "REPORT_FINAL", length = 5000)
	private String report_final;
	
	
	@ManyToOne( targetEntity = ResponsavelAtividade.class )
	@JoinColumn(name = "id_responsavel_atividade", nullable = false, referencedColumnName = "id_responsavel_atividade", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ATIV_MUD_RESP_ATIV"))
	private ResponsavelAtividade responsavelAtividade;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ATIV_MUD"))
	private Mudanca mudanca;
	
	@ManyToOne(targetEntity = StatusAtividade.class)
	@JoinColumn(name = "id_status_atividade", referencedColumnName = "id_status_atividade", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_STATUS_ATIVIDADE"))
	private StatusAtividade statusAtividade;
	
	public LocalDateTime getDt_inicio_tarefa() {
		return dt_inicio_tarefa;
	}

	public void setDt_inicio_tarefa(LocalDateTime dt_inicio_tarefa) {
		this.dt_inicio_tarefa = dt_inicio_tarefa;
	}

	public LocalDateTime getDt_final_tarefa() {
		return dt_final_tarefa;
	}

	public void setDt_final_tarefa(LocalDateTime dt_final_tarefa) {
		this.dt_final_tarefa = dt_final_tarefa;
	}

	public String getReport_final() {
		return report_final;
	}

	public void setReport_final(String report_final) {
		this.report_final = report_final;
	}

	public StatusAtividade getStatusAtividade() {
		return statusAtividade;
	}

	public void setStatusAtividade(StatusAtividade statusAtividade) {
		this.statusAtividade = statusAtividade;
	}

	public Long getId_atividade_mudanca() {
		return id_atividade_mudanca;
	}

	public void setId_atividade_mudanca(Long id_atividade_mudanca) {
		this.id_atividade_mudanca = id_atividade_mudanca;
	}

	public ResponsavelAtividade getResponsavelAtividade() {
		return responsavelAtividade;
	}

	public void setResponsavelAtividade(ResponsavelAtividade responsavelAtividade) {
		this.responsavelAtividade = responsavelAtividade;
	}

	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}

	public String getAtividade_mudanca() {
		return atividade_mudanca;
	}

	public void setAtividade_mudanca(String atividade_mudanca) {
		this.atividade_mudanca = atividade_mudanca;
	}

	public LocalTime getDuracao() {
		return duracao;
	}

	public void setDuracao(LocalTime duracao) {
		this.duracao = duracao;
	}

	public String getTitulo_atividade_mudanca() {
		return titulo_atividade_mudanca;
	}

	public void setTitulo_atividade_mudanca(String titulo_atividade_mudanca) {
		this.titulo_atividade_mudanca = titulo_atividade_mudanca;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_atividade_mudanca);
	}

	public LocalDate getDt_tarefa() {
		return dt_tarefa;
	}

	public void setDt_tarefa(LocalDate dt_tarefa) {
		this.dt_tarefa = dt_tarefa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtividadeMudanca other = (AtividadeMudanca) obj;
		return Objects.equals(id_atividade_mudanca, other.id_atividade_mudanca);
	}

	@Override
	public String toString() {
		return "AtividadeMudanca [id_atividade_mudanca=" + id_atividade_mudanca + ", atividade_mudanca="
				+ atividade_mudanca + ", duracao=" + duracao + ", responsavelAtividade=" + responsavelAtividade
				+ ", mudanca=" + mudanca + "]";
	}

}
