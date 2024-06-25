package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.portalmudanca.enums.StatusRdm;

@Entity
@Table(name = "MUDANCA")
@SequenceGenerator(name = "seq_mudanca", sequenceName = "seq_mudanca", allocationSize = 1, initialValue = 1)
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id_mudanca")
public class Mudanca implements Serializable{

	private static final long serialVersionUID = -6221853869394115281L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mudanca" )
	@Column(name = "ID_MUDANCA")
	private Long id_mudanca;
	
	@NotNull(message = "O título da mudança deverá ser informado!")
	@Column(name = "TITULO_MUDANCA", length = 200, nullable = false)
	private String titulo_mudanca;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;
		
	@Column(name = "dt_alteracao", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_alteracao;
	
	@Column(name = "LOGIN_USER", length = 100, nullable = false)
	private String login_user;
	
	@Column(name = "DT_FECHAMENTO", columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_fechamento;

	@Enumerated(EnumType.STRING)
	private StatusRdm statusMudanca;
	
	@NotNull(message = "O Tipo da mudança deve(m) ser informado!")
	@ManyToOne(targetEntity = TipoMudanca.class)
	@JoinColumn(name = "id_tipo_mudanca", referencedColumnName = "id_tipo_mudanca", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_TIPO_MUDANCA"))
	private TipoMudanca tipoMudanca;

	@NotNull(message = "A Criticidade da mudança deve(m) ser informado!")
	@ManyToOne(targetEntity = Criticidade.class)
	@JoinColumn(name = "id_criticidade", referencedColumnName = "id_criticidade", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_CRITICIDADE"))
	private Criticidade criticidade;

	@NotNull(message = "O Impacto da mudança deve(m) ser informado!")
	@ManyToOne(targetEntity = ImpactoMudanca.class)
	@JoinColumn(name = "id_impacto_mudanca", referencedColumnName = "id_impacto_mudanca", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_IMPACTO_MUDANCA"))
	private ImpactoMudanca impactoMudanca;

	@ManyToOne(targetEntity = CategoriaPadrao.class)
	@JoinColumn(name = "id_categoria_padrao", referencedColumnName = "id_categoria_padrao", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_MUDANCA_CATEGORIA_PADRAO"))
	private CategoriaPadrao categoriaPadrao;

	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PlanoComunicacao> planoComunicacoes = new ArrayList<PlanoComunicacao>();

	@OneToOne(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonBackReference
	private DadosMudanca dadosMudanca = new DadosMudanca();

	@NotNull(message = "A(s) Atividade(s) da mudança deve(m) ser informado!")
	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AtividadeMudanca> atividadesMudanca = new ArrayList<AtividadeMudanca>();

	@OneToOne(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ReportFinal reportFinal = new ReportFinal();

	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ArquivosMudanca> arquivosMudanca = new ArrayList<ArquivosMudanca>();

	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ArqAprovacaoCliente> arqAprovacaoCliente = new ArrayList<ArqAprovacaoCliente>();

	@NotNull(message = "O(s) Cliente(es) da mudança deve(m) ser informado!")
	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MudancaClientesAfetados> mudancaClientesAfetados = new ArrayList<MudancaClientesAfetados>();

	@OneToMany(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ListaAprovadores> listaAprovadores = new ArrayList<ListaAprovadores>();

	@OneToOne(mappedBy = "mudanca", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private AcaoPosAtividade acaoPosAtividade = new AcaoPosAtividade();

	@PreUpdate
    public void preUpdate() {
		dt_alteracao =  LocalDateTime.now();
    }
	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
        dt_alteracao = atual;
    }

    
	public LocalDateTime getDt_fechamento() {
		return dt_fechamento;
	}

	public void setDt_fechamento(LocalDateTime dt_fechamento) {
		this.dt_fechamento = dt_fechamento;
	}

	public AcaoPosAtividade getAcaoPosAtividade() {
		return acaoPosAtividade;
	}

	public void setAcaoPosAtividade(AcaoPosAtividade acaoPosAtividade) {
		this.acaoPosAtividade = acaoPosAtividade;
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

	public List<PlanoComunicacao> getPlanoComunicacoes() {
		return planoComunicacoes;
	}

	public void setPlanoComunicacoes(List<PlanoComunicacao> planoComunicacoes) {
		this.planoComunicacoes = planoComunicacoes;
	}

	public DadosMudanca getDadosMudanca() {
		return dadosMudanca;
	}

	public void setDadosMudanca(DadosMudanca dadosMudanca) {
		this.dadosMudanca = dadosMudanca;
	}

	public List<AtividadeMudanca> getAtividadesMudanca() {
		return atividadesMudanca;
	}

	public void setAtividadesMudanca(List<AtividadeMudanca> atividadesMudanca) {
		this.atividadesMudanca = atividadesMudanca;
	}

	public ReportFinal getReportFinal() {
		return reportFinal;
	}

	public void setReportFinal(ReportFinal reportFinal) {
		this.reportFinal = reportFinal;
	}

	public List<ArquivosMudanca> getArquivosMudanca() {
		return arquivosMudanca;
	}

	public void setArquivosMudanca(List<ArquivosMudanca> arquivosMudanca) {
		this.arquivosMudanca = arquivosMudanca;
	}

	public List<ArqAprovacaoCliente> getArqAprovacaoCliente() {
		return arqAprovacaoCliente;
	}

	public void setArqAprovacaoCliente(List<ArqAprovacaoCliente> arqAprovacaoCliente) {
		this.arqAprovacaoCliente = arqAprovacaoCliente;
	}

	public List<MudancaClientesAfetados> getMudancaClientesAfetados() {
		return mudancaClientesAfetados;
	}

	public void setMudancaClientesAfetados(List<MudancaClientesAfetados> mudancaClientesAfetados) {
		this.mudancaClientesAfetados = mudancaClientesAfetados;
	}

	public List<ListaAprovadores> getListaAprovadores() {
		return listaAprovadores;
	}

	public void setListaAprovadores(List<ListaAprovadores> listaAprovadores) {
		this.listaAprovadores = listaAprovadores;
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
				+ ", categoriaPadrao=" + categoriaPadrao + ", planoComunicacoes=" + planoComunicacoes
				+ ", dadosMudanca=" + dadosMudanca + ", atividadesMudanca=" + atividadesMudanca + ", reportFinal="
				+ reportFinal + ", arquivosMudanca=" + arquivosMudanca + ", arqAprovacaoCliente=" + arqAprovacaoCliente
				+ ", mudancaClientesAfetados=" + mudancaClientesAfetados + ", listaAprovadores=" + listaAprovadores
				+ "]";
	}


   
}
