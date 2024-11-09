package br.com.portalmudanca.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.portalmudanca.enums.StatusRdm;

public class ListaAprovadoresDTO {

	private Long          id_mudanca;
	private LocalDateTime dt_criacao_mudanca;
	private String        login_user;
	private StatusRdm     statusMudanca;
	private String        titulo_mudanca;
	private String        criticidade;
	private String        impacto_mudanca;
	private String        tipo_mudanca;
	private Long          id_lista_aprovadores;
	private LocalDateTime dt_criacao_aprovacao;
	private LocalDateTime dt_aprovacao;
	private LocalDateTime dt_reprovacao;
	private String        motivo_rejeicao;
	private Boolean       statusAprovacao;
	private String        aprovador;
	private String        login_aprovador;
	private String        dsc_mudanca;
	private String        just_mudanca;
	private String        plano_teste;
	private String        plano_rollback;
	private LocalDate     dt_inicio;
	private LocalTime     hr_inicio;
	private LocalDate     dt_final;
	private LocalTime     hr_final;

	public ListaAprovadoresDTO( Long id_mudanca            , LocalDateTime dt_criacao_mudanca  , String login_user         , StatusRdm status_mudanca, 
			                    String titulo_mudanca      , String criticidade 	           , String impacto_mudanca    , String tipo_mudanca     , 
			                    Long id_lista_aprovadores  , LocalDateTime dt_criacao_aprovacao, LocalDateTime dt_aprovacao, Boolean statusAprovacao ,
			                    String aprovador           , String login_aprovador ) {
		 this.id_mudanca             = id_mudanca          ;
		 this.dt_criacao_mudanca     = dt_criacao_mudanca  ;
		 this.login_user             = login_user          ;
		 this.statusMudanca          = status_mudanca      ;
		 this.titulo_mudanca         = titulo_mudanca      ;
		 this.criticidade 	         = criticidade 	       ;
		 this.impacto_mudanca        = impacto_mudanca     ;
		 this.tipo_mudanca           = tipo_mudanca        ;
		 this.id_lista_aprovadores 	 = id_lista_aprovadores;
		 this.dt_criacao_aprovacao   = dt_criacao_aprovacao;
		 this.dt_aprovacao           = dt_aprovacao        ;
		 this.statusAprovacao        = statusAprovacao     ;
		 this.aprovador              = aprovador           ;
		 this.login_aprovador        = login_aprovador;
	}

	public ListaAprovadoresDTO( Long      id_mudanca          , LocalDateTime dt_criacao_mudanca  , String        login_user     , StatusRdm status_mudanca , 
					            String    titulo_mudanca      , String        criticidade 	       , String       impacto_mudanca, String   tipo_mudanca    , 
					            Long      id_lista_aprovadores, LocalDateTime dt_criacao_aprovacao, LocalDateTime dt_aprovacao   , Boolean   statusAprovacao,
					            String    aprovador           , String        login_aprovador     , String        dsc_mudanca    , String    just_mudanca   ,
					            String    plano_teste         , String        plano_rollback      , LocalDate     dt_inicio      , LocalTime hr_inicio      ,
					            LocalDate dt_final            , LocalTime     hr_final
			                  ) {
			this.id_mudanca             = id_mudanca          ;
			this.dt_criacao_mudanca     = dt_criacao_mudanca  ;
			this.login_user             = login_user          ;
			this.statusMudanca          = status_mudanca      ;
			this.titulo_mudanca         = titulo_mudanca      ;
			this.criticidade 	        = criticidade 	      ;
			this.impacto_mudanca        = impacto_mudanca     ;
			this.tipo_mudanca           = tipo_mudanca        ;
			this.id_lista_aprovadores 	= id_lista_aprovadores;
			this.dt_criacao_aprovacao   = dt_criacao_aprovacao;
			this.dt_aprovacao           = dt_aprovacao        ;
			this.statusAprovacao        = statusAprovacao     ;
			this.aprovador              = aprovador           ;
			this.login_aprovador        = login_aprovador     ;
			this.dsc_mudanca            = dsc_mudanca         ;
			this.just_mudanca           = just_mudanca        ;			
			this.plano_teste            = plano_teste         ;
			this.plano_rollback         = plano_rollback      ;			
			this.dt_inicio              = dt_inicio           ;
			this.hr_inicio              = hr_inicio           ;			
			this.dt_final               = dt_final            ;
			this.hr_final               = hr_final            ;				
	}
	public ListaAprovadoresDTO( Long      id_mudanca          , LocalDateTime dt_criacao_mudanca  , String        login_user     , StatusRdm status_mudanca , 
            String    titulo_mudanca      , String        criticidade 	       , String       impacto_mudanca, String   tipo_mudanca    , 
            Long      id_lista_aprovadores, LocalDateTime dt_criacao_aprovacao, LocalDateTime dt_aprovacao   , Boolean   statusAprovacao,
            String    aprovador           , String        login_aprovador     , String        dsc_mudanca    , String    just_mudanca   ,
            String    plano_teste         , String        plano_rollback      , LocalDate     dt_inicio      , LocalTime hr_inicio      ,
            LocalDate dt_final            , LocalTime     hr_final            , LocalDateTime dt_reprovacao  , String    motivo_rejeicao
          ) {
			this.id_mudanca             = id_mudanca          ;
			this.dt_criacao_mudanca     = dt_criacao_mudanca  ;
			this.login_user             = login_user          ;
			this.statusMudanca          = status_mudanca      ;
			this.titulo_mudanca         = titulo_mudanca      ;
			this.criticidade 	        = criticidade 	      ;
			this.impacto_mudanca        = impacto_mudanca     ;
			this.tipo_mudanca           = tipo_mudanca        ;
			this.id_lista_aprovadores 	= id_lista_aprovadores;
			this.dt_criacao_aprovacao   = dt_criacao_aprovacao;
			this.dt_aprovacao           = dt_aprovacao        ;
			this.statusAprovacao        = statusAprovacao     ;
			this.aprovador              = aprovador           ;
			this.login_aprovador        = login_aprovador     ;
			this.dsc_mudanca            = dsc_mudanca         ;
			this.just_mudanca           = just_mudanca        ;			
			this.plano_teste            = plano_teste         ;
			this.plano_rollback         = plano_rollback      ;			
			this.dt_inicio              = dt_inicio           ;
			this.hr_inicio              = hr_inicio           ;			
			this.dt_final               = dt_final            ;
			this.hr_final               = hr_final            ;
			this.dt_reprovacao          = dt_reprovacao       ;
			this.motivo_rejeicao        = motivo_rejeicao     ;				
	}

