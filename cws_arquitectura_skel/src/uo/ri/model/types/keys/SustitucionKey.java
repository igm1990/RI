package uo.ri.model.types.keys;

import java.io.Serializable;

/**
 * Clase que simula la clave primaria de clase Sustitucion.
 * 
 * @author Iván González Mahagamage
 *
 */
public class SustitucionKey implements Serializable {
	private static final long serialVersionUID = 1L;

	private IntervencionKey intervencion;
	private Long repuesto;

	/**
	 * Constructor por defecto
	 *
	 */
	SustitucionKey() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime * result
				+ ((repuesto == null) ? 0 : repuesto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SustitucionKey other = (SustitucionKey) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
			return false;
		return true;
	}
}
