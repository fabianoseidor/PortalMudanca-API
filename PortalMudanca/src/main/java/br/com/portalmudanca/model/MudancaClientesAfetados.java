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
@Table(name = "MUDANCA_CLIENTES_AFETADOS")
@SequenceGenerator(name = "seq_mud_cli_afetados", sequenceName = "seq_mud_cli_afetados", allocationSize = 1, initialValue = 1)
public class MudancaClientesAfetados implements Serializable{

	private static final long serialVersionUID = -8032979711028967741L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mud_cli_afetados" )
	@Column(name = "ID_MUD_CLI_AFETADOS")
	private Long id_mud_cli_afetados;
	
	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", referencedColumnName = "id_mudanca", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CLIE_AFET_MUD"))	
	private Mudanca mudanca;
		
	@ManyToOne(targetEntity = ClientesAfetados.class)
	@JoinColumn(name = "id_clientes_af", referencedColumnName = "id_clientes_af", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CLIENTES_AFETADOS"))	
	private ClientesAfetados clientesAfetados;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_mud_cli_afetados() {
		return id_mud_cli_afetados;
	}

	public void setId_mud_cli_afetados(Long id_mud_cli_afetados) {
		this.id_mud_cli_afetados = id_mud_cli_afetados;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}

	public ClientesAfetados getClientesAfetados() {
		return clientesAfetados;
	}

	public void setClientesAfetados(ClientesAfetados clientesAfetados) {
		this.clientesAfetados = clientesAfetados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_mud_cli_afetados);
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
		return Objects.equals(id_mud_cli_afetados, other.id_mud_cli_afetados);
	}

	@Override
	public String toString() {
		return "MudancaClientesAfetados [id_mud_cli_afetados=" + id_mud_cli_afetados + ", mudanca=" + mudanca
				+ ", clientesAfetados=" + clientesAfetados + ", dt_criacao=" + dt_criacao + "]";
	}


	
}
