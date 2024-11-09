package br.com.portalmudanca.model.dto;

public class ReprovacaoDTO {
	private String motivo_rejeicao;
	private Long id_lista_aprovadores; 
	private Long id_mudanca;
	public String getMotivo_rejeicao() {
		return motivo_rejeicao;
	}
	public void setMotivo_rejeicao(String motivo_rejeicao) {
		this.motivo_rejeicao = motivo_rejeicao;
	}
	public Long getId_lista_aprovadores() {
		return id_lista_aprovadores;
	}
	public void setId_lista_aprovadores(Long id_lista_aprovadores) {
		this.id_lista_aprovadores = id_lista_aprovadores;
	}
	public Long getId_mudanca() {
		return id_mudanca;
	}
	public void setId_mudanca(Long id_mudanca) {
		this.id_mudanca = id_mudanca;
	}  

}
