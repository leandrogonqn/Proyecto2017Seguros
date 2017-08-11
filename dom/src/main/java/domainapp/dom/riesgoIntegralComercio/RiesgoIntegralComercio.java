package domainapp.dom.riesgoIntegralComercio;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagosRepository;
import domainapp.dom.estado.Estado;
import domainapp.dom.marca.Marcas;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.poliza.PolizasRepository;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;
import domainapp.dom.vehiculo.Vehiculos;
import domainapp.dom.vehiculo.VehiculosRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Polizas")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value = "RiesgoIntegralComercio")
public class RiesgoIntegralComercio extends Polizas implements Comparable<RiesgoIntegralComercio> {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", "Poliza Riesgo Integral Comercio N°: " + getPolizaNumero());
	}
	// endregion

	// Constructor
	public RiesgoIntegralComercio(String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, DetalleTipoPagos polizaPago, boolean polizaAlertaVencimientoPago,
			double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			final String riesgoIntegralComercioOtrosNombre, final float riesgoIntegralComercioOtrosMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		setPolizaPago(polizaPago);
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		setRiesgoIntegralComercioIncendioEdificio(riesgoIntegralComercioIncendioEdificio);
		setRiesgoIntegralComercioIncendioContenido(riesgoIntegralComercioIncendioContenido);
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		setRiesgoIntegralComercioOtrosNombre(riesgoIntegralComercioOtrosNombre);
		setRiesgoIntegralComercioOtrosMonto(riesgoIntegralComercioOtrosMonto);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}

	public RiesgoIntegralComercio(String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, DetalleTipoPagos polizaPago, boolean polizaAlertaVencimientoPago,
			double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			final String riesgoIntegralComercioOtrosNombre, final float riesgoIntegralComercioOtrosMonto,
			Polizas riesgoIntegralComercio) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		setPolizaPago(polizaPago);
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		setRiesgoIntegralComercioOtrosNombre(riesgoIntegralComercioOtrosNombre);
		setRiesgoIntegralComercioOtrosMonto(riesgoIntegralComercioOtrosMonto);
		riesgoIntegralComercio.setPolizaRenovacion(this);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}

	// IncendioEdificio
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Incendio edificio")
	private float riesgoIntegralComercioIncendioEdificio;

	public float getRiesgoIntegralComercioIncendioEdificio() {
		return riesgoIntegralComercioIncendioEdificio;
	}

	public void setRiesgoIntegralComercioIncendioEdificio(float riesgoIntegralComercioIncendioEdificio) {
		this.riesgoIntegralComercioIncendioEdificio = riesgoIntegralComercioIncendioEdificio;
	}

	// IncendioContenido
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Incendio contenido")
	private float riesgoIntegralComercioIncendioContenido;

	public float getRiesgoIntegralComercioIncendioContenido() {
		return riesgoIntegralComercioIncendioContenido;
	}

	public void setRiesgoIntegralComercioIncendioContenido(float riesgoIntegralComercioIncendioContenido) {
		this.riesgoIntegralComercioIncendioContenido = riesgoIntegralComercioIncendioContenido;
	}

	// Robo
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Robo")
	private float riesgoIntegralComercioRobo;

	public float getRiesgoIntegralComercioRobo() {
		return riesgoIntegralComercioRobo;
	}

	public void setRiesgoIntegralComercioRobo(float riesgoIntegralComercioRobo) {
		this.riesgoIntegralComercioRobo = riesgoIntegralComercioRobo;
	}

	// Cristales
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED, optionality = Optionality.OPTIONAL)
	@PropertyLayout(named = "Cristales")
	private float riesgoIntegralComercioCristales;

	public float getRiesgoIntegralComercioCristales() {
		return riesgoIntegralComercioCristales;
	}

	public void setRiesgoIntegralComercioCristales(float riesgoIntegralComercioCristales) {
		this.riesgoIntegralComercioCristales = riesgoIntegralComercioCristales;
	}

	// riesgoIntegralComercioRc
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "RC")
	private float riesgoIntegralComercioRc;

	public float getRiesgoIntegralComercioRc() {
		return riesgoIntegralComercioRc;
	}

	public void setRiesgoIntegralComercioRc(float riesgoIntegralComercioRc) {
		this.riesgoIntegralComercioRc = riesgoIntegralComercioRc;
	}

	// riesgoIntegralComercioRcL
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "RCL")
	private float riesgoIntegralComercioRcl;

	public float getRiesgoIntegralComercioRcl() {
		return riesgoIntegralComercioRcl;
	}

	public void setRiesgoIntegralComercioRcl(float riesgoIntegralComercioRcl) {
		this.riesgoIntegralComercioRcl = riesgoIntegralComercioRcl;
	}

	// RiesgoIntegralComercioDanioPorAgua
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Daño por agua")
	private float riesgoIntegralComercioDanioPorAgua;

	public float getRiesgoIntegralComercioDanioPorAgua() {
		return riesgoIntegralComercioDanioPorAgua;
	}

	public void setRiesgoIntegralComercioDanioPorAgua(float riesgoIntegralComercioDanioPorAgua) {
		this.riesgoIntegralComercioDanioPorAgua = riesgoIntegralComercioDanioPorAgua;
	}

	// RiesgoIntegralComercioOtrosNombre
	@Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Otros Nombre")
	private String riesgoIntegralComercioOtrosNombre;

	public String getRiesgoIntegralComercioOtrosNombre() {
		return riesgoIntegralComercioOtrosNombre;
	}

	public void setRiesgoIntegralComercioOtrosNombre(String riesgoIntegralComercioOtrosNombre) {
		this.riesgoIntegralComercioOtrosNombre = riesgoIntegralComercioOtrosNombre;
	}

	// RiesgoIntegralComercioOtrosMonto
	@Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Otros Monto")
	private float riesgoIntegralComercioOtrosMonto;

	public float getRiesgoIntegralComercioOtrosMonto() {
		return riesgoIntegralComercioOtrosMonto;
	}

	public void setRiesgoIntegralComercioOtrosMonto(float riesgoIntegralComercioOtrosMonto) {
		this.riesgoIntegralComercioOtrosMonto = riesgoIntegralComercioOtrosMonto;
	}
	
	// region > delete (action)

	// Actualizar Incendio Contenido
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioIncendioContenido(
			@ParameterLayout(named = "Incendio Contenido") final float riesgoIntegralComercioIncendioContenido) {
		setRiesgoIntegralComercioIncendioContenido(riesgoIntegralComercioIncendioContenido);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioIncendioContenido() {
		return getRiesgoIntegralComercioIncendioContenido();
	}

	// Actualizar Incendio Edificio
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioIncendioEdificio(
			@ParameterLayout(named = "Incendio Edificio") final float riesgoIntegralComercioIncendioEdificio) {
		setRiesgoIntegralComercioIncendioEdificio(riesgoIntegralComercioIncendioEdificio);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioIncendioEdificio() {
		return getRiesgoIntegralComercioIncendioEdificio();
	}

	// Actualizar Cristales
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioCristales(
			@ParameterLayout(named = "Cristales") final float riesgoIntegralComercioCristales) {
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioCristales() {
		return getRiesgoIntegralComercioCristales();
	}

	// Actualizar Robo
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioRobo(
			@ParameterLayout(named = "Robo") final float riesgoIntegralComercioRobo) {
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRobo() {
		return getRiesgoIntegralComercioRobo();
	}

	// Actualizar RC
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioRc(
			@ParameterLayout(named = "RC") final float riesgoIntegralComercioRc) {
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRc() {
		return getRiesgoIntegralComercioRc();
	}

	// Actualizar RCL
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioRcl(
			@ParameterLayout(named = "RCL") final float riesgoIntegralComercioRcl) {
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRcl() {
		return getRiesgoIntegralComercioRcl();
	}

	// Actualizar DanioPorAgua
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioDanioPorAgua(
			@ParameterLayout(named = "Daño por agua") final float riesgoIntegralComercioDanioPorAgua) {
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioDanioPorAgua() {
		return getRiesgoIntegralComercioDanioPorAgua();
	}

	// Actualizar PolizaNumero
	public RiesgoIntegralComercio actualizarPolizaNumero(@ParameterLayout(named = "Numero") final String polizaNumero) {
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero() {
		return getPolizaNumero();
	}

	// Actualizar Poliza Cliente
	public RiesgoIntegralComercio actualizarPolizaCliente(
			@ParameterLayout(named = "Cliente") final Clientes polizaCliente) {
		setPolizasCliente(polizaCliente);
		return this;
	}

	public List<Clientes> choices0ActualizarPolizaCliente() {
		return clientesRepository.listarActivos();
	}

	public Clientes default0ActualizarPolizaCliente() {
		return getPolizaCliente();
	}

	// Actualizar polizaCompania
	public RiesgoIntegralComercio actualizarPolizaCompania(
			@ParameterLayout(named = "Compañia") final Companias polizaCompania) {
		actualizarPolizaCompania(polizaCompania);
		return this;
	}

	public List<Companias> choices0ActualizarPolizaCompania() {
		return companiaRepository.listarActivos();
	}

	public Companias default0ActualizarPolizaCompania() {
		return getPolizaCompania();
	}

	// Actualizar polizaFechaEmision
	public RiesgoIntegralComercio actualizarPolizaFechaEmision(
			@ParameterLayout(named = "Fecha de Emision") final Date polizaFechaEmision) {
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision() {
		return getPolizaFechaEmision();
	}

	// Actualizar polizaFechaVigencia
	public RiesgoIntegralComercio actualizarPolizaFechaVigencia(
			@ParameterLayout(named = "Fecha de Vigencia") final Date polizaFechaVigencia) {
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia() {
		return getPolizaFechaVigencia();
	}

	// polizaFechaVencimiento
	public RiesgoIntegralComercio actualizarPolizaFechaVencimiento(
			@ParameterLayout(named = "Fecha de Vencimiento") final Date polizaFechaVencimiento) {
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento() {
		return getPolizaFechaVencimiento();
	}

	// polizaFechaVencimientoPago
	public RiesgoIntegralComercio actualizarPolizaFechaVencimientoPago(
			@ParameterLayout(named = "Fecha Vencimiento de Pago") final Date polizaFechaVencimientoPago) {
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimientoPago() {
		return getPolizaFechaVencimientoPago();
	}

	// polizaPago
	public RiesgoIntegralComercio actualizarPolizaPago(
			@ParameterLayout(named = "Pago") final DetalleTipoPagos polizaPago) {
		setPolizaPago(polizaPago);
		return this;
	}

	public List<DetalleTipoPagos> choices0ActualizarPolizaPago() {
		return detalleTipoPagosRepository.listarActivos();
	}

	public DetalleTipoPagos default0ActualizarPolizaPago() {
		return getPolizaPago();
	}

	// polizaAlertaVencimientoPago
	public RiesgoIntegralComercio actualizarPolizaAlertaVencimientoPago(
			@ParameterLayout(named = "Alerta Vencimiento de Pago") final boolean polizaAlertaVencimientoPago) {
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		return this;
	}

	public boolean default0ActualizarPolizaAlertaVencimientoPago() {
		return getPolizaAlertaVencimientoPago();
	}

	// polizaFechaBaja
	public RiesgoIntegralComercio actualizarPolizaFechaBaja(
			@ParameterLayout(named = "Fecha de Baja") final Date polizaFechaBaja) {
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja() {
		return getPolizaFechaBaja();
	}

	// polizaMotivoBaja
	public RiesgoIntegralComercio actualizarPolizaMotivoBaja(
			@ParameterLayout(named = "Motivo de la Baja") final String polizaMotivoBaja) {
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja() {
		return getPolizaMotivoBaja();
	}

	// RiesgoIntegralComercioOtrosMonto
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioOtrosMonto(
			@ParameterLayout(named = "Otros Monto") final float riesgoIntegralComercioOtrosMonto) {
		setRiesgoIntegralComercioOtrosMonto(riesgoIntegralComercioOtrosMonto);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioOtrosMonto() {
		return getRiesgoIntegralComercioOtrosMonto();
	}
	
	// riesgoIntegralComercioOtrosNombre
	public RiesgoIntegralComercio actualizarRiesgoIntegralComercioOtrosNombre(
			@ParameterLayout(named = "Otros Nombre") final String riesgoIntegralComercioOtrosNombre) {
		setRiesgoIntegralComercioOtrosNombre(riesgoIntegralComercioOtrosNombre);
		return this;
	}

	public String default0ActualizarRiesgoIntegralComercioOtrosNombre() {
		return getRiesgoIntegralComercioOtrosNombre();
	}
	
	// polizaImporteTotal
	public RiesgoIntegralComercio actualizarPolizaImporteTotal(
			@ParameterLayout(named = "Importe Total") final double polizaImporteTotal) {
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal() {
		return getPolizaImporteTotal();
	}

	// polizaRenovacion
	@ActionLayout(named = "Actualizar Renovacion")
	public RiesgoIntegralComercio actualizarPolizaRenovacion(
			@ParameterLayout(named = "Renovacion") final Polizas polizaRenovacion) {
		setPolizaRenovacion(polizaRenovacion);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public List<Polizas> choices0ActualizarPolizaRenovacion() {
		return polizasRepository.listar();
	}

	public Polizas default0ActualizarPolizaRenovacion() {
		return getPolizaRenovacion();
	}

	public RiesgoIntegralComercio borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	// endregion

	// acciones

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Emitir Renovacion")
	public RiesgoIntegralComercio renovacion(@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Clientes polizaCliente,
			@ParameterLayout(named = "Compañia") final Companias polizaCompania,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Fecha Vencimiento Pago") final Date polizaFechaVencimientoPago,
			@ParameterLayout(named = "Pago") final DetalleTipoPagos polizaPago,
			@ParameterLayout(named = "Alerta Vencimiento Pago") final boolean polizaAlertaVencimientoPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal,
			@ParameterLayout(named = "Robo") float riesgoIntegralComercioRobo, 
			@ParameterLayout(named = "Cristales") float riesgoIntegralComercioCristales,
			@ParameterLayout(named = "Incendio Edificio") float riesgoIntegralComercioIncendioEdificio,
			@ParameterLayout(named = "Incendio Contenido") float riesgoIntegralComercioIncendioContenido,
			@ParameterLayout(named = "RC") float riesgoIntegralComercioRc, 
			@ParameterLayout(named = "RCL") float riesgoIntegralComercioRcl, 
			@ParameterLayout(named = "Daño Por Agua") float riesgoIntegralComercioDanioPorAgua,
			@ParameterLayout(named = "Otros Nombre") final String riesgoIntegralComercioOtrosNombre, 
			@ParameterLayout(named = "Otros Monto") final float riesgoIntegralComercioOtrosMonto) {
		return riesgoIntegralComercioRepository.renovacion(polizaNumero, polizaCliente, polizaCompania,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaFechaVencimientoPago, polizaPago,
				polizaAlertaVencimientoPago, polizaImporteTotal, riesgoIntegralComercioRobo, riesgoIntegralComercioCristales,
				riesgoIntegralComercioIncendioEdificio, riesgoIntegralComercioIncendioContenido, riesgoIntegralComercioRc, 
				riesgoIntegralComercioRcl, riesgoIntegralComercioDanioPorAgua, riesgoIntegralComercioOtrosNombre,
				riesgoIntegralComercioOtrosMonto,this);
	}

	public List<Clientes> choices1Renovacion() {
		return clientesRepository.listarActivos();
	}

	public List<Companias> choices2Renovacion() {
		return companiaRepository.listarActivos();
	}

	public List<TiposDeCoberturas> choices4Renovacion() {
		return tiposDeCoberturasRepository.listarActivos();
	}

	public List<DetalleTipoPagos> choices7Renovacion() {
		return detalleTipoPagosRepository.listarActivos();
	}

	public Clientes default1Renovacion() {
		return getPolizaCliente();
	}

	public Companias default2Renovacion() {
		return getPolizaCompania();
	}

	public DetalleTipoPagos default7Renovacion() {
		return getPolizaPago();
	}

	public boolean default8Renovacion() {
		return getPolizaAlertaVencimientoPago();
	}

	// region > toString, compareTo
	@Override
	public String toString() {
		return ObjectContracts.toString(this, "polizaNumero");
	}

	@Override
	public int compareTo(final RiesgoIntegralComercio other) {
		return ObjectContracts.compare(this, other, "polizaNumero");
	}

	// endregion

	// region > injected dependencies

	@Inject
	RepositoryService repositoryService;

	@Inject
	TitleService titleService;

	@Inject
	MessageService messageService;

	@Inject
	ClientesRepository clientesRepository;

	@Inject
	VehiculosRepository vehiculosRepository;

	@Inject
	DetalleTipoPagosRepository detalleTipoPagosRepository;

	@Inject
	CompaniaRepository companiaRepository;

	@Inject
	TiposDeCoberturasRepository tiposDeCoberturasRepository;

	@Inject
	PolizasRepository polizasRepository;

	@Inject
	RiesgoIntegralComercioRepository riesgoIntegralComercioRepository;

	// endregion

}
