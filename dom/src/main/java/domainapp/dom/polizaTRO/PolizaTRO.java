package domainapp.dom.polizaTRO;

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
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;
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
@Discriminator(value="RiesgoCombinadosRiesgosTRO")
public class PolizaTRO extends Poliza implements Comparable <PolizaTRO> {

	 //region > title
	   public TranslatableString title() {
	       return TranslatableString.tr("{name}", "name","Poliza TRO N°: " + getPolizaNumero());
	   }
	   //endregion

		// Constructor
		public PolizaTRO(String polizaNumero, Persona polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, float riesgoTROMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoTROMonto(riesgoTROMonto);
			setPolizaEstado(Estado.previgente);
			polizaEstado.actualizarEstado(this);
		}
		
		public PolizaTRO(
				String polizaNumero, Persona polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, 
				Poliza riesgoTRO,
				float riesgoTROMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
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
	   public PolizaTRO actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Persona polizaCliente) {
	       setPolizasCliente(polizaCliente);
	       return this;
	   }
	   
	   public List<Persona> choices0ActualizarPolizaCliente(){
	   	return personaRepository.listarActivos();
	   }
	     
	   public Persona default0ActualizarPolizaCliente() {
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

		public String validateActualizarPolizaFechaVigencia(final Date polizaFechaVigencia) {

			if (polizaFechaVigencia.after(this.getPolizaFechaVencimiento())) {
				return "La fecha de vigencia es mayor a la de vencimiento";
			}
			return "";
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
		
		public String validateActualizarPolizaFechaVencimiento(final Date polizaFechaVencimiento){
			if (this.getPolizaFechaVigencia().after(polizaFechaVencimiento)){
				return "La fecha de vencimiento es menor a la de vigencia";
			}
			return "";
		}
		
	    //polizaPago
	    public PolizaTRO actualizarPolizaPago(
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
		public PolizaTRO actualizarRiesgoTROMonto(
				@ParameterLayout(named = "Monto asegurado") final float riesgoTROMonto) {
			setRiesgoTROMonto(riesgoTROMonto);
			return this;
		}

		public float default0ActualizarRiesgoTROMonto() {
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
				@ParameterLayout(named="Cliente") final Persona polizaCliente,
				@ParameterLayout(named="Compañia") final Compania polizaCompania,
				@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
				@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Monto") final float riesgoTROMonto){
	       return riesgosTRORepository.renovacion(
	    		polizaNumero,
	       		polizaCliente,
	       		polizaCompania,
	       		polizaFechaEmision,
	       		polizaFechaVigencia, 
	       		polizaFechaVencimiento,
	       		polizaTipoDePago,
	       		polizaPago,
	       		polizaImporteTotal,
	       		riesgoTROMonto,this);
		}
		
	   public List<Persona> choices1Renovacion(){
	   	return personaRepository.listarActivos();
	   }
	   
	   public List<Compania> choices2Renovacion(){
	   	return companiaRepository.listarActivos();
	   }	    
	   
	   public List<DetalleTipoPago> choices7Renovacion(			
				final String polizaNumero,
				final Persona polizaCliente,
				final Compania polizaCompania,
				final Date polizaFechaEmision,
				final Date polizaFechaVigencia,
				final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago,
				final DetalleTipoPago polizaPago,
				final double polizaImporteTotal,
				final float riesgoARTMonto) {
			return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	   }
	   
	   public Persona default1Renovacion() {
	   	return getPolizaCliente();
	   }

	   public Compania default2Renovacion(){
	   	return getPolizaCompania();
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
	   PersonaRepository personaRepository;

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
	   PolizaTRORepository riesgosTRORepository;
	   
	   //endregion

}
