package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLIENTES_AFETADOS")
@SequenceGenerator(name = "seq_clientes_afetados", sequenceName = "seq_clientes_afetados", allocationSize = 1, initialValue = 1)
public class ClientesAfetados implements Serializable{

	private static final long serialVersionUID = -954884151668286477L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_clientes_afetados")
	@Column(name = "ID_CLIENTES_AF")
	private Long id_clientes_af;
	
	@Column(name = "ID_CLIENTE_PORTAL", nullable = false)	
	private Long id_cliente_portal;
	
	@Column(name = "NOME_CLIENTE", nullable = false, length = 100)
	private String nome_cliente;

	@Column(name = "ALIAS", length = 100)
	private String alias;

	@Column(name = "CICLO_UPDATE", length = 100)
	private String ciclo_update;

	@JsonIgnore
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	/* Esta eh uma forma de realizar uma anotacao JPA de N para N
	@OneToMany(fetch = FetchType.LAZY)// Anotacao de 1 para muintos
	// @JoinTable ==> são usadas para mapear uma relação ManyToMany entre duas entidades.
	@JoinTable(name = "MUDANCA_CLIENTES_AFETADOS", // Nome da tabela intermediaria de 1 para Muintos
	           // Inicio da referencia da tabela "CLIENTES_AFETADOS" para a tabela "MUDANCA_CLIENTES_AFETADOS"
	           uniqueConstraints = @UniqueConstraint(columnNames = { "ID_CLIENTES_AF", "ID_MUDANCA" }, name = "uniq_MUD_CLIE_AFE" ),
	           joinColumns = @JoinColumn(name = "ID_CLIENTES_AF", referencedColumnName = "ID_CLIENTES_AF", table = "CLIENTES_AFETADOS", unique = false, foreignKey = @ForeignKey(name = "fk_MUD_CLIENTES_AFE", value = ConstraintMode.CONSTRAINT) ),
	           // fim referencia da tabela "MUDANCA_CLIENTES_AFETADOS" apontando para a tabela "CLIENTES_AFETADOS"
	           
	           // Inicio da referencia da tabela "MUDANCA" para a tabela "MUDANCA_CLIENTES_AFETADOS"
	           inverseJoinColumns = @JoinColumn(name = "ID_MUDANCA", unique = false, referencedColumnName = "ID_MUDANCA", table = "MUDANCA", foreignKey = @ForeignKey(name = "fk_CLIE_AFE_MUD", value = ConstraintMode.CONSTRAINT))
	)
	private List<Mudanca> mudancas;
	*/
	

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCiclo_update() {
		return ciclo_update;
	}

	public void setCiclo_update(String ciclo_update) {
		this.ciclo_update = ciclo_update;
	}

	public Long getId_clientes_af() {
		return id_clientes_af;
	}

	public void setId_clientes_af(Long id_clientes_af) {
		this.id_clientes_af = id_clientes_af;
	}

	public Long getId_cliente_portal() {
		return id_cliente_portal;
	}

	public void setId_cliente_portal(Long id_cliente_portal) {
		this.id_cliente_portal = id_cliente_portal;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_cliente_portal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientesAfetados other = (ClientesAfetados) obj;
		return Objects.equals(id_cliente_portal, other.id_cliente_portal);
	}

	@Override
	public String toString() {
		return "ClientesAfetados [id_clientes_af=" + id_clientes_af + ", id_cliente_portal=" + id_cliente_portal
				+ ", nome_cliente=" + nome_cliente + ", dt_criacao=" + dt_criacao + "]";
	}


}
