package domainapp.dom.polizaCombinadoFamiliar;

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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
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
import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.ocupacion.Ocupacion;
import domainapp.dom.ocupacion.OcupacionRepository;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoTitular.TipoTitularRepository;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tipoVivienda.TipoViviendaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Polizas"
)
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value="RiesgoCombinadosFamiliares")
public class PolizaCombinadoFamiliar extends Poliza implements Comparable<PolizaCombinadoFamiliar> {

	
	 //region > title
   public TranslatableString title() {
       return TranslatableString.tr("{name}", "name","Poliza Combinado Familiar N°: " + getPolizaNumero());
   }
   //endregion

	// Constructor
	public PolizaCombinadoFamiliar(String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
			String riesgoCombinadosFamiliaresDomicilio, Localidad riesgoCombinadosFamiliaresLocalidad, Ocupacion riesgoCombinadosFamiliaresOcupacion,
			TipoVivienda riesgoCombinadosFamiliaresTipoVivienda, TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			 DetalleTipoPago polizaPago, 
			double polizaImporteTotal) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public PolizaCombinadoFamiliar(
			String polizaNumero,
			Cliente polizaCliente,
			Compania polizaCompania,
			String riesgoCombinadosFamiliaresDomicilio,
			Localidad riesgoCombinadosFamiliaresLocalidad, 
			Ocupacion riesgoCombinadosFamiliaresOcupacion,
			TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			Date polizaFechaEmision,Date polizaFechaVigencia,
			Date polizaFechaVencimiento,
			DetalleTipoPago polizaPago,
			double polizaImporteTotal,
			Poliza riesgoCombinadoFamiliar) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setPolizaEstado(Estado.previgente);
		riesgoCombinadoFamiliar.setPolizaRenovacion(this);
		polizaEstado.actualizarEstado(this);
	}
	
	//Domicilio
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Domicilio")
   @Column(allowsNull = "false")
	private String riesgoCombinadosFamiliaresDomicilio; 
	
	public String getRiesgoCombinadosFamiliaresDomicilio() {
		return riesgoCombinadosFamiliaresDomicilio;
	}

	public void setRiesgoCombinadosFamiliaresDomicilio(String riesgoCombinadosFamiliaresDomicilio) {
		this.riesgoCombinadosFamiliaresDomicilio = riesgoCombinadosFamiliaresDomicilio;
	}
	
	//Localidad
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Localidad")
	@Column(allowsNull = "false", name="localidadId")
	private Localidad riesgoCombinadosFamiliaresLocalidad;

	public Localidad getRiesgoCombinadosFamiliaresLocalidad() {
		return riesgoCombinadosFamiliaresLocalidad;
	}

	public void setRiesgoCombinadosFamiliaresLocalidad(Localidad riesgoCombinadosFamiliaresLocalidad) {
		this.riesgoCombinadosFamiliaresLocalidad = riesgoCombinadosFamiliaresLocalidad;
	}

	//Ocupación
	@Column(name="ocupacionId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Ocupación")
	private Ocupacion riesgoCombinadosFamiliaresOcupacion; 
	
	public Ocupacion getRiesgoCombinadosFamiliaresOcupacion() {
		return riesgoCombinadosFamiliaresOcupacion;
	}

	public void setRiesgoCombinadosFamiliaresOcupacion(Ocupacion riesgoCombinadosFamiliaresOcupacion) {
		this.riesgoCombinadosFamiliaresOcupacion = riesgoCombinadosFamiliaresOcupacion;
	}

	//Tipo Vivienda
	@Column(name="tipoViviendaId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de vivienda")
	private TipoVivienda riesgoCombinadosFamiliaresTipoVivienda; 
	
	public TipoVivienda getRiesgoCombinadosFamiliaresTipoVivienda() {
		return riesgoCombinadosFamiliaresTipoVivienda;
	}

	public void setRiesgoCombinadosFamiliaresTipoVivienda(TipoVivienda riesgoCombinadosFamiliaresTipoVivienda) {
		this.riesgoCombinadosFamiliaresTipoVivienda = riesgoCombinadosFamiliaresTipoVivienda;
	}


	//Tipo Titular
	@Column(name="tipoTitularId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de Titular")
	private TipoTitular riesgoCombinadosFamiliaresTipoTitular; 

	public TipoTitular getRiesgoCombinadosFamiliaresTipoTitular() {
		return riesgoCombinadosFamiliaresTipoTitular;
	}

	public void setRiesgoCombinadosFamiliaresTipoTitular(TipoTitular riesgoCombinadosFamiliaresTipoTitular) {
		this.riesgoCombinadosFamiliaresTipoTitular = riesgoCombinadosFamiliaresTipoTitular;
	}

	//region > delete (action)
   @Action(
           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
   )
   
   //Actualizar PolizaNumero
	public PolizaCombinadoFamiliar actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
   
	//Actualizar Poliza Cliente
   public PolizaCombinadoFamiliar actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Cliente polizaCliente) {
       setPolizasCliente(polizaCliente);
       return this;
   }
   
   public List<Cliente> choices0ActualizarPolizaCliente(){
   	return clientesRepository.listarActivos();
   }
     
   public Cliente default0ActualizarPolizaCliente() {
   	return getPolizaCliente();
   }
   
   //Actualizar polizaCompania
   public PolizaCombinadoFamiliar actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
       actualizarPolizaCompania(polizaCompania);
       return this;
   }
   
   public List<Compania> choices0ActualizarPolizaCompania(){
   	return companiaRepository.listarActivos();
   }
     
   public Compania default0ActualizarPolizaCompania() {
   	return getPolizaCompania();
   }    
   
   //Actualizar poliza Domicilio
	public PolizaCombinadoFamiliar actualizarPolizaDomicilio(@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio){
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		return this;
	}

	public String default0ActualizarPolizaDomicilio() {
		return getRiesgoCombinadosFamiliaresDomicilio();
	}

	// Actualizar poliza Localidad
	public PolizaCombinadoFamiliar actualizarPolizaLocalidad(@ParameterLayout(named = "Localidad") final Localidad riesgoCombinadosFamiliaresLocalidad) {
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		return this;
	}

	public Localidad default0ActualizarPolizaLocalidad() {
		return getRiesgoCombinadosFamiliaresLocalidad();
	}

	// Actualizar poliza Ocupacion
	   public PolizaCombinadoFamiliar actualizarPolizaOcupacion(@ParameterLayout(named="Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion) {
		   actualizarPolizaOcupacion(riesgoCombinadosFamiliaresOcupacion);
	       return this;
	   }
	   
	   public List<Ocupacion> choices0ActualizarPolizaOcupacion(){
	   	return ocupacionesRepository.listarActivos();
	   }
	     
	   public Ocupacion default0ActualizarPolizaOcupacion() {
	   	return getRiesgoCombinadosFamiliaresOcupacion();
	   }   
	   
	
   //Actualizar poliza Tipo Vivienda
   public PolizaCombinadoFamiliar actualizarPolizaTipoVivienda(@ParameterLayout(named="Tipo Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda) {
	   actualizarPolizaTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
       return this;
   }
   
   public List<TipoVivienda> choices0ActualizarPolizaTipoVivienda(){
   	return tiposViviendaRepository.listarActivos();
   }
     
   public TipoVivienda default0ActualizarPolizaTipoVivienda() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
   
   //Actualizar poliza Tipo Titular
   public PolizaCombinadoFamiliar actualizarPolizaTipoTitular(@ParameterLayout(named="Tipo Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular) {
	   actualizarPolizaTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
       return this;
   }
   
   public List<TipoTitular> choices0ActualizarPolizaTipoTitular(){
   	return tipoTitularesRepository.listarActivos();
   }

   public TipoTitular default0ActualizarPolizaTipoTitular() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  

   //Actualizar polizaFechaEmision
	public PolizaCombinadoFamiliar actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
   //Actualizar polizaFechaVigencia
	public PolizaCombinadoFamiliar actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
   //polizaFechaVencimiento
	public PolizaCombinadoFamiliar actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	    
   //polizaPago
   public PolizaCombinadoFamiliar actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago) {
       setPolizaPago(polizaPago);
       return this;
   }
   
   public List<DetalleTipoPago> choices0ActualizarPolizaPago(){
   	return detalleTipoPagosRepository.listarActivos();
   }
     
   public DetalleTipoPago default0ActualizarPolizaPago() {
   	return getPolizaPago();
   }
   
   //polizaFechaBaja
	public PolizaCombinadoFamiliar actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
   
   //polizaMotivoBaja
	public PolizaCombinadoFamiliar actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
   
   //polizaImporteTotal
	public PolizaCombinadoFamiliar actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
   //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
   public PolizaCombinadoFamiliar actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
       setPolizaRenovacion(polizaRenovacion);
       polizaEstado.actualizarEstado(this);
       return this;
   }
   
   public List<Poliza> choices0ActualizarPolizaRenovacion(){
   	return polizasRepository.listar();
   }
     
   public Poliza default0ActualizarPolizaRenovacion() {
   	return getPolizaRenovacion();
   }
   
   public PolizaCombinadoFamiliar borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
   	return this;
   }
   
   //endregion

   //acciones

	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
	@ActionLayout(named="Emitir Renovacion")
	public PolizaCombinadoFamiliar renovacion(
			/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
			/*1*/	            @ParameterLayout(named="Cliente") final Cliente polizaCliente,
			/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
			/*3*/	    		@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
			/*4*/				@ParameterLayout(named="Localidad") final Localidad riesgoCombinadoFamiliarLocalidad,
			/*5*/	            @ParameterLayout(named="Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			/*6*/	            @ParameterLayout(named="Tipo de Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			/*7*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			/*8*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			/*9*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			/*10*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago,
			/*12*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
       return riesgoCombinadosFamiliaresRepository.renovacion(
    		polizaNumero,
       		polizaCliente,
       		polizaCompania,
       		riesgoCombinadosFamiliaresDomicilio,
       		riesgoCombinadoFamiliarLocalidad,
       		riesgoCombinadosFamiliaresOcupacion,
       		riesgoCombinadosFamiliaresTipoVivienda,
       		riesgoCombinadosFamiliaresTipoTitular,
       		polizaFechaEmision,
       		polizaFechaVigencia, 
       		polizaFechaVencimiento,
       		polizaPago,
       		polizaImporteTotal,this);
	}
	
   public List<Cliente> choices1Renovacion(){
   	return clientesRepository.listarActivos();
   }
   
   public List<Compania> choices2Renovacion(){
   	return companiaRepository.listarActivos();
   }	   
   
   public List<Localidad> choices4Renovacion(){
	   	return localidadesRepository.listarActivos();
	   }	    
   
   public List<Ocupacion> choices5Renovacion(){
   	return ocupacionesRepository.listarActivos();
   }
   public List<TipoVivienda> choices6Renovacion(){
	   	return tiposViviendaRepository.listarActivos();
   }
	     
   public List<TipoTitular> choices7Renovacion(){
	   	return tipoTitularesRepository.listarActivos();
	   }
   
   public List<DetalleTipoPago> choices11Renovacion(){
   	return detalleTipoPagosRepository.listarActivos();
   }
   
   public Cliente default1Renovacion() {
   	return getPolizaCliente();
   }
   public String default3Renovacion() {
	   	return getRiesgoCombinadosFamiliaresDomicilio();
	   }

   public Compania default2Renovacion(){
   	return getPolizaCompania();
   }
   
   public Localidad default4Renovacion(){
	   	return getRiesgoCombinadosFamiliaresLocalidad();
	   }
   
   public Ocupacion default5Renovacion() {
   	return getRiesgoCombinadosFamiliaresOcupacion();
   }   
   public TipoVivienda default6Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
	   
   public TipoTitular default7Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  
   public DetalleTipoPago default11Renovacion(){
   	return getPolizaPago();
   }
   
   //region > toString, compareTo
   @Override
   public String toString() {
       return ObjectContracts.toString(this, "polizaNumero");
   }
   @Override
   public int compareTo(final PolizaCombinadoFamiliar other) {
       return ObjectContracts.compare(this, other, "polizaNumero");
   }

   //endregion

   //region > injected dependencies

   @Inject
   RepositoryService repositoryService;

   @Inject
   TitleService titleService;

   @Inject
   MessageService messageService;
   
   @Inject
   ClienteRepository clientesRepository;

   @Inject
   TipoViviendaRepository tiposViviendaRepository;
   
   @Inject
   TipoTitularRepository tipoTitularesRepository;

   @Inject
   OcupacionRepository ocupacionesRepository;
   
   @Inject
   DetalleTipoPagoRepository detalleTipoPagosRepository;
   
   @Inject
   CompaniaRepository companiaRepository;
   
   @Inject
   TipoDeCoberturaRepository tiposDeCoberturasRepository;
   
   @Inject
   PolizaRepository polizasRepository;

   @Inject
   PolizaCombinadoFamiliarRepository riesgoCombinadosFamiliaresRepository;
   
   @Inject
   LocalidadRepository localidadesRepository;
   
   //endregion

	
}
