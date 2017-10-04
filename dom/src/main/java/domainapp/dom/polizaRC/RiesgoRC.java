package domainapp.dom.polizaRC;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
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
import org.apache.isis.applib.annotation.Parameter;
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
import domainapp.dom.detalleTipoPago.DetalleTipoPagoMenu;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.estado.Estado;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;
import domainapp.dom.polizaART.PolizaART;
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
@Discriminator(value="RiesgoCombinadosRiesgosRC")
public class RiesgoRC extends Poliza implements Comparable<RiesgoRC>{
	 //region > title
	   public TranslatableString title() {
	       return TranslatableString.tr("{name}", "name","Poliza RC N°: " + getPolizaNumero());
	   }
	   //endregion

		// Constructor
		public RiesgoRC(String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, float riesgoRCMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoRCMonto(riesgoRCMonto);
			setPolizaEstado(Estado.previgente);
			polizaEstado.actualizarEstado(this);
		}
		
		public RiesgoRC(
				String polizaNumero, Cliente polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, 
				Poliza riesgoRC,
				float riesgoRCMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoRCMonto(riesgoRCMonto);
			setPolizaEstado(Estado.previgente);
			riesgoRC.setPolizaRenovacion(this);
			polizaEstado.actualizarEstado(this);
		}
		
		//Monto
	   @Property(editing = Editing.DISABLED)
		@PropertyLayout(named="Monto")
	   @Column(allowsNull = "false")
		private float riesgoRCMonto; 
		
	public float getRiesgoRCMonto() {
		return riesgoRCMonto;
		}

	public void setRiesgoRCMonto(float riesgoRCMonto) {
		this.riesgoRCMonto = riesgoRCMonto;
		}

	//region > delete (action)
	   @Action(
	           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
	   )
	   
