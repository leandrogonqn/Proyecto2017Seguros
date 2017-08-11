package domainapp.dom.riesgoCombinadoFamiliar;

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
import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagosRepository;
import domainapp.dom.estado.Estado;
import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.ocupacion.OcupacionesRepository;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.poliza.PolizasRepository;
import domainapp.dom.tipoTitular.TipoTitulares;
import domainapp.dom.tipoTitular.TipoTitularesRepository;
import domainapp.dom.tipoVivienda.TiposVivienda;
import domainapp.dom.tipoVivienda.TiposViviendaRepository;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;

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
public class RiesgoCombinadosFamiliares extends Polizas implements Comparable<RiesgoCombinadosFamiliares> {

	
	 //region > title
   public TranslatableString title() {
       return TranslatableString.tr("{name}", "name","Poliza Combinado Familiar N°: " + getPolizaNumero());
   }
   //endregion

	// Constructor
	public RiesgoCombinadosFamiliares(String polizaNumero,Clientes polizaCliente,Companias polizaCompania,String riesgoCombinadosFamiliaresDomicilio,Ocupaciones riesgoCombinadosFamiliaresOcupacion,TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,TipoTitulares riesgoCombinadosFamiliaresTipoTitular,Date polizaFechaEmision,Date polizaFechaVigencia,Date polizaFechaVencimiento,Date polizaFechaVencimientoPago, DetalleTipoPagos polizaPago,boolean polizaAlertaVencimientoPago,double polizaImporteTotal) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		setPolizaPago(polizaPago);
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public RiesgoCombinadosFamiliares(
			String polizaNumero,
			Clientes polizaCliente,
			Companias polizaCompania,
			String riesgoCombinadosFamiliaresDomicilio,
			Ocupaciones riesgoCombinadosFamiliaresOcupacion,
			TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
			TipoTitulares riesgoCombinadosFamiliaresTipoTitular,
			Date polizaFechaEmision,Date polizaFechaVigencia,
			Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, 
			DetalleTipoPagos polizaPago,
			boolean polizaAlertaVencimientoPago,
			double polizaImporteTotal,
			Polizas riesgoCombinadoFamiliar) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		setPolizaPago(polizaPago);
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
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
	private String riesgoCombinadosFamiliaresLocalidad;

	public String getRiesgoCombinadosFamiliaresLocalidad() {
		return riesgoCombinadosFamiliaresLocalidad;
	}

	public void setRiesgoCombinadosFamiliaresLocalidad(String riesgoCombinadosFamiliaresLocalidad) {
		this.riesgoCombinadosFamiliaresLocalidad = riesgoCombinadosFamiliaresLocalidad;
	}

	//Ocupación
	@Column(name="ocupacionId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Ocupación")
	private Ocupaciones riesgoCombinadosFamiliaresOcupacion; 
	
	public Ocupaciones getRiesgoCombinadosFamiliaresOcupacion() {
		return riesgoCombinadosFamiliaresOcupacion;
	}

	public void setRiesgoCombinadosFamiliaresOcupacion(Ocupaciones riesgoCombinadosFamiliaresOcupacion) {
		this.riesgoCombinadosFamiliaresOcupacion = riesgoCombinadosFamiliaresOcupacion;
	}

	//Tipo Vivienda
	@Column(name="tipoViviendaId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de vivienda")
	private TiposVivienda riesgoCombinadosFamiliaresTipoVivienda; 
	
	public TiposVivienda getRiesgoCombinadosFamiliaresTipoVivienda() {
		return riesgoCombinadosFamiliaresTipoVivienda;
	}

	public void setRiesgoCombinadosFamiliaresTipoVivienda(TiposVivienda riesgoCombinadosFamiliaresTipoVivienda) {
		this.riesgoCombinadosFamiliaresTipoVivienda = riesgoCombinadosFamiliaresTipoVivienda;
	}


	//Tipo Titular
	@Column(name="tipoTitularId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de Titular")
	private TipoTitulares riesgoCombinadosFamiliaresTipoTitular; 

	public TipoTitulares getRiesgoCombinadosFamiliaresTipoTitular() {
		return riesgoCombinadosFamiliaresTipoTitular;
	}

	public void setRiesgoCombinadosFamiliaresTipoTitular(TipoTitulares riesgoCombinadosFamiliaresTipoTitular) {
		this.riesgoCombinadosFamiliaresTipoTitular = riesgoCombinadosFamiliaresTipoTitular;
	}

	//region > delete (action)
   public static class DeleteDomainEvent extends ActionDomainEvent<RiesgoCombinadosFamiliares> {}
   @Action(
           domainEvent = DeleteDomainEvent.class,
           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
   )
   
