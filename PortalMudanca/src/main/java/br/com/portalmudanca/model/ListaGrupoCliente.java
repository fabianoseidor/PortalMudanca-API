package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "LISTA_GRUPO_CLIENTE")
@SequenceGenerator(name = "seq_ista_Grupo_Cliente", sequenceName = "seq_ista_Grupo_Cliente", allocationSize = 1, initialValue = 1)
public class ListaGrupoCliente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ista_Grupo_Cliente" )
	@Column(name = "ID_LISTA_GRUPO_CLIENTE")
	private Long id_lista_grupo_cliente;
	
	@ManyToOne(targetEntity = GrupoCliente.class)
	@JoinColumn(name = "id_grupo_cliente", referencedColumnName = "id_grupo_cliente", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_GRUPO_CLIENTE_LISTA"))	
	private GrupoCliente grupoCliente;
		
	@ManyToOne(targetEntity = ClientesAfetados.class)
	@JoinColumn(name = "id_clientes_af", referencedColumnName = "id_clientes_af", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_CLIENTES_LISTA"))	
	private ClientesAfetados clientesAfetados;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_lista_grupo_cliente() {
		return id_lista_grupo_cliente;
	}

	public void setId_lista_grupo_cliente(Long id_lista_grupo_cliente) {
		this.id_lista_grupo_cliente = id_lista_grupo_cliente;
	}

	public GrupoCliente getGrupoCliente() {
		return grupoCliente;
	}

	public void setGrupoCliente(GrupoCliente grupoCliente) {
		this.grupoCliente = grupoCliente;
	}

	public ClientesAfetados getClientesAfetados() {
		return clientesAfetados;
	}

	public void setClientesAfetados(ClientesAfetados clientesAfetados) {
		this.clientesAfetados = clientesAfetados;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public String toString() {
		return "ListaGrupoCliente [id_lista_grupo_cliente=" + id_lista_grupo_cliente + ", grupoCliente=" + grupoCliente
				+ ", clientesAfetados=" + clientesAfetados + ", dt_criacao=" + dt_criacao + "]";
	}
}
