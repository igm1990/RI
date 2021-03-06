package uo.ri.persistence.impl;

import java.sql.*;
import java.util.*;

import alb.util.jdbc.Jdbc;
import uo.ri.common.*;
import uo.ri.persistence.MecanicosGateway;

public class MecanicosGatewayImpl implements MecanicosGateway {
	private Connection c;
	private PreparedStatement pst;
	private ResultSet rs;

	public MecanicosGatewayImpl() throws SQLException, BusinessException {
		setConnection();
	}

	@Override
	public void setConnection() throws BusinessException {
		c = TratamientoExcepciones.setConnection();
	}

	@Override
	public void añadirMecanico(String nombre, String apellidos)
			throws BusinessException {
		try {
			pst = TratamientoExcepciones.configurarPreparementStament(c,
					"SQL_INSERTAR_MECANICO");
			TratamientoExcepciones.setString(pst, 1, nombre);
			TratamientoExcepciones.setString(pst, 2, apellidos);
			TratamientoExcepciones.executeUpdate(pst);
			c.commit();
		} catch (SQLException e) {
			throw new BusinessException("Error al a�adir el mecanico");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}

	@Override
	public void borrarMecanico(Long idMecanico) throws BusinessException {
		try {
			pst = TratamientoExcepciones.configurarPreparementStament(c,
					"SQL_BORRAR_MECANICO");
			TratamientoExcepciones.setLong(pst, 1, idMecanico);
			TratamientoExcepciones.executeUpdate(pst);
			c.commit();
		} catch (SQLException e) {
			throw new BusinessException("Error al borrar el mecanico");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}

	@Override
	public List<Map<String, Object>> listarMecanicos()
			throws BusinessException {
		List<Map<String, Object>> mecanicos = null;
		try {
			pst = TratamientoExcepciones.configurarPreparementStament(c,
					"SQL_LISTAR_MECANICOS");
			rs = TratamientoExcepciones.executeQuery(pst);
			mecanicos = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> mecanico = new HashMap<String, Object>();
				mecanico.put("id", rs.getLong("id"));
				mecanico.put("nombre", rs.getString("nombre"));
				mecanico.put("apellidos", rs.getString("apellidos"));
				mecanicos.add(mecanico);
			}
			c.commit();
		} catch (SQLException e) {
			throw new BusinessException("Error al listar los mecanicos");
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return mecanicos;

	}

	@Override
	public void actualizarMecanico(Long id, String nombre, String apellidos)
			throws BusinessException {
		try {
			pst = TratamientoExcepciones.configurarPreparementStament(c,
					"SQL_ACTUALIZAR_MECANICO");
			TratamientoExcepciones.setString(pst, 1, nombre);
			TratamientoExcepciones.setString(pst, 2, apellidos);
			TratamientoExcepciones.setLong(pst, 3, id);
			TratamientoExcepciones.executeUpdate(pst);
			c.commit();
		} catch (SQLException e) {
			throw new BusinessException("Error al actualizar el mecanico");
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
}
