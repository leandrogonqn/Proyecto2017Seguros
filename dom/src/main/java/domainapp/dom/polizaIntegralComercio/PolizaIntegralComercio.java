package domainapp.dom.polizaIntegralComercio;

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

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.estado.Estado;
import domainapp.dom.marca.Marca;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;
import domainapp.dom.vehiculo.Vehiculo;
import domainapp.dom.vehiculo.VehiculoRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Polizas")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value = "RiesgoIntegralComercio")
public class PolizaIntegralComercio extends Poliza implements Comparable<PolizaIntegralComercio> {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", "Poliza Riesgo Integral Comercio N°: " + getPolizaNumero());
	}
	// endregion

	// Constructor
	public PolizaIntegralComercio(String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			 DetalleTipoPago polizaPago, 
			double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			float riesgoIntegralComercioRCC, final String riesgoIntegralComercioOtrosNombre, final float riesgoIntegralComercioOtrosMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		setRiesgoIntegralComercioIncendioEdificio(riesgoIntegralComercioIncendioEdificio);
		setRiesgoIntegralComercioIncendioContenido(riesgoIntegralComercioIncendioContenido);
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		setRiesgoIntegralComercioRCC(riesgoIntegralComercioRCC);
		setRiesgoIntegralComercioOtrosNombre(riesgoIntegralComercioOtrosNombre);
		setRiesgoIntegralComercioOtrosMonto(riesgoIntegralComercioOtrosMonto);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}

	public PolizaIntegralComercio(String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			 DetalleTipoPago polizaPago,
			double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			float riesgoIntegralComercioRCC, final String riesgoIntegralComercioOtrosNombre, final float riesgoIntegralComercioOtrosMonto,
			Poliza riesgoIntegralComercio) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		setRiesgoIntegralComercioRCC(riesgoIntegralComercioRCC);
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
	
	// RiesgoIntegralComercioRCC
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "RCC")
	private float riesgoIntegralComercioRCC;

	public float getRiesgoIntegralComercioRCC() {
		return riesgoIntegralComercioRCC;
	}

	public void setRiesgoIntegralComercioRCC(float riesgoIntegralComercioRCC) {
		this.riesgoIntegralComercioRCC = riesgoIntegralComercioRCC;
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
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioIncendioContenido(
			@ParameterLayout(named = "Incendio Contenido") final float riesgoIntegralComercioIncendioContenido) {
		setRiesgoIntegralComercioIncendioContenido(riesgoIntegralComercioIncendioContenido);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioIncendioContenido() {
		return getRiesgoIntegralComercioIncendioContenido();
	}

	// Actualizar Incendio Edificio
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioIncendioEdificio(
			@ParameterLayout(named = "Incendio Edificio") final float riesgoIntegralComercioIncendioEdificio) {
		setRiesgoIntegralComercioIncendioEdificio(riesgoIntegralComercioIncendioEdificio);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioIncendioEdificio() {
		return getRiesgoIntegralComercioIncendioEdificio();
	}

	// Actualizar Cristales
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioCristales(
			@ParameterLayout(named = "Cristales") final float riesgoIntegralComercioCristales) {
		setRiesgoIntegralComercioCristales(riesgoIntegralComercioCristales);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioCristales() {
		return getRiesgoIntegralComercioCristales();
	}

	// Actualizar Robo
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioRobo(
			@ParameterLayout(named = "Robo") final float riesgoIntegralComercioRobo) {
		setRiesgoIntegralComercioRobo(riesgoIntegralComercioRobo);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRobo() {
		return getRiesgoIntegralComercioRobo();
	}

	// Actualizar RC
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioRc(
			@ParameterLayout(named = "RC") final float riesgoIntegralComercioRc) {
		setRiesgoIntegralComercioRc(riesgoIntegralComercioRc);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRc() {
		return getRiesgoIntegralComercioRc();
	}

	// Actualizar RCL
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioRcl(
			@ParameterLayout(named = "RCL") final float riesgoIntegralComercioRcl) {
		setRiesgoIntegralComercioRcl(riesgoIntegralComercioRcl);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRcl() {
		return getRiesgoIntegralComercioRcl();
	}

	// Actualizar DanioPorAgua
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioDanioPorAgua(
			@ParameterLayout(named = "Daño por agua") final float riesgoIntegralComercioDanioPorAgua) {
		setRiesgoIntegralComercioDanioPorAgua(riesgoIntegralComercioDanioPorAgua);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioDanioPorAgua() {
		return getRiesgoIntegralComercioDanioPorAgua();
	}
	
	// Actualizar RCC
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioRCC(
			@ParameterLayout(named = "RCC") final float riesgoIntegralComercioRCC) {
		setRiesgoIntegralComercioRCC(riesgoIntegralComercioRCC);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioRCC() {
		return getRiesgoIntegralComercioRCC();
	}

	// Actualizar PolizaNumero
	public PolizaIntegralComercio actualizarPolizaNumero(@ParameterLayout(named = "Numero") final String polizaNumero) {
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero() {
		return getPolizaNumero();
	}

	// Actualizar Poliza Cliente
	public PolizaIntegralComercio actualizarPolizaCliente(
			@ParameterLayout(named = "Cliente") final Cliente polizaCliente) {
		setPolizasCliente(polizaCliente);
		return this;
	}

	public List<Cliente> choices0ActualizarPolizaCliente() {
		return clientesRepository.listarActivos();
	}

	public Cliente default0ActualizarPolizaCliente() {
		return getPolizaCliente();
	}

	// Actualizar polizaCompania
	public PolizaIntegralComercio actualizarPolizaCompania(
			@ParameterLayout(named = "Compañia") final Compania polizaCompania) {
		actualizarPolizaCompania(polizaCompania);
		return this;
	}

	public List<Compania> choices0ActualizarPolizaCompania() {
		return companiaRepository.listarActivos();
	}

	public Compania default0ActualizarPolizaCompania() {
		return getPolizaCompania();
	}

	// Actualizar polizaFechaEmision
	public PolizaIntegralComercio actualizarPolizaFechaEmision(
			@ParameterLayout(named = "Fecha de Emision") final Date polizaFechaEmision) {
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision() {
		return getPolizaFechaEmision();
	}

	// Actualizar polizaFechaVigencia
	public PolizaIntegralComercio actualizarPolizaFechaVigencia(
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
	public PolizaIntegralComercio actualizarPolizaFechaVencimiento(
			@ParameterLayout(named = "Fecha de Vencimiento") final Date polizaFechaVencimiento) {
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento() {
		return getPolizaFechaVencimiento();
	}

	// polizaPago
	public PolizaIntegralComercio actualizarPolizaPago(
			@ParameterLayout(named = "Pago") final DetalleTipoPago polizaPago) {
		setPolizaPago(polizaPago);
		return this;
	}

	public List<DetalleTipoPago> choices0ActualizarPolizaPago() {
		return detalleTipoPagosRepository.listarActivos();
	}

	public DetalleTipoPago default0ActualizarPolizaPago() {
		return getPolizaPago();
	}

	// polizaFechaBaja
	public PolizaIntegralComercio actualizarPolizaFechaBaja(
			@ParameterLayout(named = "Fecha de Baja") final Date polizaFechaBaja) {
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja() {
		return getPolizaFechaBaja();
	}

	// polizaMotivoBaja
	public PolizaIntegralComercio actualizarPolizaMotivoBaja(
			@ParameterLayout(named = "Motivo de la Baja") final String polizaMotivoBaja) {
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja() {
		return getPolizaMotivoBaja();
	}

	// RiesgoIntegralComercioOtrosMonto
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioOtrosMonto(
			@ParameterLayout(named = "Otros Monto") final float riesgoIntegralComercioOtrosMonto) {
		setRiesgoIntegralComercioOtrosMonto(riesgoIntegralComercioOtrosMonto);
		return this;
	}

	public float default0ActualizarRiesgoIntegralComercioOtrosMonto() {
		return getRiesgoIntegralComercioOtrosMonto();
	}
	
	// riesgoIntegralComercioOtrosNombre
	public PolizaIntegralComercio actualizarRiesgoIntegralComercioOtrosNombre(
			@ParameterLayout(named = "Otros Nombre") final String riesgoIntegralComercioOtrosNombre) {
		setRiesgoIntegralComercioOtrosNombre(riesgoIntegralComercioOtrosNombre);
		return this;
	}

	public String default0ActualizarRiesgoIntegralComercioOtrosNombre() {
		return getRiesgoIntegralComercioOtrosNombre();
	}
	
	// polizaImporteTotal
	public PolizaIntegralComercio actualizarPolizaImporteTotal(
			@ParameterLayout(named = "Importe Total") final double polizaImporteTotal) {
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal() {
		return getPolizaImporteTotal();
	}

	// polizaRenovacion
	@ActionLayout(named = "Actualizar Renovacion")
	public PolizaIntegralComercio actualizarPolizaRenovacion(
			@ParameterLayout(named = "Renovacion") final Poliza polizaRenovacion) {
		setPolizaRenovacion(polizaRenovacion);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public List<Poliza> choices0ActualizarPolizaRenovacion() {
		return polizasRepository.listar();
	}

	public Poliza default0ActualizarPolizaRenovacion() {
		return getPolizaRenovacion();
	}

	public PolizaIntegralComercio borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	// endregion

	// acciones

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Emitir Renovacion")
	public PolizaIntegralComercio renovacion(
/*0*/			@ParameterLayout(named = "Número") final String polizaNumero,
/*1*/			@ParameterLayout(named = "Cliente") final Cliente polizaCliente,
/*2*/		@ParameterLayout(named = "Compañia") final Compania polizaCompania,
/*3*/			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
/*4*/			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
/*5*/			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
/*6*/			@ParameterLayout(named = "Pago") final DetalleTipoPago polizaPago,
/*7*/			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal,
/*8*/			@ParameterLayout(named = "Robo") float riesgoIntegralComercioRobo, 
/*9*/			@ParameterLayout(named = "Cristales") float riesgoIntegralComercioCristales,
/*10*/			@ParameterLayout(named = "Incendio Edificio") float riesgoIntegralComercioIncendioEdificio,
/*11*/			@ParameterLayout(named = "Incendio Contenido") float riesgoIntegralComercioIncendioContenido,
/*12*/			@ParameterLayout(named = "RC") float riesgoIntegralComercioRc, 
/*13*/			@ParameterLayout(named = "RCL") float riesgoIntegralComercioRcl, 
/*14*/			@ParameterLayout(named = "Daño Por Agua") float riesgoIntegralComercioDanioPorAgua,
/*15*/			@ParameterLayout(named = "RCC") float riesgoIntegralComercioRCC,
/*16*/			@ParameterLayout(named = "Otros Nombre") final String riesgoIntegralComercioOtrosNombre, 
/*17*/			@ParameterLayout(named = "Otros Monto") final float riesgoIntegralComercioOtrosMonto) {
		return riesgoIntegralComercioRepository.renovacion(polizaNumero, polizaCliente, polizaCompania,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaPago,
				 polizaImporteTotal, riesgoIntegralComercioRobo, riesgoIntegralComercioCristales,
				riesgoIntegralComercioIncendioEdificio, riesgoIntegralComercioIncendioContenido, riesgoIntegralComercioRc, 
				riesgoIntegralComercioRcl, riesgoIntegralComercioDanioPorAgua, riesgoIntegralComercioRCC, riesgoIntegralComercioOtrosNombre,
				riesgoIntegralComercioOtrosMonto,this);
	}

	public List<Cliente> choices1Renovacion() {
		return clientesRepository.listarActivos();
	}

	public List<Compania> choices2Renovacion() {
		return companiaRepository.listarActivos();
	}

	public List<DetalleTipoPago> choices6Renovacion() {
		return detalleTipoPagosRepository.listarActivos();
	}

	public Cliente default1Renovacion() {
		return getPolizaCliente();
	}

	public Compania default2Renovacion() {
		return getPolizaCompania();
	}

	public DetalleTipoPago default6Renovacion() {
		return getPolizaPago();
	}


	public float default8Renovacion() {
		return getRiesgoIntegralComercioRobo();
	}

	public float default9Renovacion() {
		return getRiesgoIntegralComercioCristales();
	}

	public float default10Renovacion() {
		return getRiesgoIntegralComercioIncendioEdificio();
	}

	public float default11Renovacion() {
		return getRiesgoIntegralComercioIncendioContenido();
	}
	
	public float default12Renovacion() {
		return getRiesgoIntegralComercioRc();
	}

	public float default13Renovacion() {
		return getRiesgoIntegralComercioRcl();
	}

	public float default14Renovacion() {
		return getRiesgoIntegralComercioDanioPorAgua();
	}

	public float default15Renovacion() {
		return getRiesgoIntegralComercioRCC();
	}
	
	public String default16Renovacion() {
		return getRiesgoIntegralComercioOtrosNombre();
	}

	public float default17Renovacion() {
		return getRiesgoIntegralComercioOtrosMonto();
	}

	// region > toString, compareTo
	@Override
	public String toString() {
		return ObjectContracts.toString(this, "polizaNumero");
	}

	@Override
	public int compareTo(final PolizaIntegralComercio other) {
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
	ClienteRepository clientesRepository;

	@Inject
	VehiculoRepository vehiculosRepository;

	@Inject
	DetalleTipoPagoRepository detalleTipoPagosRepository;

	@Inject
	CompaniaRepository companiaRepository;

	@Inject
	TipoDeCoberturaRepository tiposDeCoberturasRepository;

	@Inject
	PolizaRepository polizasRepository;

	@Inject
	PolizaIntegralComercioRepository riesgoIntegralComercioRepository;

	// endregion

}