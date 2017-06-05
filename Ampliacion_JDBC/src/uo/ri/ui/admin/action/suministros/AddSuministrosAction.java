package uo.ri.ui.admin.action.suministros;

import java.sql.SQLException;

import alb.util.console.Console;
import uo.ri.business.AdminService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServiceFactory;
import uo.ri.ui.util.PrinterAdapter;
import uo.ri.ui.util.interfaces.Command;

/**
 * Clase de la interfaz de usuario para añadir un repuesto suministrado por un
 * proveedor.
 * 
 * @author Iván González Mahagamage
 *
 */
public class AddSuministrosAction implements Command {

	@Override
	public void execute() throws BusinessException, SQLException {
		Long idRepuesto = Console.readLong("Id del repuesto");
		Long idProveedor = Console.readLong("Id del proveedor");
		Double precio = Console.readDouble("Precio del repuesto");
		AdminService service = ServiceFactory.getAdminService();
		service.newSuministro(idRepuesto, idProveedor, precio);
		PrinterAdapter
				.print("Nuevo repuesto ofrecido por el proveedor a�adido");
	}

}
