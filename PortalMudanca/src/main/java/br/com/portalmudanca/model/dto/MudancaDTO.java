package br.com.portalmudanca.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.portalmudanca.model.ListaAprovadores;
import br.com.portalmudanca.model.MudancaClientesAfetados;

public class MudancaDTO implements Serializable{

	private static final long serialVersionUID = 2087992603549542453L;
	
	private Long id_mudanca;	
	private String titulo_mudanca;
	private String dt_criacao;
	private String dt_alteracao;
	private String login_user;
	private String email_solicitante;
	private String statusMudanca;
	private String descricao;
	private String dt_Inicio_Mudanca;
	private String hr_Inicio_Mudanca;
	private String dt_Conclusao_Mudanca;
	private String hr_Conclusao_Mudanca;
	private String solicitant_Mudanca;
	private String impacto;
	private String urgencia;
	private String tipo_Mudanca;
	private List<ListaAprovadores> listaAprovadores = new ArrayList<ListaAprovadores>();
	private List<MudancaClientesAfetados> mudancaClientesAfetados = new ArrayList<MudancaClientesAfetados>();

	public String getEmail_solicitante() {
		return email_solicitante;
	}
	public void setEmail_solicitante(String email_solicitante) {
		this.email_solicitante = email_solicitante;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDt_Inicio_Mudanca() {
		return dt_Inicio_Mudanca;
	}
	public void setDt_Inicio_Mudanca(String dt_Inicio_Mudanca) {
		this.dt_Inicio_Mudanca = dt_Inicio_Mudanca;
	}
	public String getHr_Inicio_Mudanca() {
		return hr_Inicio_Mudanca;
	}
	public void setHr_Inicio_Mudanca(String hr_Inicio_Mudanca) {
		this.hr_Inicio_Mudanca = hr_Inicio_Mudanca;
	}
	public String getDt_Conclusao_Mudanca() {
		return dt_Conclusao_Mudanca;
	}
	public void setDt_Conclusao_Mudanca(String dt_Conclusao_Mudanca) {
		this.dt_Conclusao_Mudanca = dt_Conclusao_Mudanca;
	}
	public String getHr_Conclusao_Mudanca() {
		return hr_Conclusao_Mudanca;
	}
	public void setHr_Conclusao_Mudanca(String hr_Conclusao_Mudanca) {
		this.hr_Conclusao_Mudanca = hr_Conclusao_Mudanca;
	}
	public String getSolicitant_Mudanca() {
		return solicitant_Mudanca;
	}
	public void setSolicitant_Mudanca(String solicitant_Mudanca) {
		this.solicitant_Mudanca = solicitant_Mudanca;
	}
	public String getImpacto() {
		return impacto;
	}
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public String getTipo_Mudanca() {
		return tipo_Mudanca;
	}
	public void setTipo_Mudanca(String tipo_Mudanca) {
		this.tipo_Mudanca = tipo_Mudanca;
	}
	public List<ListaAprovadores> getListaAprovadores() {
		return listaAprovadores;
	}
	public void setListaAprovadores(List<ListaAprovadores> listaAprovadores) {
		this.listaAprovadores = listaAprovadores;
	}
	public List<MudancaClientesAfetados> getMudancaClientesAfetados() {
		return mudancaClientesAfetados;
	}
	public void setMudancaClientesAfetados(List<MudancaClientesAfetados> mudancaClientesAfetados) {
		this.mudancaClientesAfetados = mudancaClientesAfetados;
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
	public String getDt_criacao() {
		return dt_criacao;
	}
	public void setDt_criacao(String dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
	public String getDt_alteracao() {
		return dt_alteracao;
	}
	public void setDt_alteracao(String dt_alteracao) {
		this.dt_alteracao = dt_alteracao;
	}
	public String getLogin_user() {
		return login_user;
	}
	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}
	public String getStatusMudanca() {
		return statusMudanca;
	}
	public void setStatusMudanca(String statusMudanca) {
		this.statusMudanca = statusMudanca;
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
		MudancaDTO other = (MudancaDTO) obj;
		return Objects.equals(id_mudanca, other.id_mudanca);
	}
	@Override
	public String toString() {
		return "MudancaDTO [id_mudanca=" + id_mudanca + ", titulo_mudanca=" + titulo_mudanca + ", dt_criacao="
				+ dt_criacao + ", dt_alteracao=" + dt_alteracao + ", login_user=" + login_user + ", statusMudanca="
				+ statusMudanca + "]";
	}

	

}
