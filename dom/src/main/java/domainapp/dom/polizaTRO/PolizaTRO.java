package domainapp.dom.polizaTRO;

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
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;
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
@Discriminator(value="RiesgoCombinadosRiesgosTRO")
public class PolizaTRO extends Poliza implements Comparable <PolizaTRO> {

	 //region > title
	   public TranslatableString title() {
	       return TranslatableString.tr("{name}", "name","Poliza TRO N°: " + getPolizaNumero());
	   }
	   //endregion

		// Constructor
		public PolizaTRO(String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, DetalleTipoPago polizaPago,
				double polizaImporteTotal, float riesgoTROMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoTROMonto(riesgoTROMonto);
			setPolizaEstado(Estado.previgente);
			polizaEstado.actualizarEstado(this);
		}
		
		public PolizaTRO(
				String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, DetalleTipoPago polizaPago,
				double polizaImporteTotal, 
				Poliza riesgoTRO,
				float riesgoTROMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoTROMonto(riesgoTROMonto);
			setPolizaEstado(Estado.previgente);
			riesgoTRO.setPolizaRenovacion(this);
			polizaEstado.actualizarEstado(this);
		}
		
		//Monto
	   @Property(editing = Editing.DISABLED)
		@PropertyLayout(named="Monto")
	   @Column(allowsNull = "false")
		private float riesgoTROMonto; 
		
	public float getRiesgoTROMonto() {
		return riesgoTROMonto;
	}

	public void setRiesgoTROMonto(float riesgoTROMonto) {
		this.riesgoTROMonto = riesgoTROMonto;
	}

	//region > delete (action)
	   @Action(
	           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
	   )
	   
	   //Actualizar PolizaNumero
		public PolizaTRO actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
			setPolizaNumero(polizaNumero);
			return this;
		}

		public String default0ActualizarPolizaNumero(){
			return getPolizaNumero();
		}
	   
		//Actualizar Poliza Cliente
	   public PolizaTRO actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Cliente polizaCliente) {
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
	   public PolizaTRO actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
	       actualizarPolizaCompania(polizaCompania);
	       return this;
	   }
	   
	   public List<Compania> choices0ActualizarPolizaCompania(){
	   	return companiaRepository.listarActivos();
	   }
	     
	   public Compania default0ActualizarPolizaCompania() {
	   	return getPolizaCompania();
	   }    
	   

	   //Actualizar polizaFechaEmision
		public PolizaTRO actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
			setPolizaFechaEmision(polizaFechaEmision);
			return this;
		}

		public Date default0ActualizarPolizaFechaEmision(){
			return getPolizaFechaEmision();
		}
		
	   //Actualizar polizaFechaVigencia
		public PolizaTRO actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
			setPolizaFechaVigencia(polizaFechaVigencia);
			polizaEstado.actualizarEstado(this);
			JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
			return this;
		}

		public Date default0ActualizarPolizaFechaVigencia(){
			return getPolizaFechaVigencia();
		}
		
	   //polizaFechaVencimiento
		public PolizaTRO actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			polizaEstado.actualizarEstado(this);
			return this;
		}

		public Date default0ActualizarPolizaFechaVencimiento(){
			return getPolizaFechaVencimiento();
		}
		
	   //polizaPago
	   public PolizaTRO actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago) {
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
		public PolizaTRO actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
			setPolizaFechaBaja(polizaFechaBaja);
			return this;
		}

		public Date default0ActualizarPolizaFechaBaja(){
			return getPolizaFechaBaja();
		}    
	   
	   //polizaMotivoBaja
		public PolizaTRO actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
			setPolizaMotivoBaja(polizaMotivoBaja);
			return this;
		}

		public String default0ActualizarPolizaMotivoBaja(){
			return getPolizaMotivoBaja();
		}    
	   
	   //polizaImporteTotal
		public PolizaTRO actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
			setPolizaImporteTotal(polizaImporteTotal);
			return this;
		}

		public double default0ActualizarPolizaImporteTotal(){
			return getPolizaImporteTotal();
		}    
		
		// riesgoARTMonto
		public PolizaTRO actualizarRiesgoRCMonto(
				@ParameterLayout(named = "Monto asegurado") final float riesgoTROMonto) {
			setRiesgoTROMonto(riesgoTROMonto);
			return this;
		}

		public float default0ActualizarRiesgoRCMonto() {
			return getRiesgoTROMonto();
		}

	   //polizaRenovacion
		@ActionLayout(named="Actualizar Renovacion")
	   public PolizaTRO actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
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
	   
	   public PolizaTRO borrarPolizaRenovacion() {
			setPolizaRenovacion(null);
			polizaEstado.actualizarEstado(this);
	   	return this;
	   }
	   
	   //endregion

	   //acciones

		@Action(invokeOn=InvokeOn.OBJECT_ONLY)
		@ActionLayout(named="Emitir Renovacion")
		public PolizaTRO renovacion(
				@ParameterLayout(named="Número") final String polizaNumero,
				@ParameterLayout(named="Cliente") final Cliente polizaCliente,
				@ParameterLayout(named="Compañia") final Compania polizaCompania,
				@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Monto") final float riesgoTROMonto){
	       return riesgosTRORepository.renovacion(
	    		polizaNumero,
	       		polizaCliente,
	       		polizaCompania,
	       		polizaFechaEmision,
	       		polizaFechaVigencia, 
	       		polizaFechaVencimiento,
	       		polizaPago,
	       		polizaImporteTotal,
	       		riesgoTROMonto,this);
		}
		
	   public List<Cliente> choices1Renovacion(){
	   	return clientesRepository.listarActivos();
	   }
	   
	   public List<Compania> choices2Renovacion(){
	   	return companiaRepository.listarActivos();
	   }	    
	   
	   public List<DetalleTipoPago> choices6Renovacion(){
	   	return detalleTipoPagosRepository.listarActivos();
	   }
	   
	   public Cliente default1Renovacion() {
	   	return getPolizaCliente();
	   }

	   public Compania default2Renovacion(){
	   	return getPolizaCompania();
	   }
	   
	   public DetalleTipoPago default6Renovacion(){
	   	return getPolizaPago();
	   }
	   
	   //region > toString, compareTo
	   @Override
	   public String toString() {
	       return ObjectContracts.toString(this, "polizaNumero");
	   }
	   @Override
	   public int compareTo(final PolizaTRO other) {
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
	   DetalleTipoPagoRepository detalleTipoPagosRepository;
	   
	   @Inject
	   CompaniaRepository companiaRepository;
	   
	   @Inject
	   TipoDeCoberturaRepository tiposDeCoberturasRepository;
	   
	   @Inject
	   PolizaRepository polizasRepository;

	   @Inject
	   PolizaTRORepository riesgosTRORepository;
	   
	   //endregion

}