	public Long getId_mudanca() {
		return id_mudanca;
	}

	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
	}

	public LocalDateTime getDt_criacao_mudanca() {
		return dt_criacao_mudanca;
	}

	public void setDt_criacao_mudanca(LocalDateTime dt_criacao_mudanca) {
		this.dt_criacao_mudanca = dt_criacao_mudanca;
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

	public String getTitulo_mudanca() {
		return titulo_mudanca;
	}

	public void setTitulo_mudanca(String titulo_mudanca) {
		this.titulo_mudanca = titulo_mudanca;
	}

	public String getCriticidade() {
		return criticidade;
	}

	public void setCriticidade(String criticidade) {
		this.criticidade = criticidade;
	}

	public String getImpacto_mudanca() {
		return impacto_mudanca;
	}

	public void setImpacto_mudanca(String impacto_mudanca) {
		this.impacto_mudanca = impacto_mudanca;
	}

	public String getTipo_mudanca() {
		return tipo_mudanca;
	}

	public void setTipo_mudanca(String tipo_mudanca) {
		this.tipo_mudanca = tipo_mudanca;
	}

	public Long getId_lista_aprovadores() {
		return id_lista_aprovadores;
	}

	public void setId_lista_aprovadores(Long id_lista_aprovadores) {
		this.id_lista_aprovadores = id_lista_aprovadores;
	}

	public LocalDateTime getDt_criacao_aprovacao() {
		return dt_criacao_aprovacao;
	}

	public void setDt_criacao_aprovacao(LocalDateTime dt_criacao_aprovacao) {
		this.dt_criacao_aprovacao = dt_criacao_aprovacao;
	}

	public LocalDateTime getDt_aprovacao() {
		return dt_aprovacao;
	}

	public void setDt_aprovacao(LocalDateTime dt_aprovacao) {
		this.dt_aprovacao = dt_aprovacao;
	}

	public LocalDateTime getDt_reprovacao() {
		return dt_reprovacao;
	}

	public void setDt_reprovacao(LocalDateTime dt_reprovacao) {
		this.dt_reprovacao = dt_reprovacao;
	}

	public String getMotivo_rejeicao() {
		return motivo_rejeicao;
	}

	public void setMotivo_rejeicao(String motivo_rejeicao) {
		this.motivo_rejeicao = motivo_rejeicao;
	}

	public Boolean getStatusAprovacao() {
		return statusAprovacao;
	}

	public void setStatusAprovacao(Boolean statusAprovacao) {
		this.statusAprovacao = statusAprovacao;
	}

	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}

	public String getLogin_aprovador() {
		return login_aprovador;
	}

	public void setLogin_aprovador(String login_aprovador) {
		this.login_aprovador = login_aprovador;
	}

	public String getDsc_mudanca() {
		return dsc_mudanca;
	}

	public void setDsc_mudanca(String dsc_mudanca) {
		this.dsc_mudanca = dsc_mudanca;
	}

	public String getJust_mudanca() {
		return just_mudanca;
	}

	public void setJust_mudanca(String just_mudanca) {
		this.just_mudanca = just_mudanca;
	}

	public String getPlano_teste() {
		return plano_teste;
	}

	public void setPlano_teste(String plano_teste) {
		this.plano_teste = plano_teste;
	}

	public String getPlano_rollback() {
		return plano_rollback;
	}

	public void setPlano_rollback(String plano_rollback) {
		this.plano_rollback = plano_rollback;
	}

	public LocalDate getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(LocalDate dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public LocalTime getHr_inicio() {
		return hr_inicio;
	}

	public void setHr_inicio(LocalTime hr_inicio) {
		this.hr_inicio = hr_inicio;
	}

	public LocalDate getDt_final() {
		return dt_final;
	}

	public void setDt_final(LocalDate dt_final) {
		this.dt_final = dt_final;
	}

	public LocalTime getHr_final() {
		return hr_final;
	}

	public void setHr_final(LocalTime hr_final) {
		this.hr_final = hr_final;
	}
	
	
}