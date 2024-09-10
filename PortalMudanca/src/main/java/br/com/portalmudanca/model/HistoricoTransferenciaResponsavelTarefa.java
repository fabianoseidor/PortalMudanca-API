package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "HISTORICO_TRANSF_RESPONS_TAREFA")
@SequenceGenerator(name = "seq_hist_transf_resp_tarefa", sequenceName = "seq_hist_transf_resp_tarefa", allocationSize = 1, initialValue = 1)
public class HistoricoTransferenciaResponsavelTarefa implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hist_transf_resp_tarefa")
	@Column(name = "ID_HISTORICO")
    private Long id_historico;
	
	@Column(name = "ID_MUDANCA")
	private Long id_mudanca;
	
	@Column(name = "ID_TAREFA")
	private Long id_tarefa;
	
	@Column(name = "ID_RESPONSAVEL_ORIGEM")
	private Long id_responsavel_origem;
	
	@Column(name = "NOME_USER_ORIGEM")
	private String nome_user_origem;
	
	@Column(name = "ID_RESPONSAVEL_DESTINO")
	private Long id_responsavel_destino;
	
	@Column(name = "NOME_USER_DESTINO")
	private String nome_user_destino;

	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dt_criacao;

	@Column(name = "LOGIN_USER")
	private String login_user;

	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}

	public String getNome_user_origem() {
		return nome_user_origem;
	}

	public void setNome_user_origem(String nome_user_origem) {
		this.nome_user_origem = nome_user_origem;
	}

	public String getNome_user_destino() {
		return nome_user_destino;
	}

	public void setNome_user_destino(String nome_user_destino) {
		this.nome_user_destino = nome_user_destino;
	}


	public Long getId_historico() {
		return id_historico;
	}

	public void setId_historico(Long id_historico) {
		this.id_historico = id_historico;
	}

	public Long getId_mudanca() {
		return id_mudanca;
	}

	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
	}

	public Long getId_tarefa() {
		return id_tarefa;
	}

	public void setId_tarefa(Long id_tarefa) {
		this.id_tarefa = id_tarefa;
	}

	public Long getId_responsavel_origem() {
		return id_responsavel_origem;
	}

	public void setId_responsavel_origem(Long id_responsavel_origem) {
		this.id_responsavel_origem = id_responsavel_origem;
	}

	public Long getId_responsavel_destino() {
		return id_responsavel_destino;
	}

	public void setId_responsavel_destino(Long id_responsavel_destino) {
		this.id_responsavel_destino = id_responsavel_destino;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}
	
	
	
}
