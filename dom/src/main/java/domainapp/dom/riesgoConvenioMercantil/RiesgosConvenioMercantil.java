package domainapp.dom.riesgoConvenioMercantil;

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
import domainapp.dom.poliza.Polizas;
import domainapp.dom.poliza.PolizasRepository;
import domainapp.dom.riesgoRC.RiesgosRC;
import domainapp.dom.riesgoRC.RiesgosRCRepository;
import domainapp.dom.riesgoRC.RiesgosRC.DeleteDomainEvent;
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
@Discriminator(value="RiesgoCombinadosRiesgosConvenioMercantil")
public class RiesgosConvenioMercantil extends Polizas implements Comparable<RiesgosConvenioMercantil>{
	
	 //region > title
	   public TranslatableString title() {
	       return TranslatableString.tr("{name}", "name","Poliza RC N°: " + getPolizaNumero());
	   }
	   //endregion

		// Constructor
		public RiesgosConvenioMercantil(String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, DetalleTipoPagos polizaPago,
				double polizaImporteTotal, float riesgoConvenioMercantilMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoConvenioMercantilMonto(riesgoConvenioMercantilMonto);
			setPolizaEstado(Estado.previgente);
			polizaEstado.actualizarEstado(this);
		}
		
		public RiesgosConvenioMercantil(
				String polizaNumero, Clientes polizaCliente, Companias polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, DetalleTipoPagos polizaPago,
				double polizaImporteTotal, 
				Polizas riesgoRC,
				float riesgoConvenioMercantilMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoConvenioMercantilMonto(riesgoConvenioMercantilMonto);
			setPolizaEstado(Estado.previgente);
			riesgoRC.setPolizaRenovacion(this);
			polizaEstado.actualizarEstado(this);
		}
		
		//Monto
	   @Property(editing = Editing.DISABLED)
		@PropertyLayout(named="Monto")
	   @Column(allowsNull = "false")
		private float riesgoConvenioMercantilMonto; 
		
	public float getRiesgoConvenioMercantilMonto() {
		return riesgoConvenioMercantilMonto;
	}

	public void setRiesgoConvenioMercantilMonto(float riesgoConvenioMercantilMonto) {
		this.riesgoConvenioMercantilMonto = riesgoConvenioMercantilMonto;
	}

	//region > delete (action)
	   public static class DeleteDomainEvent extends ActionDomainEvent<RiesgosRC> {}
	   @Action(
	           domainEvent = DeleteDomainEvent.class,
	           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
	   )
	   
	   //Actualizar PolizaNumero
		public RiesgosConvenioMercantil actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
			setPolizaNumero(polizaNumero);
			return this;
		}

		public String default0ActualizarPolizaNumero(){
			return getPolizaNumero();
		}
	   
		//Actualizar Poliza Cliente
	   public RiesgosConvenioMercantil actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Clientes polizaCliente) {
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
	   public RiesgosConvenioMercantil actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Companias polizaCompania) {
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
		public RiesgosConvenioMercantil actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
			setPolizaFechaEmision(polizaFechaEmision);
			return this;
		}

		public Date default0ActualizarPolizaFechaEmision(){
			return getPolizaFechaEmision();
		}
		
	   //Actualizar polizaFechaVigencia
		public RiesgosConvenioMercantil actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
			setPolizaFechaVigencia(polizaFechaVigencia);
			polizaEstado.actualizarEstado(this);
			JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
			return this;
		}

		public Date default0ActualizarPolizaFechaVigencia(){
			return getPolizaFechaVigencia();
		}
		
	   //polizaFechaVencimiento
		public RiesgosConvenioMercantil actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			polizaEstado.actualizarEstado(this);
			return this;
		}

		public Date default0ActualizarPolizaFechaVencimiento(){
			return getPolizaFechaVencimiento();
		}
		
	   //polizaPago
	   public RiesgosConvenioMercantil actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago) {
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
		public RiesgosConvenioMercantil actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
			setPolizaFechaBaja(polizaFechaBaja);
			return this;
		}

		public Date default0ActualizarPolizaFechaBaja(){
			return getPolizaFechaBaja();
		}    
	   
	   //polizaMotivoBaja
		public RiesgosConvenioMercantil actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
			setPolizaMotivoBaja(polizaMotivoBaja);
			return this;
		}

		public String default0ActualizarPolizaMotivoBaja(){
			return getPolizaMotivoBaja();
		}    
	   
	   //polizaImporteTotal
		public RiesgosConvenioMercantil actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
			setPolizaImporteTotal(polizaImporteTotal);
			return this;
		}

		public double default0ActualizarPolizaImporteTotal(){
			return getPolizaImporteTotal();
		}    
		
		// riesgoARTMonto
		public RiesgosConvenioMercantil actualizarRiesgoRCMonto(
				@ParameterLayout(named = "Monto asegurado") final float riesgoConvenioMercantilMonto) {
			setRiesgoConvenioMercantilMonto(riesgoConvenioMercantilMonto);
			return this;
		}

		public float default0ActualizarRiesgoRCMonto() {
			return getRiesgoConvenioMercantilMonto();
		}

	   //polizaRenovacion
		@ActionLayout(named="Actualizar Renovacion")
	   public RiesgosConvenioMercantil actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Polizas polizaRenovacion) {
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
	   
	   public RiesgosConvenioMercantil borrarPolizaRenovacion() {
			setPolizaRenovacion(null);
			polizaEstado.actualizarEstado(this);
	   	return this;
	   }
	   
	   //endregion

	   //acciones

		@Action(invokeOn=InvokeOn.OBJECT_ONLY)
		@ActionLayout(named="Emitir Renovacion")
		public RiesgosConvenioMercantil renovacion(
				@ParameterLayout(named="Número") final String polizaNumero,
				@ParameterLayout(named="Cliente") final Clientes polizaCliente,
				@ParameterLayout(named="Compañia") final Companias polizaCompania,
				@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Monto") final float riesgoConvenioMercantilMonto){
	       return riesgosConvenioMercantilRepository.renovacion(
	    		polizaNumero,
	       		polizaCliente,
	       		polizaCompania,
	       		polizaFechaEmision,
	       		polizaFechaVigencia, 
	       		polizaFechaVencimiento,
	       		polizaPago,
	       		polizaImporteTotal,
	       		riesgoConvenioMercantilMonto,this);
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
	   public int compareTo(final RiesgosConvenioMercantil other) {
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
	   RiesgosConvenioMercantilRepository riesgosConvenioMercantilRepository;
	   
	   //endregion

}
