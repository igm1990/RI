package uo.ri.business.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import uo.ri.business.CashService;
import uo.ri.business.impl.cash.CreateInvoiceFor;
import uo.ri.common.BusinessException;

/**
 * Clase de la parte logica que implementa a la clase CashService.
 * 
 * @author Iv�n Gonz�lez Mahagamage
 *
 */
public class CashServiceImpl implements CashService {

	@Override
	public Map<String, Object> createInvoiceFor(List<Long> idsAveria)
			throws BusinessException, SQLException {
		return new CreateInvoiceFor(idsAveria).execute();
	}

}
