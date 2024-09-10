package br.com.portalmudanca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ITENS_CATEGORIA_PADRAO")
@SequenceGenerator(name = "seq_itens_cat_padrao", sequenceName = "seq_itens_cat_padrao", allocationSize = 1, initialValue = 1)

public class ItensCategoriaPadrao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_itens_cat_padrao")
	@Column(name = "ITENS_CAT_PADRAO")
	private Long id_itens_cat_padrao;
	
	@Column(name = "TITULO_CAT_PADRAO",length = 200, nullable = false)
	private String tituloCatPadrao;
	
	@Column(name = "DESCRICAO_CAT_PADRAO",length = 200, nullable = false)
	private String descCatPadrao;
	
	@Column(name = "DURACAO", nullable = false)
	private Long duracao;	

	@Column(name = "PRIORIDADE", nullable = false)
	private int prioridade;	
	
	@Column(name = "INDISPONIBILIDADE")
	private Boolean indisponibilidade;	
	
	@ManyToOne(targetEntity = CategoriaPadrao.class)
	@JoinColumn(name = "id_categoria_padrao", nullable = true, referencedColumnName = "id_categoria_padrao", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_ITENS_CAT_PADRAO_CATEGORIA"))
    private CategoriaPadrao categoriaPadrao;
	
	public Boolean getIndisponibilidade() {
		return indisponibilidade;
	}

	public void setIndisponibilidade(Boolean indisponibilidade) {
		this.indisponibilidade = indisponibilidade;
	}

	public Long getId_itens_cat_padrao() {
		return id_itens_cat_padrao;
	}

	public void setId_itens_cat_padrao(Long id_itens_cat_padrao) {
		this.id_itens_cat_padrao = id_itens_cat_padrao;
	}

	public String getTituloCatPadrao() {
		return tituloCatPadrao;
	}

	public void setTituloCatPadrao(String tituloCatPadrao) {
		this.tituloCatPadrao = tituloCatPadrao;
	}

	public String getDescCatPadrao() {
		return descCatPadrao;
	}

	public void setDescCatPadrao(String descCatPadrao) {
		this.descCatPadrao = descCatPadrao;
	}

	public Long getDuracao() {
		return duracao;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public CategoriaPadrao getCategoriaPadrao() {
		return categoriaPadrao;
	}

	public void setCategoriaPadrao(CategoriaPadrao categoriaPadrao) {
		this.categoriaPadrao = categoriaPadrao;
	}
	
}
