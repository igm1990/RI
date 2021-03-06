package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import uo.ri.model.exception.BusinessException;
import uo.ri.model.types.AveriaStatus;

@Entity
@Table(name = "TAVERIAS")
public class Averia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe = 0.0;

	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;

	@ManyToOne
	private Vehiculo vehiculo;

	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();

	@ManyToOne
	private Mecanico mecanico;

	@ManyToOne
	private Factura factura;

	Averia() {
	}

	public Averia(Vehiculo vehiculo) {
		this(new Date(), vehiculo);
	}

	public Averia(Date fecha, Vehiculo vehiculo) {
		this.fecha = fecha;
		Association.Averiar.link(vehiculo, this);
		vehiculo.incrementarAverias();
	}

	public Averia(Vehiculo vehiculo, String descripcion) {
		this(new Date(), vehiculo);
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public AveriaStatus getStatus() {
		return status;
	}

	public void setStatus(AveriaStatus status) {
		this.status = status;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>(intervenciones);
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" + fecha
				+ ", importe=" + importe + ", status=" + status + "]";
	}

	/**
	 * Asigna la averia al mecanico
	 * 
	 * @param mecanico
	 * @throws BusinessException
	 */
	public void assignTo(Mecanico mecanico) throws BusinessException {
		// Solo se puede asignar una averia que est� ABIERTA
		// linkado de averia y mecanico
		// la averia pasa a ASIGNADA
		if (getStatus().equals(AveriaStatus.ABIERTA)) {
			Association.Asignar.link(mecanico, this);
			setStatus(AveriaStatus.ASIGNADA);
		} else
			throw new BusinessException(
					"La averia no se puede asignar, su estado es abierta");
	}

	/**
	 * El mec�nico da por finalizada esta aver�a, entonces se calcula el importe
	 * 
	 * @throws BusinessException
	 * 
	 */
	public void markAsFinished() throws BusinessException {
		// Se verifica que est� en estado ASIGNADA
		// se calcula el importe
		// se desvincula mecanico y averia
		// el status cambia a TERMINADA
		if (getStatus().equals(AveriaStatus.ASIGNADA)) {
			calcularImporte();
			Association.Asignar.unlink(mecanico, this);
			setStatus(AveriaStatus.TERMINADA);
		} else
			throw new BusinessException(
					"La aver�a no esta asignada para poder finalizarla.");
	}

	private void calcularImporte() {
		double precio = 0;
		for (Intervencion intervencion : intervenciones)
			precio += intervencion.getImporte();
		setImporte(precio);
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mec�nico (el
	 * primero no ha podido terminar la reparaci�n), pero debe ser pasada a
	 * ABIERTA primero
	 */
	public void reopen() {
		// Solo se puede reabrir una averia que est� TERMINADA
		// la averia pasa a ABIERTA
		if (status.equals(AveriaStatus.TERMINADA)) {
			setStatus(AveriaStatus.ABIERTA);
		}
	}

	/**
	 * Una aver�a ya facturada se elimina de la factura
	 */
	public void markBackToFinished() {
		// verificar que la averia est� FACTURADA
		// cambiar status a TERMINADA
		if (getStatus().equals(AveriaStatus.FACTURADA)) {
			Association.Facturar.unlink(factura, this);
			setStatus(AveriaStatus.TERMINADA);
		}
	}

	public void desassign() {
		Association.Asignar.unlink(mecanico, this);
	}

}
