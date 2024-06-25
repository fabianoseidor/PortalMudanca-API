package br.com.portalmudanca.model.dto;

import java.time.LocalDateTime;

public class PlanoComunicacaoDAO {

	private Long id_plano_comunicacao;
	private String nome_contato;
	private String email;
	private String empresa;
	private LocalDateTime dt_criacao;
	
	public Long getId_plano_comunicacao() {
		return id_plano_comunicacao;
	}
	public void setId_plano_comunicacao(Long id_plano_comunicacao) {
		this.id_plano_comunicacao = id_plano_comunicacao;
	}
	public String getNome_contato() {
		return nome_contato;
	}
	public void setNome_contato(String nome_contato) {
		this.nome_contato = nome_contato;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}
	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
	@Override
	public String toString() {
		return "PlanoComunicacaoDAO [id_plano_comunicacao=" + id_plano_comunicacao + ", nome_contato=" + nome_contato
				+ ", email=" + email + ", empresa=" + empresa + ", dt_criacao=" + dt_criacao + "]";
	}
	
	

}
