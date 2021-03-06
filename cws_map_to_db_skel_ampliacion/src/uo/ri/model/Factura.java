package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import uo.ri.model.exception.BusinessException;
import uo.ri.model.types.status.AveriaStatus;
import uo.ri.model.types.status.FacturaStatus;
import alb.util.date.DateUtil;

@Entity
@Table(name = "TFACTURAS")
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long numero;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private double importe;
	private double iva;

	@Enumerated(EnumType.STRING)
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;

	@OneToMany(mappedBy = "factura")
	private Set<Averia> averias = new HashSet<Averia>();

	@OneToMany(mappedBy = "factura")
	private Set<Cargo> cargos = new HashSet<Cargo>();

	Factura() {
	}

	public Factura(Long numero) {
		super();
		this.numero = numero;
		this.fecha = new Date(new java.util.Date().getTime());
	}

	public Factura(long numero, Date today) {
		this(numero);
		this.fecha = new Date(today.getTime());
	}

	public Factura(long l, List<Averia> averias) throws BusinessException {
		this.numero = l;
		this.fecha = new Date(new java.util.Date().getTime());
		for (Averia a : averias) {
			addAveria(a);
		}
	}

	public Factura(long l, Date fecha, List<Averia> averias)
			throws BusinessException {
		this(l, fecha);
		for (Averia a : averias) {
			addAveria(a);
		}
	}

	public Set<Cargo> getCargos() {
		return new HashSet<>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		for (Averia a : averias) {
			importe += a.getImporte();
		}

		importe = importe * (1 + getIva() / 100);
		importe = Math.rint(importe * 100) / 100;

		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getIva() {
		java.util.Date _01_07_2012 = DateUtil.fromString("1/7/2012");
		if (this.fecha.after(_01_07_2012)) {
			this.iva = 21.0;
		} else {
			this.iva = 18.0;
		}

		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public FacturaStatus getStatus() {
		return status;
	}

	public void setStatus(FacturaStatus status) {
		this.status = status;
	}

	public Set<Averia> getAverias() {
		return new HashSet<>(averias);
	}

	Set<Averia> _getAverias() {
		return this.averias;
	}

	public Long getNumero() {
		return numero;
	}

	/**
	 * Añade la averia a la factura
	 * 
	 * @param averia
	 * @throws BusinessException
	 */
	public void addAveria(Averia averia) throws BusinessException {
		// Verificar que la factura está en estado SIN_ABONAR
		// Verificar que La averia está TERMINADA
		// linkar factura y averia
		// marcar la averia como FACTURADA ( averia.markAsInvoiced() )
		// calcular el importe

		if (averia.getStatus().equals(AveriaStatus.TERMINADA)
				&& getStatus().equals(FacturaStatus.SIN_ABONAR)) {
			averia.setStatus(AveriaStatus.FACTURADA);
			Association.Facturar.link(this, averia);
		} else {
			throw new BusinessException("No se puede a�adir la aver�a a"
					+ " la factura, no est� terminada");
		}
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de
	 * factura
	 */
	void calcularImporte() {
		// iva = ...
		// importe = ...
		for (Averia a : averias)
			importe += a.getImporte();

		importe = importe * (1 + getIva() / 100);
		importe = Math.rint(importe * 100) / 100;
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula el
	 * importe
	 * 
	 * @param averia
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		// desenlazar factura y averia
		// la averia vuelve al estado FINALIZADA ( averia.markBackToFinished() )
		// volver a calcular el importe
		if (getStatus().equals(FacturaStatus.SIN_ABONAR)) {
			Association.Facturar.unlink(this, averia);
			averia.setStatus(AveriaStatus.TERMINADA);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe="
				+ importe + ", iva=" + iva + ", status=" + status + ", averias="
				+ averias + "]";
	}

}
