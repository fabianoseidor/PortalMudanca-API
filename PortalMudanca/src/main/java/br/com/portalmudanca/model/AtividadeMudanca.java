package br.com.portalmudanca.model;

import java.io.Serializable;
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
@Table(name = "ATIVIDADE_MUDANCA")
@SequenceGenerator(name = "seq_atividade_mudanca", sequenceName = "seq_atividade_mudanca", allocationSize = 1, initialValue = 1)

public class AtividadeMudanca implements Serializable{

	private static final long serialVersionUID = 9118672086133844820L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atividade_mudanca")
	@Column(name = "ID_ATIVIDADE_MUDANCA")
	private Long id_atividade_mudanca;
	
	@Column(name = "ATIVIDADE_MUDANCA", length = 5000, nullable = false)
	private String atividade_mudanca;
	
	@Column(name = "DURACAO", columnDefinition = "TIME", nullable = false)
	private LocalTime duracao;

	@ManyToOne( targetEntity = ResponsavelAtividade.class )
	@JoinColumn(name = "id_responsavel_atividade", nullable = false, referencedColumnName = "id_responsavel_atividade", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ATIV_MUD_RESP_ATIV"))
	private ResponsavelAtividade responsavelAtividade;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ATIV_MUD"))
	private Mudanca mudanca;

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

	@Override
	public int hashCode() {
		return Objects.hash(id_atividade_mudanca);
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
