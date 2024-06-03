package br.com.portalmudanca.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_ARQ")
@SequenceGenerator(name = "seq_tipo_arq", sequenceName = "seq_tipo_arq", allocationSize = 1, initialValue = 1)
public class TipoArq implements Serializable{

	private static final long serialVersionUID = -4527350215060876083L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_arq")
	@Column(name = "ID_TIPO_ARQ")
	private Long id_tipo_arq;
	
	@Column(name = "TIPO_ARQ", nullable = false, length = 4)
	private String tipo_arq;

	public Long getId_tipo_arq() {
		return id_tipo_arq;
	}

	public void setId_tipo_arq(Long id_tipo_arq) {
		this.id_tipo_arq = id_tipo_arq;
	}

	public String getTipo_arq() {
		return tipo_arq;
	}

	public void setTipo_arq(String tipo_arq) {
		this.tipo_arq = tipo_arq;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_tipo_arq);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoArq other = (TipoArq) obj;
		return Objects.equals(id_tipo_arq, other.id_tipo_arq);
	}

	@Override
	public String toString() {
		return "TipoArq [id_tipo_arq=" + id_tipo_arq + ", tipo_arq=" + tipo_arq + "]";
	}
}
