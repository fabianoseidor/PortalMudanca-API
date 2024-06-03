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
@Table(name = "ARQUIVOS_MUDANCA")
@SequenceGenerator(name = "seq_arquivos_mudanca", sequenceName = "seq_arquivos_mudanca", allocationSize = 1, initialValue = 1)

public class ArquivosMudanca implements Serializable{

	private static final long serialVersionUID = -4800083005942260356L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_arquivos_mudanca")
	@Column(name = "ID_ARQUIVOS_MUDANCA")
	private Long id_arquivos_mudanca;
	
	@Column(name = "NOME_ARQ")
	private String nome_arq;
	
	@ManyToOne(targetEntity = TipoArq.class)
	@JoinColumn(name = "id_tipo_arq", nullable = false, referencedColumnName = "id_tipo_arq", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ARQUIVOS_MUDANCA_TIPO_ARQ"))
	private TipoArq tipoArq;

	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ARQUIVOS_MUDANCA_MUD"))
	private Mudanca mudanca;
			
	@Column(name = "DT_CRIACAO", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }

	public Long getId_arquivos_mudanca() {
		return id_arquivos_mudanca;
	}

	public void setId_arquivos_mudanca(Long id_arquivos_mudanca) {
		this.id_arquivos_mudanca = id_arquivos_mudanca;
	}

	public String getNome_arq() {
		return nome_arq;
	}

	public void setNome_arq(String nome_arq) {
		this.nome_arq = nome_arq;
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
		return Objects.hash(id_arquivos_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivosMudanca other = (ArquivosMudanca) obj;
		return Objects.equals(id_arquivos_mudanca, other.id_arquivos_mudanca);
	}

	@Override
	public String toString() {
		return "ArquivosMudanca [id_arquivos_mudanca=" + id_arquivos_mudanca + ", nome_arq=" + nome_arq + ", tipoArq="
				+ tipoArq + ", mudanca=" + mudanca + ", dt_criacao=" + dt_criacao + "]";
	}

}
