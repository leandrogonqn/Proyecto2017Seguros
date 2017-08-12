package domainapp.dom.riesgoVidaColectivo;

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
@Discriminator(value="RiesgoCombinadosRiesgosVidaColectivo")
public class RiesgosVidaColectivo extends Polizas implements Comparable<RiesgosVidaColectivo> {

	
	 //region > title
   public TranslatableString title() {
       return TranslatableString.tr("{name}", "name","Poliza VidaColectivo N°: " + getPolizaNumero());
   }
   //endregion

	// Constructor
	public RiesgosVidaColectivo(String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia,
			Date polizaFechaVencimiento, DetalleTipoPagos polizaPago,
			double polizaImporteTotal, float riesgoVidaColectivoMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoVidaColectivoMonto(riesgoVidaColectivoMonto);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public RiesgosVidaColectivo(
			String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
			Date polizaFechaEmision, Date polizaFechaVigencia,
			Date polizaFechaVencimiento, DetalleTipoPagos polizaPago,
			double polizaImporteTotal, 
			Polizas riesgoVidaColectivo,
			float riesgoVidaColectivoMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoVidaColectivoMonto(riesgoVidaColectivoMonto);
		setPolizaEstado(Estado.previgente);
		riesgoVidaColectivo.setPolizaRenovacion(this);
		polizaEstado.actualizarEstado(this);
	}
	
	//Monto
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Monto")
   @Column(allowsNull = "false")
	private float riesgoVidaColectivoMonto; 
	

	public float getRiesgoVidaColectivoMonto() {
		return riesgoVidaColectivoMonto;
	}

	public void setRiesgoVidaColectivoMonto(float riesgoVidaColectivoMonto) {
		this.riesgoVidaColectivoMonto = riesgoVidaColectivoMonto;
	}

//region > delete (action)
   public static class DeleteDomainEvent extends ActionDomainEvent<RiesgosVidaColectivo> {}
   @Action(
           domainEvent = DeleteDomainEvent.class,
           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
   )
   
   //Actualizar PolizaNumero
	public RiesgosVidaColectivo actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
   
	//Actualizar Poliza Cliente
   public RiesgosVidaColectivo actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Clientes polizaCliente) {
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
   public RiesgosVidaColectivo actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Companias polizaCompania) {
       actualizarPolizaCompania(polizaCompania);
       return this;
   }
   
   public List<Companias> choices0ActualizarPolizaCompania(){
   	return companiaRepository.listarActivos();
   }
     
   public Companias default0ActualizarPolizaCompania() {
   	return getPolizaCompania();
   }    
   

   //Actualizar polizaFechaEmision
	public RiesgosVidaColectivo actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
   //Actualizar polizaFechaVigencia
	public RiesgosVidaColectivo actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
	//Actualizar riesgoVidaColectivoMonto
	public RiesgosVidaColectivo actualizarRiesgoVidaColectivoMonto(@ParameterLayout (named="Monto VidaColectivo") final float riesgoVidaColectivoMonto){
		setRiesgoVidaColectivoMonto(riesgoVidaColectivoMonto);
		return(this);
	}
	
	public float default0ActualizarRiesgoVidaColectivoMonto(){
		return getRiesgoVidaColectivoMonto();
	}
	
   //polizaFechaVencimiento
	public RiesgosVidaColectivo actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	
   //polizaPago
   public RiesgosVidaColectivo actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago) {
       setPolizaPago(polizaPago);
       return this;
   }
   
   public List<DetalleTipoPagos> choices0ActualizarPolizaPago(){
   	return detalleTipoPagosRepository.listarActivos();
   }
     
   public DetalleTipoPagos default0ActualizarPolizaPago() {
   	return getPolizaPago();
   }
   
   //polizaFechaBaja
	public RiesgosVidaColectivo actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
   
   //polizaMotivoBaja
	public RiesgosVidaColectivo actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
   
   //polizaImporteTotal
	public RiesgosVidaColectivo actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
   //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
   public RiesgosVidaColectivo actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Polizas polizaRenovacion) {
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
   
   public RiesgosVidaColectivo borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
   	return this;
   }
   
   //endregion

   //acciones

	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
	@ActionLayout(named="Emitir Renovacion")
	public RiesgosVidaColectivo renovacion(
			@ParameterLayout(named="Número") final String polizaNumero,
			@ParameterLayout(named="Cliente") final Clientes polizaCliente,
			@ParameterLayout(named="Compañia") final Companias polizaCompania,
			@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
			@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
			@ParameterLayout(named="Monto") final float riesgoVidaColectivoMonto){
       return riesgosVidaColectivoRepository.renovacion(
    		polizaNumero,
       		polizaCliente,
       		polizaCompania,
       		polizaFechaEmision,
       		polizaFechaVigencia, 
       		polizaFechaVencimiento,
       		polizaPago,
       		polizaImporteTotal,
       		riesgoVidaColectivoMonto,this);
	}
	
   public List<Clientes> choices1Renovacion(){
   	return clientesRepository.listarActivos();
   }
   
   public List<Companias> choices2Renovacion(){
   	return companiaRepository.listarActivos();
   }	    
   
   public List<DetalleTipoPagos> choices6Renovacion(){
   	return detalleTipoPagosRepository.listarActivos();
   }
   
   public Clientes default1Renovacion() {
   	return getPolizaCliente();
   }

   public Companias default2Renovacion(){
   	return getPolizaCompania();
   }
   
   public DetalleTipoPagos default6Renovacion(){
   	return getPolizaPago();
   }
   
   //region > toString, compareTo
   @Override
   public String toString() {
       return ObjectContracts.toString(this, "polizaNumero");
   }
   @Override
   public int compareTo(final RiesgosVidaColectivo other) {
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
   DetalleTipoPagosRepository detalleTipoPagosRepository;
   
   @Inject
   CompaniaRepository companiaRepository;
   
   @Inject
   TiposDeCoberturasRepository tiposDeCoberturasRepository;
   
   @Inject
   PolizasRepository polizasRepository;

   @Inject
   RiesgosVidaColectivoRepository riesgosVidaColectivoRepository;
   
   //endregion

	
}
