package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CATEGORIA_PADRAO")
@SequenceGenerator(name = "seq_categoria_padrao", sequenceName = "seq_categoria_padrao", allocationSize = 1, initialValue = 1)
public class CategoriaPadrao implements Serializable{

	private static final long serialVersionUID = 5554328306966819593L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_padrao")
	@Column(name = "ID_CATEGORIA_PADRAO")
	private Long id_categoria_padrao;
	
	@Column(name = "CATEGORIA_PADRAO",length = 200, nullable = false)
	private String categoria_padrao;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@Column(name = "OBS", length = 200)
	private String obs;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CATEGORIA_PADRAO_MUDANCA"))
	private Mudanca mudanca;	

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


	public Long getId_categoria_padrao() {
		return id_categoria_padrao;
	}

	public void setId_categoria_padrao(Long id_categoria_padrao) {
		this.id_categoria_padrao = id_categoria_padrao;
	}

	public String getCategoria_padrao() {
		return categoria_padrao;
	}

	public void setCategoria_padrao(String categoria_padrao) {
		this.categoria_padrao = categoria_padrao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_categoria_padrao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaPadrao other = (CategoriaPadrao) obj;
		return Objects.equals(id_categoria_padrao, other.id_categoria_padrao);
	}

	@Override
	public String toString() {
		return "CategoriaPadrao [id_categoria_padrao=" + id_categoria_padrao + ", categoria_padrao=" + categoria_padrao
				+ ", dt_criacao=" + dt_criacao + ", obs=" + obs + "]";
	}
	
	
	
}
