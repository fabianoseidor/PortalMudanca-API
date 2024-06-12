package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "ACAO_POS_ATIVIDADE")
@SequenceGenerator(name = "seq_acao_pos_atividade", sequenceName = "seq_acao_pos_atividade", allocationSize = 1, initialValue = 1)
public class AcaoPosAtividade implements Serializable{

	private static final long serialVersionUID = 7949771339086353423L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acao_pos_atividade")
	@Column(name = "ID_ACAO_POS_ATIVIDADE")
	private Long id_acao_pos_atividade;
	
	@Column(name = "PLANO_TESTE", length = 5000, nullable = false)
	private String plano_teste;

	@Column(name = "PLANO_ROLLBACK", length = 5000, nullable = false)
	private String plano_rollback;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

	@ManyToOne(targetEntity = Mudanca.class)
	@JoinColumn(name = "id_mudanca", nullable = true, referencedColumnName = "id_mudanca", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ACAO_POS_ATIV_MUD"))
	private Mudanca mudanca;

	
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual =  LocalDateTime.now();
        dt_criacao   = atual;
    }


	public Long getId_acao_pos_atividade() {
		return id_acao_pos_atividade;
	}


	public void setId_acao_pos_atividade(Long id_acao_pos_atividade) {
		this.id_acao_pos_atividade = id_acao_pos_atividade;
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


	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}


	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}


	public Mudanca getMudanca() {
		return mudanca;
	}


	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}


	@Override
	public String toString() {
		return "AcaoPosAtividade [id_acao_pos_atividade=" + id_acao_pos_atividade + ", plano_teste=" + plano_teste
				+ ", plano_rollback=" + plano_rollback + ", dt_criacao=" + dt_criacao + ", mudanca=" + mudanca + "]";
	}
        
	
}
