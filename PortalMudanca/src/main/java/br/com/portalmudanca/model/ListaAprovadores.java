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
@Table(name = "LISTA_APROVADORES")
@SequenceGenerator(name = "seq_lista_aprovadores", sequenceName = "seq_lista_aprovadores", allocationSize = 1, initialValue = 1)
public class ListaAprovadores implements Serializable{

	private static final long serialVersionUID = 1435008651713278470L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lista_aprovadores" )
	@Column(name = "ID_LISTA_APROVADORES")
	private Long id_lista_aprovadores;

	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", referencedColumnName = "id_mudanca", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_APROVADORES_MUDANCA"))	
	private Mudanca mudanca;
	
	@ManyToOne(targetEntity = Aprovadores.class)
	@JoinColumn(name = "id_aprovadores", referencedColumnName = "id_aprovadores", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDA_APROV"))	
	private Aprovadores aprovadores;

	@Column(name = "STATUS_APROVACAO", nullable = false)
	private Boolean statusAprovacao;

	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_lista_aprovadores() {
		return id_lista_aprovadores;
	}

	public void setId_lista_aprovadores(Long id_lista_aprovadores) {
		this.id_lista_aprovadores = id_lista_aprovadores;
	}

	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}

	public Aprovadores getAprovadores() {
		return aprovadores;
	}

	public void setAprovadores(Aprovadores aprovadores) {
		this.aprovadores = aprovadores;
	}

	public Boolean getStatusAprovacao() {
		return statusAprovacao;
	}

	public void setStatusAprovacao(Boolean statusAprovacao) {
		this.statusAprovacao = statusAprovacao;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_lista_aprovadores);
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
		return Objects.equals(id_lista_aprovadores, other.id_lista_aprovadores);
	}

	@Override
	public String toString() {
		return "ListaAprovadores [id_lista_aprovadores=" + id_lista_aprovadores + ", mudanca=" + mudanca
				+ ", aprovadores=" + aprovadores + ", statusAprovacao=" + statusAprovacao + ", dt_criacao=" + dt_criacao
				+ "]";
	}
}
