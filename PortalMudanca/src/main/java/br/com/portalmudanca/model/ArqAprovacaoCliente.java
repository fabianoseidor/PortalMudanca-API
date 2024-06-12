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
@Table(name = "ARQ_APROVACAO_CLIENTE")
@SequenceGenerator(name = "seq_arq_aprov_cliente", sequenceName = "seq_arq_aprov_cliente", allocationSize = 1, initialValue = 1)
public class ArqAprovacaoCliente implements Serializable{


	private static final long serialVersionUID = -3477912375905255261L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_arq_aprov_cliente")
	@Column(name = "ID_ARQ_APRO_CLI")
	private Long id_arq_apro_cli;
	
	@Column(name = "NOME_ARQ", length = 200, nullable = false)
	private String nome_arq;
	
	@Column(name = "ARQUIVO",nullable = false)
	private String arquivo;
	
	@ManyToOne(targetEntity = TipoArq.class)
	@JoinColumn(name = "id_tipo_arq", nullable = false, referencedColumnName = "id_tipo_arq", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ARQ_APROVACAO_CLIENTE_TIPO_ARQ"))
	private TipoArq tipoArq;

	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ARQ_APROVACAO_CLIENTE_MUDANCA"))
	private Mudanca mudanca;
			
	@Column(name = "DT_CRIACAO", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_arq_apro_cli() {
		return id_arq_apro_cli;
	}

	public void setId_arq_apro_cli(Long id_arq_apro_cli) {
		this.id_arq_apro_cli = id_arq_apro_cli;
	}

	public String getNome_arq() {
		return nome_arq;
	}

	public void setNome_arq(String nome_arq) {
		this.nome_arq = nome_arq;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public TipoArq getTipoArq() {
		return tipoArq;
	}

	public void setTipoArq(TipoArq tipoArq) {
		this.tipoArq = tipoArq;
	}

	public Mudanca getMudanca() {
		return mudanca;
	}

	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_arq_apro_cli);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArqAprovacaoCliente other = (ArqAprovacaoCliente) obj;
		return Objects.equals(id_arq_apro_cli, other.id_arq_apro_cli);
	}

	@Override
	public String toString() {
		return "ArqAprovacaoCliente [id_arq_apro_cli=" + id_arq_apro_cli + ", nome_arq=" + nome_arq + ", arquivo="
				+ arquivo + ", tipoArq=" + tipoArq + ", mudanca=" + mudanca + ", dt_criacao=" + dt_criacao + "]";
	}
    
}
