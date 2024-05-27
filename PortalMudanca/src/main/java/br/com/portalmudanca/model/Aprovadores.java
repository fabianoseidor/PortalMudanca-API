package br.com.portalmudanca.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "APROVADORES")
@SequenceGenerator(name = "seq_aprovadores", sequenceName = "seq_aprovadores", allocationSize = 1, initialValue = 1)
public class Aprovadores implements Serializable{
	private static final long serialVersionUID = 4530816119170670229L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aprovadores")
	@Column(name = "ID_APROVADORES")
	private Long idAprovadores;
	
	@Column(name = "APROVADOR", length = 200 ,nullable = false)
	private String aprovador;
	
	@Column(name = "OBS", length = 5000)
	private String obs;
	
	/* Esta eh uma forma de realizar uma anotacao JPA de N para N
	@OneToMany(fetch = FetchType.LAZY) // Anotacao de 1 para muintos
	// @JoinTable ==> são usadas para mapear uma relação ManyToMany entre duas entidades.
	@JoinTable( name = "LISTA_APROVADORES", // Nome da tabela intermediaria de 1 para Muintos
			    // Inicio da referencia da tabela "APROVADORES" para a tabela "LISTA_APROVADORES"
			    uniqueConstraints = @UniqueConstraint( columnNames = { "ID_MUDANCA", "ID_APROVADORES" }, name = "uniq_LIST_APRO_MUDANCAS" ),
	            joinColumns = @JoinColumn(name = "ID_APROVADORES", referencedColumnName = "ID_APROVADORES", table = "APROVADORES", unique = false, foreignKey = @ForeignKey(name = "fk_MUD_APROV_LIST", value = ConstraintMode.CONSTRAINT) ),
	            // fim referencia da tabela "LISTA_APROVADORES" apontando para a tabela "APROVADORES"
	            
	            // Inicio da referencia da tabela "MUDANCA" para a tabela "LISTA_APROVADORES"
	            inverseJoinColumns = @JoinColumn( name = "ID_MUDANCA", unique = false, referencedColumnName = "ID_MUDANCA", table = "MUDANCA", foreignKey = @ForeignKey(name = "fk_APROV_LIST_MUD", value = ConstraintMode.CONSTRAINT ) )
	)
	private List<Mudanca> mudancas;
    */
	
	public Long getIdAprovadores() {
		return idAprovadores;
	}

	public void setIdAprovadores(Long idAprovadores) {
		this.idAprovadores = idAprovadores;
	}

	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "Aprovadores [idAprovadores=" + idAprovadores + ", aprovador=" + aprovador + ", obs=" + obs + "]";
	}
	
}
