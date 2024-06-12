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
@Table(name = "EXECUTOR_CATEGORIA_PADRAO")
@SequenceGenerator(name = "seq_exec_categ_padrao", sequenceName = "seq_exec_categ_padrao", allocationSize = 1, initialValue = 1)
public class ExecutorCategoriaPadrao implements Serializable{

	private static final long serialVersionUID = 3579738666611411396L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_exec_categ_padrao" )
	@Column(name = "ID_EXEC_CATEG_PADRAO")
	private Long id_exec_categ_padrao;

	@ManyToOne(targetEntity = CategoriaPadrao.class)
	@JoinColumn(name = "id_categoria_padrao", referencedColumnName = "id_categoria_padrao", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CAT_PAD_APROV"))	
	private CategoriaPadrao categoria_padrao;
	
	@ManyToOne(targetEntity = ResponsavelAtividade.class)
	@JoinColumn(name = "id_responsavel_atividade", referencedColumnName = "id_responsavel_atividade", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_APROV_CAT_PAD"))	
	private ResponsavelAtividade responsavel_atividade;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_exec_categ_padrao() {
		return id_exec_categ_padrao;
	}

	public void setId_exec_categ_padrao(Long id_exec_categ_padrao) {
		this.id_exec_categ_padrao = id_exec_categ_padrao;
	}

	public CategoriaPadrao getCategoria_padrao() {
		return categoria_padrao;
	}

	public void setCategoria_padrao(CategoriaPadrao categoria_padrao) {
		this.categoria_padrao = categoria_padrao;
	}

	public ResponsavelAtividade getResponsavel_atividade() {
		return responsavel_atividade;
	}

	public void setResponsavel_atividade(ResponsavelAtividade responsavel_atividade) {
		this.responsavel_atividade = responsavel_atividade;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_exec_categ_padrao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecutorCategoriaPadrao other = (ExecutorCategoriaPadrao) obj;
		return Objects.equals(id_exec_categ_padrao, other.id_exec_categ_padrao);
	}

	@Override
	public String toString() {
		return "ExecutorCategoriaPadrao [id_exec_categ_padrao=" + id_exec_categ_padrao + ", categoria_padrao="
				+ categoria_padrao + ", responsavel_atividade=" + responsavel_atividade + ", dt_criacao=" + dt_criacao
				+ "]";
	}
}