	   //Actualizar PolizaNumero
		public RiesgoRC actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
			setPolizaNumero(polizaNumero);
			return this;
		}

		public String default0ActualizarPolizaNumero(){
			return getPolizaNumero();
		}
	   
		//Actualizar Poliza Cliente
	   public RiesgoRC actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Cliente polizaCliente) {
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
	   public RiesgoRC actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
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
		public RiesgoRC actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
			setPolizaFechaEmision(polizaFechaEmision);
			return this;
		}

		public Date default0ActualizarPolizaFechaEmision(){
			return getPolizaFechaEmision();
		}
		
	   //Actualizar polizaFechaVigencia
		public RiesgoRC actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
			setPolizaFechaVigencia(polizaFechaVigencia);
			polizaEstado.actualizarEstado(this);
			JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
			return this;
		}

		public Date default0ActualizarPolizaFechaVigencia(){
			return getPolizaFechaVigencia();
		}

		public String validateActualizarPolizaFechaVigencia(final Date polizaFechaVigencia) {

			if (polizaFechaVigencia.after(this.getPolizaFechaVencimiento())) {
				return "La fecha de vigencia es mayor a la de vencimiento";
			}
			return "";
		}
		
	   //polizaFechaVencimiento
		public RiesgoRC actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			polizaEstado.actualizarEstado(this);
			return this;
		}

		public Date default0ActualizarPolizaFechaVencimiento(){
			return getPolizaFechaVencimiento();
		}
		
		public String validateActualizarPolizaFechaVencimiento(final Date polizaFechaVencimiento){
			if (this.getPolizaFechaVigencia().after(polizaFechaVencimiento)){
				return "La fecha de vencimiento es menor a la de vigencia";
			}
			return "";
		}
		
	    //polizaPago
	    public RiesgoRC actualizarPolizaPago(
	    		@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
				@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago) {
	        setPolizaTipoDePago(polizaTipoDePago);
	    	setPolizaPago(polizaPago);
	        return this;
	    }
	    
	    public List<DetalleTipoPago> choices1ActualizarPolizaPago(			
	 			final TipoPago polizaTipoDePago,
	 			final DetalleTipoPago polizaPago) {
	 		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	    }
	    
	    public TipoPago default0ActualizarPolizaPago() {
	    	return getPolizaTipoDePago();
	    }
	      
	    public DetalleTipoPago default1ActualizarPolizaPago() {
	    	return getPolizaPago();
	    }
	   
	   //polizaFechaBaja
		public RiesgoRC actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
			setPolizaFechaBaja(polizaFechaBaja);
			return this;
		}

		public Date default0ActualizarPolizaFechaBaja(){
			return getPolizaFechaBaja();
		}    
	   
	   //polizaMotivoBaja
		public RiesgoRC actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
			setPolizaMotivoBaja(polizaMotivoBaja);
			return this;
		}

		public String default0ActualizarPolizaMotivoBaja(){
			return getPolizaMotivoBaja();
		}    
	   
	   //polizaImporteTotal
		public RiesgoRC actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
			setPolizaImporteTotal(polizaImporteTotal);
			return this;
		}

		public double default0ActualizarPolizaImporteTotal(){
			return getPolizaImporteTotal();
		}    
		
		// riesgoARTMonto
		public RiesgoRC actualizarRiesgoRCMonto(
				@ParameterLayout(named = "Monto asegurado") final float riesgoRCMonto) {
			setRiesgoRCMonto(riesgoRCMonto);
			return this;
		}

		public float default0ActualizarRiesgoRCMonto() {
			return getRiesgoRCMonto();
		}

	   //polizaRenovacion
		@ActionLayout(named="Actualizar Renovacion")
	   public RiesgoRC actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
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
	   
	   public RiesgoRC borrarPolizaRenovacion() {
			setPolizaRenovacion(null);
			polizaEstado.actualizarEstado(this);
	   	return this;
	   }
	   
	   //endregion

	   //acciones

		@Action(invokeOn=InvokeOn.OBJECT_ONLY)
		@ActionLayout(named="Emitir Renovacion")
		public RiesgoRC renovacion(
				@ParameterLayout(named="Número") final String polizaNumero,
				@ParameterLayout(named="Cliente") final Cliente polizaCliente,
				@ParameterLayout(named="Compañia") final Compania polizaCompania,
				@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
				@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Monto") final float riesgoRCMonto){
	       return riesgosRCRepository.renovacion(
	    		polizaNumero,
	       		polizaCliente,
	       		polizaCompania,
	       		polizaFechaEmision,
	       		polizaFechaVigencia, 
	       		polizaFechaVencimiento,
	       		polizaTipoDePago,
	       		polizaPago,
	       		polizaImporteTotal,
	       		riesgoRCMonto,this);
		}
		
	   public List<Cliente> choices1Renovacion(){
	   	return clientesRepository.listarActivos();
	   }
	   
	   public List<Compania> choices2Renovacion(){
	   	return companiaRepository.listarActivos();
	   }	    
	   
	   public List<DetalleTipoPago> choices7Renovacion(			
				final String polizaNumero,
				final Cliente polizaCliente,
				final Compania polizaCompania,
				final Date polizaFechaEmision,
				final Date polizaFechaVigencia,
				final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago,
				final DetalleTipoPago polizaPago,
				final double polizaImporteTotal,
				final float riesgoRCMonto) {
			return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	   }
	   
	   public Cliente default1Renovacion() {
	   	return getPolizaCliente();
	   }

	   public TipoPago default6Renovacion(){
		   	return getPolizaTipoDePago();
		   }
	   
	   public DetalleTipoPago default7Renovacion(){
	   	return getPolizaPago();
	   }
	   
	   //region > toString, compareTo
	   @Override
	   public String toString() {
	       return ObjectContracts.toString(this, "polizaNumero");
	   }
	   @Override
	   public int compareTo(final RiesgoRC other) {
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
	   DetalleTipoPagoMenu detalleTipoPagoMenu;
	   
	   @Inject
	   CompaniaRepository companiaRepository;
	   
	   @Inject
	   TipoDeCoberturaRepository tiposDeCoberturasRepository;
	   
	   @Inject
	   PolizaRepository polizasRepository;

	   @Inject
	   RiesgoRCRepository riesgosRCRepository;
	   
	   //endregion

}
