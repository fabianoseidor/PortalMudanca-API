package br.com.portalmudanca.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.portalmudanca.model.ArquivosMudanca;
import br.com.portalmudanca.model.CategoriaPadrao;
import br.com.portalmudanca.model.Criticidade;
import br.com.portalmudanca.model.DadosMudanca;
import br.com.portalmudanca.model.ImpactoMudanca;
import br.com.portalmudanca.model.Mudanca;
import br.com.portalmudanca.model.TipoMudanca;

public class MudancaPadraoDTO {

	@JsonBackReference
	private Mudanca mudanca;
	
	private TipoMudanca tipoMudanca;
	private ImpactoMudanca impactoMudanca;
	private Criticidade criticidade;
	private CategoriaPadrao categoriaPadrao;

	@JsonBackReference
	private DadosMudanca dadosMudanca;
	private List<ArquivosMudanca> arquivosMudancas = new ArrayList<ArquivosMudanca>();
	
	public Mudanca getMudanca() {
		return mudanca;
	}
	public void setMudanca(Mudanca mudanca) {
		this.mudanca = mudanca;
	}
	public TipoMudanca getTipoMudanca() {
		return tipoMudanca;
	}
	public void setTipoMudanca(TipoMudanca tipoMudanca) {
		this.tipoMudanca = tipoMudanca;
	}
	public ImpactoMudanca getImpactoMudanca() {
		return impactoMudanca;
	}
	public void setImpactoMudanca(ImpactoMudanca impactoMudanca) {
		this.impactoMudanca = impactoMudanca;
	}
	public Criticidade getCriticidade() {
		return criticidade;
	}
	public void setCriticidade(Criticidade criticidade) {
		this.criticidade = criticidade;
	}
	public CategoriaPadrao getCategoriaPadrao() {
		return categoriaPadrao;
	}
	public void setCategoriaPadrao(CategoriaPadrao categoriaPadrao) {
		this.categoriaPadrao = categoriaPadrao;
	}
	
	public DadosMudanca getDadosMudanca() {
		return dadosMudanca;
	}
	public void setDadosMudanca(DadosMudanca dadosMudanca) {
		this.dadosMudanca = dadosMudanca;
	}
	
	public List<ArquivosMudanca> getArquivosMudancas() {
		return arquivosMudancas;
	}
	public void setArquivosMudancas(List<ArquivosMudanca> arquivosMudancas) {
		this.arquivosMudancas = arquivosMudancas;
	}
	
	
}
