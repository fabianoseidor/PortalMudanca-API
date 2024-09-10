package br.com.portalmudanca.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import br.com.portalmudanca.enums.StatusRdm;

public class ListaMudancaNaoFechadasDTO {
	
	private Long id_mudanca;

	private LocalDateTime dt_criacao;
	private LocalDateTime dt_alteracao;
	private LocalDateTime dt_fechamento;
	private StatusRdm statusMudanca;
	private String titulo_mudanca;
	private String email_solicitante;
	private LocalDate dt_inicio;
	private LocalTime hr_inicio;
	private LocalDate dt_final;
	private LocalTime hr_final;
	private int qty_dias;
	
	public ListaMudancaNaoFechadasDTO( Long id_mudanca, LocalDateTime dt_criacao, LocalDateTime dt_alteracao, LocalDateTime dt_fechamento, StatusRdm statusMudanca, String titulo_mudanca, String email_solicitante,
			                           LocalDate dt_inicio, LocalTime hr_inicio, LocalDate dt_final, LocalTime hr_final/*, int qty_dias */) {
		this.id_mudanca        = id_mudanca;                   
		this.dt_criacao        = dt_criacao;                   
		this.dt_alteracao      = dt_alteracao;                 
		this.dt_fechamento     = dt_fechamento;                
		this.statusMudanca     = statusMudanca;               
		this.titulo_mudanca    = titulo_mudanca;               
		this.email_solicitante = email_solicitante;            
		this.dt_inicio         = dt_inicio;                    
		this.hr_inicio         = hr_inicio;                    
		this.dt_final          = dt_final;                     
		this.hr_final          = hr_final;                     
		// this.qty_dias          = qty_dias;                      		                                                              
	}

	public ListaMudancaNaoFechadasDTO( Long id_mudanca) {
		this.id_mudanca        = id_mudanca;
	}
	public Long getId_mudanca() {
		return id_mudanca;
	}

	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
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

	public LocalDateTime getDt_fechamento() {
		return dt_fechamento;
	}

	public void setDt_fechamento(LocalDateTime dt_fechamento) {
		this.dt_fechamento = dt_fechamento;
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

	public String getEmail_solicitante() {
		return email_solicitante;
	}

	public void setEmail_solicitante(String email_solicitante) {
		this.email_solicitante = email_solicitante;
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

	public int getQty_dias() {
		return qty_dias;
	}

	public void setQty_dias(int qty_dias) {
		this.qty_dias = qty_dias;
	}
	

}
