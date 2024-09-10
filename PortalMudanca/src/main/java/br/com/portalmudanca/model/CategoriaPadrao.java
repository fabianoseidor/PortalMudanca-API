package br.com.portalmudanca.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "CATEGORIA_PADRAO")
@SequenceGenerator(name = "seq_categoria_padrao", sequenceName = "seq_categoria_padrao", allocationSize = 1, initialValue = 1)
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id_categoria_padrao")
public class CategoriaPadrao implements Serializable{

	private static final long serialVersionUID = 5554328306966819593L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_padrao")
	@Column(name = "ID_CATEGORIA_PADRAO")
	private Long id_categoria_padrao;
	
	@Column(name = "CATEGORIA_PADRAO",length = 200, nullable = false)
	private String categoria_padrao;
	
	@Column(name = "DT_CRIACAO", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime dt_criacao;

	@Column( name = "ATIVAR_CONTRATO")
	private Boolean ativar_contrato = Boolean.FALSE;
	
	@Column( name = "DESLIGAR_CONTRATO")
	private Boolean desligar_contrato = Boolean.FALSE;

	@Column(name = "LOGIN_USER", length = 100)
	private String login_user;
	
	@Column(name = "OBS", length = 200)
	private String obs;
	
	@OneToMany(mappedBy = "categoriaPadrao", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ItensCategoriaPadrao> itensCategoriaPadrao = new ArrayList<ItensCategoriaPadrao>();
		
	@PrePersist
	public void prePersist() {
		final LocalDateTime atual = LocalDateTime.now();
		dt_criacao     = atual;
	}
	
	public Boolean getAtivar_contrato() {
		return ativar_contrato;
	}

	public void setAtivar_contrato(Boolean ativar_contrato) {
		this.ativar_contrato = ativar_contrato;
	}

	public Boolean getDesligar_contrato() {
		return desligar_contrato;
	}

	public void setDesligar_contrato(Boolean desligar_contrato) {
		this.desligar_contrato = desligar_contrato;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public List<ItensCategoriaPadrao> getItensCategoriaPadrao() {
		return itensCategoriaPadrao;
	}

	public void setItensCategoriaPadrao(List<ItensCategoriaPadrao> itensCategoriaPadrao) {
		this.itensCategoriaPadrao = itensCategoriaPadrao;
	}

	public LocalDateTime getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(LocalDateTime dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Long getId_categoria_padrao() {
		return id_categoria_padrao;
	}

	public void setId_categoria_padrao(Long id_categoria_padrao) {
		this.id_categoria_padrao = id_categoria_padrao;
	}

	public String getCategoria_padrao() {
		return categoria_padrao;
	}

	public void setCategoria_padrao(String categoria_padrao) {
		this.categoria_padrao = categoria_padrao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_categoria_padrao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaPadrao other = (CategoriaPadrao) obj;
		return Objects.equals(id_categoria_padrao, other.id_categoria_padrao);
	}

	@Override
	public String toString() {
		return "CategoriaPadrao [id_categoria_padrao=" + id_categoria_padrao + ", categoria_padrao=" + categoria_padrao
				+ ", dt_criacao=" + dt_criacao + ", obs=" + obs + "]";
	}
}
