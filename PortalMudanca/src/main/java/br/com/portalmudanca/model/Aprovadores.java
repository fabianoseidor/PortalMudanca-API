package br.com.portalmudanca.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "APROVADORES")
@SequenceGenerator(name = "seq_aprovadores", sequenceName = "seq_aprovadores", allocationSize = 1, initialValue = 1)
public class Aprovadores implements Serializable{
	private static final long serialVersionUID = 4530816119170670229L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aprovadores")
	@Column(name = "ID_APROVADORES")
	private Long idAprovadores;
	
	@Column(name = "APROVADOR", length = 200 ,nullable = false)
	private String aprovador;
	
	@Column(name = "OBS", length = 5000)
	private String obs;

	public Long getIdAprovadores() {
		return idAprovadores;
	}

	public void setIdAprovadores(Long idAprovadores) {
		this.idAprovadores = idAprovadores;
	}

	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "Aprovadores [idAprovadores=" + idAprovadores + ", aprovador=" + aprovador + ", obs=" + obs + "]";
	}
	
}
