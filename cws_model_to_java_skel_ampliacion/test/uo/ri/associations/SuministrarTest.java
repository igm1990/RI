package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.Proveedor;
import uo.ri.model.Repuesto;
import uo.ri.model.Suministro;
import uo.ri.model.Sustitucion;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;
import uo.ri.model.exception.BusinessException;

public class SuministrarTest {
	private Mecanico mecanico;
	private Averia averia;
	private Intervencion intervencion;
	private Repuesto repuesto;
	private Sustitucion sustitucion;
	private Vehiculo vehiculo;
	private TipoVehiculo tipoVehiculo;
	private Cliente cliente;
	private Proveedor proveedor;
	private Suministro suministro;

	@Before
	public void setUp() throws BusinessException {
		cliente = new Cliente("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);

		tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		averia = new Averia(vehiculo, "falla la junta la trocla");

		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");

		intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);

		repuesto = new Repuesto("R1001", "junta la trocla", 100.0, 5, 9, 2);
		sustitucion = new Sustitucion(repuesto, intervencion);

		proveedor = new Proveedor("asdfadf");
		suministro = new Suministro(proveedor, repuesto, 250);
	}

	@Test
	public void testSuministrarAdd() throws BusinessException {
		assertTrue(suministro.getProveedor().equals(proveedor));
		assertTrue(suministro.getRepuesto().equals(repuesto));

		assertTrue(repuesto.getSuministros().contains(suministro));
		assertTrue(repuesto.getSuministros().size() == 1);

		assertTrue(proveedor.getSuministros().contains(suministro));
		assertTrue(proveedor.getSuministros().size() == 1);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testSuministrarRemove() throws BusinessException {
		Association.Suministrar.unlink(suministro);

		assertTrue(suministro.getProveedor() == null);
		assertTrue(suministro.getRepuesto() == null);

		assertTrue(!repuesto.getSuministros().contains(sustitucion));
		assertTrue(repuesto.getSuministros().size() == 0);

		assertTrue(!proveedor.getSuministros().contains(sustitucion));
		assertTrue(proveedor.getSuministros().size() == 0);
	}

	@Test
	public void testSafeReturnProveedor() throws BusinessException {
		Set<Suministro> suministros = proveedor.getSuministros();
		suministros.remove(suministro);

		assertTrue(suministros.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				proveedor.getSuministros().size() == 1);
	}

	@Test
	public void testSafeReturnRepuesto() throws BusinessException {
		Set<Suministro> suministros = repuesto.getSuministros();
		suministros.remove(suministro);

		assertTrue(suministros.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				repuesto.getSuministros().size() == 1);
	}

}
