package uo.ri.persistence.repuesto;

import uo.ri.persistence.util.FinderType;
import uo.ri.persistence.util.Jpa;

/**
 * Clase de la capa de persistencia que busca el id de un repuesto por su
 * código.
 * 
 * @author Iván González Mahagamage
 *
 */
public class FinderRepuestoIdByCodigo implements FinderType {
	private String codigoRepuesto;

	/**
	 * Constructor con parámetros.
	 *
	 * @param codigoRepuesto
	 *            Código del repuesto.
	 */
	public FinderRepuestoIdByCodigo(String codigoRepuesto) {
		this.codigoRepuesto = codigoRepuesto;
	}

	@Override
	public Object execute() {
		return Jpa.getManager()
				.createNamedQuery("Repuesto.findIdByCodigo", Long.class)
				.setParameter(1, codigoRepuesto).getSingleResult();
	}

	@Override
	public String toString() {
		return "Buscar el id del repuesto con el código -> " + codigoRepuesto;
	}

}
