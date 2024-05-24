package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import br.com.portalmudanca.enums.StatusRdm;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "MUDANCA")
@SequenceGenerator(name = "seq_mudanca", sequenceName = "seq_mudanca", allocationSize = 1, initialValue = 1)
public class Mudanca implements Serializable{

	private static final long serialVersionUID = -6221853869394115281L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mudanca" )
	@Column(name = "ID_MUDANCA")
	private Long id_mudanca;
	
	@Column(name = "TITULO_MUDANCA", length = 200, nullable = false)
	private String titulo_mudanca;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
	
	@Column(name = "DT_ALTERACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_alteracao;
	
	@Column(name = "LOGIN_USER", length = 100, nullable = false)
	private String login_user;
	
	@Enumerated(EnumType.STRING)
	private StatusRdm statusMudanca;
	
	@ManyToOne(targetEntity = TipoMudanca.class)
	@JoinColumn(name = "id_tipo_mudanca", referencedColumnName = "id_tipo_mudanca", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_TIPO_MUDANCA"))
	private TipoMudanca tipoMudanca;

	@ManyToOne(targetEntity = Criticidade.class)
	@JoinColumn(name = "id_criticidade", referencedColumnName = "id_criticidade", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_CRITICIDADE"))
	private Criticidade criticidade;

	@ManyToOne(targetEntity = ImpactoMudanca.class)
	@JoinColumn(name = "id_impacto_mudanca", referencedColumnName = "id_impacto_mudanca", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_IMPACTO_MUDANCA"))
	private ImpactoMudanca impactoMudanca;

	@ManyToOne(targetEntity = CategoriaPadrao.class)
	@JoinColumn(name = "id_categoria_padrao", referencedColumnName = "id_categoria_padrao", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_CATEGORIA_PADRAO"))
	private CategoriaPadrao categoriaPadrao;

	
	@PreUpdate
    public void preUpdate() {
		dt_criacao =  LocalDateTime.now();
    }
	    
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
        dt_alteracao = atual;
    }

	public Long getId_mudanca() {
		return id_mudanca;
	}

	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
	}

	public String getTitulo_mudanca() {
		return titulo_mudanca;
	}

	public void setTitulo_mudanca(String titulo_mudanca) {
		this.titulo_mudanca = titulo_mudanca;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public LocalDateTime getDt_alteracao() {
		return dt_alteracao;
	}

	public void setDt_alteracao(LocalDateTime dt_alteracao) {
		this.dt_alteracao = dt_alteracao;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public StatusRdm getStatusMudanca() {
		return statusMudanca;
	}

	public void setStatusMudanca(StatusRdm statusMudanca) {
		this.statusMudanca = statusMudanca;
	}

	public TipoMudanca getTipoMudanca() {
		return tipoMudanca;
	}

	public void setTipoMudanca(TipoMudanca tipoMudanca) {
		this.tipoMudanca = tipoMudanca;
	}

	public Criticidade getCriticidade() {
		return criticidade;
	}

	public void setCriticidade(Criticidade criticidade) {
		this.criticidade = criticidade;
	}

	public ImpactoMudanca getImpactoMudanca() {
		return impactoMudanca;
	}

	public void setImpactoMudanca(ImpactoMudanca impactoMudanca) {
		this.impactoMudanca = impactoMudanca;
	}

	public CategoriaPadrao getCategoriaPadrao() {
		return categoriaPadrao;
	}

	public void setCategoriaPadrao(CategoriaPadrao categoriaPadrao) {
		this.categoriaPadrao = categoriaPadrao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_mudanca);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mudanca other = (Mudanca) obj;
		return Objects.equals(id_mudanca, other.id_mudanca);
	}

	@Override
	public String toString() {
		return "Mudanca [id_mudanca=" + id_mudanca + ", titulo_mudanca=" + titulo_mudanca + ", dt_criacao=" + dt_criacao
				+ ", dt_alteracao=" + dt_alteracao + ", login_user=" + login_user + ", statusMudanca=" + statusMudanca
				+ ", tipoMudanca=" + tipoMudanca + ", criticidade=" + criticidade + ", impactoMudanca=" + impactoMudanca
				+ ", categoriaPadrao=" + categoriaPadrao + "]";
	}
    
    
}
