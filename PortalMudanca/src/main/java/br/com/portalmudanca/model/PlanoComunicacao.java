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
@Table(name = "PLANO_COMUNICACAO")
@SequenceGenerator(name = "seq_plano_comunicacao", sequenceName = "seq_plano_comunicacao", allocationSize = 1, initialValue = 1)
public class PlanoComunicacao implements Serializable{

	private static final long serialVersionUID = -280457124494665261L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_plano_comunicacao")
	@Column(name = "ID_PLANO_COMUNICACAO")
	private Long id_plano_comunicacao;
	
	@Column(name = "NOME_CONTATO", length = 200 ,nullable = false)
	private String nome_contato;
	
	@Column(name = "EMAIL", length = 200 ,nullable = false)
	private String email;
	
	@Column(name = "EMPRESA", length = 200 ,nullable = false)
	private String empresa;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = false, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_PLANO_COMUNICACAO_MUDANCA"))
	private Mudanca mudanca;

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_plano_comunicacao() {
		return id_plano_comunicacao;
	}

	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}

	public void setId_plano_comunicacao(Long id_plano_comunicacao) {
		this.id_plano_comunicacao = id_plano_comunicacao;
	}

	public String getNome_contato() {
		return nome_contato;
	}

	public void setNome_contato(String nome_contato) {
		this.nome_contato = nome_contato;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id_plano_comunicacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanoComunicacao other = (PlanoComunicacao) obj;
		return Objects.equals(id_plano_comunicacao, other.id_plano_comunicacao);
	}

	@Override
	public String toString() {
		return "PlanoComunicacao [id_plano_comunicacao=" + id_plano_comunicacao + ", nome_contato=" + nome_contato
				+ ", email=" + email + ", empresa=" + empresa + ", dt_criacao=" + dt_criacao + ", mudanca=" + mudanca
				+ "]";
	}


}