   //Actualizar PolizaNumero
	public RiesgoCombinadosFamiliares actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
   
	//Actualizar Poliza Cliente
   public RiesgoCombinadosFamiliares actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Clientes polizaCliente) {
       setPolizasCliente(polizaCliente);
       return this;
   }
   
   public List<Clientes> choices0ActualizarPolizaCliente(){
   	return clientesRepository.listarActivos();
   }
     
   public Clientes default0ActualizarPolizaCliente() {
   	return getPolizaCliente();
   }
   
   //Actualizar polizaCompania
   public RiesgoCombinadosFamiliares actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Companias polizaCompania) {
       actualizarPolizaCompania(polizaCompania);
       return this;
   }
   
   public List<Companias> choices0ActualizarPolizaCompania(){
   	return companiaRepository.listarActivos();
   }
     
   public Companias default0ActualizarPolizaCompania() {
   	return getPolizaCompania();
   }    
   
   //Actualizar poliza Domicilio
	public RiesgoCombinadosFamiliares actualizarPolizaDomicilio(@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio){
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		return this;
	}

	public String default0ActualizarPolizaDomicilio(){
		return getRiesgoCombinadosFamiliaresDomicilio();
	}
	//Actualizar poliza Ocupacion
	   public RiesgoCombinadosFamiliares actualizarPolizaOcupacion(@ParameterLayout(named="Ocupación") final Ocupaciones riesgoCombinadosFamiliaresOcupacion) {
		   actualizarPolizaOcupacion(riesgoCombinadosFamiliaresOcupacion);
	       return this;
	   }
	   
	   public List<Ocupaciones> choices0ActualizarPolizaOcupacion(){
	   	return ocupacionesRepository.listarActivos();
	   }
	     
	   public Ocupaciones default0ActualizarPolizaOcupacion() {
	   	return getRiesgoCombinadosFamiliaresOcupacion();
	   }   
	   
	
   //Actualizar poliza Tipo Vivienda
   public RiesgoCombinadosFamiliares actualizarPolizaTipoVivienda(@ParameterLayout(named="Tipo Vivienda") final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda) {
	   actualizarPolizaTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
       return this;
   }
   
   public List<TiposVivienda> choices0ActualizarPolizaTipoVivienda(){
   	return tiposViviendaRepository.listarActivos();
   }
     
   public TiposVivienda default0ActualizarPolizaTipoVivienda() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
   
   //Actualizar poliza Tipo Titular
   public RiesgoCombinadosFamiliares actualizarPolizaTipoTitular(@ParameterLayout(named="Tipo Titular") final TipoTitulares riesgoCombinadosFamiliaresTipoTitular) {
	   actualizarPolizaTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
       return this;
   }
   
   public List<TipoTitulares> choices0ActualizarPolizaTipoTitular(){
   	return tipoTitularesRepository.listarActivos();
   }

   public TipoTitulares default0ActualizarPolizaTipoTitular() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  

   //Actualizar polizaFechaEmision
	public RiesgoCombinadosFamiliares actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
   //Actualizar polizaFechaVigencia
	public RiesgoCombinadosFamiliares actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
   //polizaFechaVencimiento
	public RiesgoCombinadosFamiliares actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	    
   //polizaFechaVencimientoPago
	public RiesgoCombinadosFamiliares actualizarPolizaFechaVencimientoPago(@ParameterLayout(named="Fecha Vencimiento de Pago") final Date polizaFechaVencimientoPago){
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimientoPago(){
		return getPolizaFechaVencimientoPago();
	}    
	
   //polizaPago
   public RiesgoCombinadosFamiliares actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago) {
       setPolizaPago(polizaPago);
       return this;
   }
   
   public List<DetalleTipoPagos> choices0ActualizarPolizaPago(){
   	return detalleTipoPagosRepository.listarActivos();
   }
     
   public DetalleTipoPagos default0ActualizarPolizaPago() {
   	return getPolizaPago();
   }
	
   //polizaAlertaVencimientoPago
	public RiesgoCombinadosFamiliares actualizarPolizaAlertaVencimientoPago(@ParameterLayout(named="Alerta Vencimiento de Pago") final boolean polizaAlertaVencimientoPago){
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		return this;
	}

	public boolean default0ActualizarPolizaAlertaVencimientoPago(){
		return getPolizaAlertaVencimientoPago();
	}    
   
   //polizaFechaBaja
	public RiesgoCombinadosFamiliares actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
   
   //polizaMotivoBaja
	public RiesgoCombinadosFamiliares actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
   
   //polizaImporteTotal
	public RiesgoCombinadosFamiliares actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
   //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
   public RiesgoCombinadosFamiliares actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Polizas polizaRenovacion) {
       setPolizaRenovacion(polizaRenovacion);
       polizaEstado.actualizarEstado(this);
       return this;
   }
   
   public List<Polizas> choices0ActualizarPolizaRenovacion(){
   	return polizasRepository.listar();
   }
     
   public Polizas default0ActualizarPolizaRenovacion() {
   	return getPolizaRenovacion();
   }
   
   public RiesgoCombinadosFamiliares borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
   	return this;
   }
   
   //endregion

   //acciones

	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
	@ActionLayout(named="Emitir Renovacion")
	public RiesgoCombinadosFamiliares renovacion(
			/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
			/*1*/	            @ParameterLayout(named="Cliente") final Clientes polizaCliente,
			/*2*/	            @ParameterLayout(named="Compañia") final Companias polizaCompania,
			/*3*/	    		@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
			/*4*/	            @ParameterLayout(named="Ocupación") final Ocupaciones riesgoCombinadosFamiliaresOcupacion,
			/*5*/	            @ParameterLayout(named="Tipo de Vivienda") final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
			/*6*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,
			/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			/*10*/				@ParameterLayout(named="Fecha Vencimiento Pago") final Date polizaFechaVencimientoPago,
			/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
			/*12*/				@ParameterLayout(named="Alerta Vencimiento Pago") final boolean polizaAlertaVencimientoPago,
			/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
       return riesgoCombinadosFamiliaresRepository.renovacion(
    		polizaNumero,
       		polizaCliente,
       		polizaCompania,
       		riesgoCombinadosFamiliaresDomicilio,
       		riesgoCombinadosFamiliaresOcupacion,
       		riesgoCombinadosFamiliaresTipoVivienda,
       		riesgoCombinadosFamiliaresTipoTitular,
       		polizaFechaEmision,
       		polizaFechaVigencia, 
       		polizaFechaVencimiento,
       		polizaFechaVencimientoPago, 
       		polizaPago,
       		polizaAlertaVencimientoPago,
       		polizaImporteTotal,this);
	}
	
   public List<Clientes> choices1Renovacion(){
   	return clientesRepository.listarActivos();
   }
   
   public List<Companias> choices2Renovacion(){
   	return companiaRepository.listarActivos();
   }	    
   
   public List<Ocupaciones> choices4Renovacion(){
   	return ocupacionesRepository.listarActivos();
   }
   public List<TiposVivienda> choices5Renovacion(){
	   	return tiposViviendaRepository.listarActivos();
   }
	     
   public List<TipoTitulares> choices6Renovacion(){
	   	return tipoTitularesRepository.listarActivos();
	   }
   
   public List<DetalleTipoPagos> choices11Renovacion(){
   	return detalleTipoPagosRepository.listarActivos();
   }
   
   public Clientes default1Renovacion() {
   	return getPolizaCliente();
   }
   public String default3Renovacion() {
	   	return getRiesgoCombinadosFamiliaresDomicilio();
	   }

   public Companias default2Renovacion(){
   	return getPolizaCompania();
   }
   
   public Ocupaciones default4Renovacion() {
   	return getRiesgoCombinadosFamiliaresOcupacion();
   }   
   public TiposVivienda default5Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
	   
   public TipoTitulares default6Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  
   public DetalleTipoPagos default11Renovacion(){
   	return getPolizaPago();
   }
   
   public boolean default12Renovacion(){
   	return getPolizaAlertaVencimientoPago();
   }
   
   //region > toString, compareTo
   @Override
   public String toString() {
       return ObjectContracts.toString(this, "polizaNumero");
   }
   @Override
   public int compareTo(final RiesgoCombinadosFamiliares other) {
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
   ClientesRepository clientesRepository;

   @Inject
   TiposViviendaRepository tiposViviendaRepository;
   
   @Inject
   TipoTitularesRepository tipoTitularesRepository;

   @Inject
   OcupacionesRepository ocupacionesRepository;
   
   @Inject
   DetalleTipoPagosRepository detalleTipoPagosRepository;
   
   @Inject
   CompaniaRepository companiaRepository;
   
   @Inject
   TiposDeCoberturasRepository tiposDeCoberturasRepository;
   
   @Inject
   PolizasRepository polizasRepository;

   @Inject
   RiesgoCombinadosFamiliaresRepository riesgoCombinadosFamiliaresRepository;
   
   //endregion

	
}
