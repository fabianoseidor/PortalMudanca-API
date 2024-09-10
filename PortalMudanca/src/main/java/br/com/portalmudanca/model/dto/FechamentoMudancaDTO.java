package br.com.portalmudanca.model.dto;

public class FechamentoMudancaDTO {
	private String statusGMUD; 
	private Long   idMudanca;
    private String reportFinal;
	public String getStatusGMUD() {
		return statusGMUD;
	}
	public void setStatusGMUD(String statusGMUD) {
		this.statusGMUD = statusGMUD;
	}
	public Long getIdMudanca() {
		return idMudanca;
	}
	public void setIdMudanca(Long idMudanca) {
		this.idMudanca = idMudanca;
	}
	public String getReportFinal() {
		return reportFinal;
	}
	public void setReportFinal(String reportFinal) {
		this.reportFinal = reportFinal;
	}
    
    
}
