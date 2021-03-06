package uo.ri.persistence;

import java.util.List;
import java.util.Map;

import uo.ri.common.BusinessException;

/**
 * Clase de la parte de persistencia encargada de manejar los datos de los
 * proveedores.
 * 
 * @author Iván González Mahagamage
 *
 */
public interface ProveedoresGateway {
	/**
	 * Método que se encarga de conectarse a la base de datos.
	 * 
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	void setConnection() throws BusinessException;

	/**
	 * Método que a�ade un proveedor a la base de datos.
	 * 
	 * @param nombre
	 *            Nombre del proveedor.
	 * @param codigo
	 *            C�digo del proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	void añadirProveedor(String nombre, String codigo) throws BusinessException;

	/**
	 * Método que borra un proveedor de la base de datos.
	 * 
	 * @param idProveedor
	 *            ID del proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	void borrarProveedor(Long idProveedor) throws BusinessException;

	/**
	 * Método que lista los proveedores que hay en la base de datos.
	 * 
	 * @return Una lista los proveedores que hay en la base de datos.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	List<Map<String, Object>> listarProveedores() throws BusinessException;

	/**
	 * Método que modica la informaci�n de un proveedor en la base de datos.
	 * 
	 * @param id
	 *            ID del proveedor.
	 * @param nombre
	 *            Nombre del proveedor.
	 * @param codigo
	 *            C�digo del proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	void actualizarProveedor(Long id, String nombre, String codigo)
			throws BusinessException;

	/**
	 * Método que busca el ID de un proveedor.
	 * 
	 * @param nombre
	 *            Nombre del proveedor.
	 * @return ID del proveedor.
	 * @throws BusinessException
	 *             Excepción ocurrida al realizar el programa.
	 */
	Long buscarIDProveedor(String nombre) throws BusinessException;
}
