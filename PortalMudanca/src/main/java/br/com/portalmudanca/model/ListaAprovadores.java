package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "LISTA_APROVADORES")

public class ListaAprovadores implements Serializable{

	private static final long serialVersionUID = 1435008651713278470L;

	@Id
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", referencedColumnName = "id_mudanca", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_APROVADORES_MUDANCA"))	
	private Long id_mudanca;
	
	@Id
	@ManyToOne(targetEntity = Aprovadores.class)
	@JoinColumn(name = "id_aprovadores", referencedColumnName = "id_aprovadores", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDA_APROV"))	
	private Long id_aprovadores;
	
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

	@PreUpdate
    public void preUpdate() {
		dt_criacao =  LocalDateTime.now();
    }

	public Long getId_mudanca() {
		return id_mudanca;
	}


	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
	}


	public Long getId_aprovadores() {
		return id_aprovadores;
	}


	public void setId_aprovadores(Long id_aprovadores) {
		this.id_aprovadores = id_aprovadores;
	}


	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}


	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id_aprovadores, id_mudanca);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListaAprovadores other = (ListaAprovadores) obj;
		return Objects.equals(id_aprovadores, other.id_aprovadores) && Objects.equals(id_mudanca, other.id_mudanca);
	}


	@Override
	public String toString() {
		return "ListaAprovadores [id_mudanca=" + id_mudanca + ", id_aprovadores=" + id_aprovadores + ", dt_criacao="
				+ dt_criacao + "]";
	}

}
