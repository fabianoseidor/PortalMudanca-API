package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "MUDANCA_CLIENTES_AFETADOS")

public class MudancaClientesAfetados implements Serializable{

	private static final long serialVersionUID = -8032979711028967741L;
	
	@Id
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", referencedColumnName = "id_mudanca", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CLIE_AFET_MUD"))	
	private Long id_mudanca;
	
	@Id
	@ManyToOne(targetEntity = ClientesAfetados.class)
	@JoinColumn(name = "id_clientes_af", referencedColumnName = "id_clientes_af", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CLIENTES_AFETADOS"))	
	private Long id_clientes_af;
	
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

	public Long getId_clientes_af() {
		return id_clientes_af;
	}

	public void setId_clientes_af(Long id_clientes_af) {
		this.id_clientes_af = id_clientes_af;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_clientes_af, id_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MudancaClientesAfetados other = (MudancaClientesAfetados) obj;
		return Objects.equals(id_clientes_af, other.id_clientes_af) && Objects.equals(id_mudanca, other.id_mudanca);
	}

	@Override
	public String toString() {
		return "MudancaClientesAfetados [id_mudanca=" + id_mudanca + ", id_clientes_af=" + id_clientes_af
				+ ", dt_criacao=" + dt_criacao + "]";
	}
	
}
