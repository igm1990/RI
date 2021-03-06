package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

/**
 * Clase de la parte de persistencia encargada de manejar los datos de los
 * suministros.
 * 
 * @author Iván González Mahagamage
 *
 */
public interface SuministrosGateway {
	/**
	 * Método que se encarga de conectarse a la base de datos.
	 * 
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	void setConnection() throws BusinessException;

	/**
	 * Método que a�ade un repuesto suministrado por un proveedor de la base de
	 * datos.
	 * 
	 * @param idRepuesto
	 *            ID del repuesto.
	 * @param idProveedor
	 *            ID del proveedor.
	 * @param precio
	 *            Precio del respuesto.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 * @throws SQLException
	 *             Excepción ocurrida al realizar secuencias SQL.
	 */
	void añadirSuministros(Long idRepuesto, Long idProveedor, Double precio)
			throws BusinessException, SQLException;

	/**
	 * Método que borra un repuesto suministrado por un proveedor de la base de
	 * datos.
	 * 
	 * @param idRepuesto
	 *            ID del repuesto.
	 * @param idProveedor
	 *            ID del proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 * @throws SQLException
	 *             Excepción ocurrida al realizar secuencias SQL.
	 */
	void borrarSuministros(Long idRepuesto, Long idProveedor)
			throws BusinessException, SQLException;

	/**
	 * Método que lista los repuestos que suministra un proveedor.
	 * 
	 * @param idProveedor
	 *            ID del proveedor.
	 * @return Una lista con los repuestos que suministra un proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 * @throws SQLException
	 *             Excepción ocurrida al realizar secuencias SQL.
	 */
	List<Map<String, Object>> listarSuministrosProveedor(Long idProveedor)
			throws BusinessException, SQLException;

	/**
	 * Método que lista los proveedores que suministran un suministro
	 * 
	 * @param idRepuesto
	 *            ID del repuesto.
	 * @return Una lista de los proveedores que suministran un suministro
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 * @throws SQLException
	 *             Excepción ocurrida al realizar secuencias SQL.
	 */
	List<Map<String, Object>> listarSuministrosRepuesto(Long idRepuesto)
			throws BusinessException, SQLException;

	/**
	 * Actualiza la información de un repuesto suministrado por proveedor.
	 * 
	 * @param idRepuesto
	 *            ID del repuesto.
	 * @param idProveedor
	 *            ID del proveedor.
	 * @param precio
	 *            Precio del respuesto.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 * @throws SQLException
	 *             Excepción ocurrida al realizar secuencias SQL.
	 */
	void actualizarSuministro(Long idRepuesto, Long idProveedor, Double precio)
			throws BusinessException, SQLException;
}
