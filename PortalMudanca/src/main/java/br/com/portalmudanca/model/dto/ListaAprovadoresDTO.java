package br.com.portalmudanca.model.dto;

import java.time.LocalDateTime;

import br.com.portalmudanca.enums.StatusRdm;

public class ListaAprovadoresDTO {

	private Long id_mudanca;
	private LocalDateTime dt_criacao_mudanca;
	private String login_user;
	private StatusRdm statusMudanca;
	private String titulo_mudanca;
	private String criticidade;
	private String impacto_mudanca;
	private String tipo_mudanca;
	private Long id_lista_aprovadores;
	private LocalDateTime dt_criacao_aprovacao;
	private LocalDateTime dt_aprovacao;
	private Boolean statusAprovacao;
	private String aprovador;

	public ListaAprovadoresDTO( Long id_mudanca            , LocalDateTime dt_criacao_mudanca  , String login_user         , StatusRdm status_mudanca, 
			                    String titulo_mudanca      , String criticidade 	           , String impacto_mudanca    , String tipo_mudanca     , 
			                    Long id_lista_aprovadores  , LocalDateTime dt_criacao_aprovacao, LocalDateTime dt_aprovacao, Boolean statusAprovacao ,
			                    String aprovador ) {
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
		 this.aprovador              = aprovador     ;
	}
	
	public ListaAprovadoresDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
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
	public String getStatus_mudanca() {
		return statusMudanca.getStatusRdm();
	}
	public void setStatus_mudanca(StatusRdm status_mudanca) {
		this.statusMudanca = status_mudanca;
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
	public Boolean getStatusAprovacao() {
		return statusAprovacao;
	}
	public void setStatusAprovacao(Boolean statusAprovacao) {
		this.statusAprovacao = statusAprovacao;
	}

	
	
	
}