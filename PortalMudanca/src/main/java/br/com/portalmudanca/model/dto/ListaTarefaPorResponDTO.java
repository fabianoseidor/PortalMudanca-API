package br.com.portalmudanca.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ListaTarefaPorResponDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long          id_mudanca;	
	private String        titulo_mudanca;
	private Long          id_tipo_mudanca;
	private Long          id_atividade_mudanca;
	private String        titulo_atividade_mudanca;	
	private LocalDate     dt_tarefa;
	private LocalTime     duracao;
	private Integer       prioridade;	
	private String        atividade_mudanca;
	private Long          id_responsavel_atividade;
	private LocalDateTime dt_inicio_tarefa;
	private LocalDateTime dt_final_tarefa;
	private String        responsavel_atividade;
	private String        login_user;
	
	public ListaTarefaPorResponDTO(Long id_mudanca  , String titulo_mudanca, Long id_tipo_mudanca    , Long id_atividade_mudanca    , String titulo_atividade_mudanca, LocalDate dt_tarefa          ,
                                   LocalTime duracao, Integer prioridade   , String atividade_mudanca, Long id_responsavel_atividade, LocalDateTime dt_inicio_tarefa , LocalDateTime dt_final_tarefa,        
                                   String responsavel_atividade            , String login_user ) {
		this.id_mudanca	              = id_mudanca              ;	            
		this.titulo_mudanca           = titulo_mudanca          ;          
		this.id_tipo_mudanca          = id_tipo_mudanca         ;            
		this.id_atividade_mudanca     = id_atividade_mudanca    ;    
		this.titulo_atividade_mudanca =	titulo_atividade_mudanca;
		this.atividade_mudanca        = atividade_mudanca       ;       
		this.dt_tarefa                = dt_tarefa               ;               
		this.duracao                  = duracao                 ;                 
		this.dt_inicio_tarefa         = dt_inicio_tarefa        ;        
		this.dt_final_tarefa          = dt_final_tarefa         ;         
		this.prioridade	              = prioridade              ;
		this.responsavel_atividade    = responsavel_atividade   ;
		this.id_responsavel_atividade = id_responsavel_atividade;
		this.login_user               = login_user;
	}
/*	
	public ListaTarefaPorResponDTO( Long id_mudanca, Long id_atividade_mudanca, Long id_responsavel_atividade ) {
		this.id_mudanca               = id_mudanca;
		this.id_atividade_mudanca     = id_atividade_mudanca;
		this.id_responsavel_atividade = id_responsavel_atividade;
	}
*/
	
	public String getResponsavel_atividade() {
		return responsavel_atividade;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public void setResponsavel_atividade(String responsavel_atividade) {
		this.responsavel_atividade = responsavel_atividade;
	}

	public Long getId_tipo_mudanca() {
		return id_tipo_mudanca;
	}
	public void setId_tipo_mudanca(Long id_tipo_mudanca) {
		this.id_tipo_mudanca = id_tipo_mudanca;
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
	public Long getId_atividade_mudanca() {
		return id_atividade_mudanca;
	}
	public void setId_atividade_mudanca(Long id_atividade_mudanca) {
		this.id_atividade_mudanca = id_atividade_mudanca;
	}
	public String getTitulo_atividade_mudanca() {
		return titulo_atividade_mudanca;
	}
	public void setTitulo_atividade_mudanca(String titulo_atividade_mudanca) {
		this.titulo_atividade_mudanca = titulo_atividade_mudanca;
	}
	public String getAtividade_mudanca() {
		return atividade_mudanca;
	}
	public void setAtividade_mudanca(String atividade_mudanca) {
		this.atividade_mudanca = atividade_mudanca;
	}
	public LocalDate getDt_tarefa() {
		return dt_tarefa;
	}
	public void setDt_tarefa(LocalDate dt_tarefa) {
		this.dt_tarefa = dt_tarefa;
	}
	public LocalTime getDuracao() {
		return duracao;
	}
	public void setDuracao(LocalTime duracao) {
		this.duracao = duracao;
	}
	public LocalDateTime getDt_inicio_tarefa() {
		return dt_inicio_tarefa;
	}
	public void setDt_inicio_tarefa(LocalDateTime dt_inicio_tarefa) {
		this.dt_inicio_tarefa = dt_inicio_tarefa;
	}
	public LocalDateTime getDt_final_tarefa() {
		return dt_final_tarefa;
	}
	public void setDt_final_tarefa(LocalDateTime dt_final_tarefa) {
		this.dt_final_tarefa = dt_final_tarefa;
	}
	public Integer getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}
	
	public Long getId_responsavel_atividade() {
		return id_responsavel_atividade;
	}
	public void setId_responsavel_atividade(Long id_responsavel_atividade) {
		this.id_responsavel_atividade = id_responsavel_atividade;
	}
}
