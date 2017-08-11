package domainapp.dom.riesgoAutomotor;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

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
@Discriminator(value="RiesgoAutomotores")
public class RiesgoAutomotores extends Polizas implements Comparable<RiesgoAutomotores> {
	
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name","Poliza Automotor N°: " + getPolizaNumero());
    }
    //endregion

	// Constructor
	public RiesgoAutomotores(
			String polizaNumero, 
			Clientes polizaCliente,
			Vehiculos riesgoAutomotorVehiculo,
			Companias polizaCompania,
			TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, 
			DetalleTipoPagos polizaPago,
			boolean polizaAlertaVencimientoPago,
			double polizaImporteTotal) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setRiesgoAutomotorVehiculo(riesgoAutomotorVehiculo);
		setPolizasCompania(polizaCompania);
		setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
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
	
	public RiesgoAutomotores(
			String polizaNumero, 
			Clientes polizaCliente,
			Vehiculos riesgoAutomotorVehiculo,
			Companias polizaCompania,
			TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, 
			DetalleTipoPagos polizaPago,
			boolean polizaAlertaVencimientoPago,
			double polizaImporteTotal,
			Polizas riesgoAutomotores) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setRiesgoAutomotorVehiculo(riesgoAutomotorVehiculo);
		setPolizasCompania(polizaCompania);
		setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		setPolizaPago(polizaPago);
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		setPolizaImporteTotal(polizaImporteTotal);
		riesgoAutomotores.setPolizaRenovacion(this);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	//Vehiculo
	@Column(name="vehiculoId")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Vehiculo")
	private Vehiculos riesgoAutomotorVehiculo; 
	
	public Vehiculos getRiesgoAutomotorVehiculo() {
		return riesgoAutomotorVehiculo;
	}

	public void setRiesgoAutomotorVehiculo(Vehiculos riesgoAutomotorVehiculo) {
		this.riesgoAutomotorVehiculo = riesgoAutomotorVehiculo;
	}	
	
	//Tipo Cobertura
	@Column(name="tipoDeCoberturaId")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Tipo de Cobertura")
	private TiposDeCoberturas riesgoAutomotorTipoDeCobertura; 
	
	public TiposDeCoberturas getRiesgoAutomotorTipoDeCobertura() {
		return riesgoAutomotorTipoDeCobertura;
	}

	public void setRiesgoAutomotorTipoDeCobertura(TiposDeCoberturas riesgoAutomotorTipoDeCobertura) {
		this.riesgoAutomotorTipoDeCobertura = riesgoAutomotorTipoDeCobertura;
	}	
	    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<RiesgoAutomotores> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    
    //Actualizar PolizaNumero
	public RiesgoAutomotores actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
    
	//Actualizar Poliza Cliente
    public RiesgoAutomotores actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Clientes polizaCliente) {
        setPolizasCliente(polizaCliente);
        return this;
    }
    
    public List<Clientes> choices0ActualizarPolizaCliente(){
    	return clientesRepository.listarActivos();
    }
      
    public Clientes default0ActualizarPolizaCliente() {
    	return getPolizaCliente();
    }
    
    //Actualizar RiesgoAutomotorVehiculo
    public RiesgoAutomotores actualizarRiesgoAutomotorVehiculo(@ParameterLayout(named="Vehiculo") final Vehiculos riesgoAutomotorVehiculo) {
        setRiesgoAutomotorVehiculo(riesgoAutomotorVehiculo);
        return this;
    }
    
    public List<Vehiculos> choices0ActualizarRiesgoAutomotorVehiculo(){
    	return vehiculosRepository.listarActivos();
    }
      
    public Vehiculos default0ActualizarRiesgoAutomotorVehiculo() {
    	return getRiesgoAutomotorVehiculo();
    }
    
    //Actualizar polizaCompania
    public RiesgoAutomotores actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Companias polizaCompania) {
        actualizarPolizaCompania(polizaCompania);
        return this;
    }
    
    public List<Companias> choices0ActualizarPolizaCompania(){
    	return companiaRepository.listarActivos();
    }
      
    public Companias default0ActualizarPolizaCompania() {
    	return getPolizaCompania();
    }    
    
    //Actualizar riesgoAutomotorTiposDeCoberturas
    public RiesgoAutomotores actualizarRiesgoAutomotorTiposDeCoberturas(@ParameterLayout(named="Tipos De Coberturas") final TiposDeCoberturas riesgoAutomotorTiposDeCoberturas) {
    	setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
        return this;
    }
    
    public List<TiposDeCoberturas> choices0ActualizarRiesgoAutomotorTiposDeCoberturas(){
    	return tiposDeCoberturasRepository.listarActivos();
    }
      
    public TiposDeCoberturas default0ActualizarRiesgoAutomotorTiposDeCoberturas() {
    	return getRiesgoAutomotorTipoDeCobertura();
    }
    
    //Actualizar polizaFechaEmision
	public RiesgoAutomotores actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
    //Actualizar polizaFechaVigencia
	public RiesgoAutomotores actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
    //polizaFechaVencimiento
	public RiesgoAutomotores actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	    
    //polizaFechaVencimientoPago
	public RiesgoAutomotores actualizarPolizaFechaVencimientoPago(@ParameterLayout(named="Fecha Vencimiento de Pago") final Date polizaFechaVencimientoPago){
		setPolizaFechaVencimientoPago(polizaFechaVencimientoPago);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimientoPago(){
		return getPolizaFechaVencimientoPago();
	}    
	
    //polizaPago
    public RiesgoAutomotores actualizarPolizaPago(@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago) {
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
	public RiesgoAutomotores actualizarPolizaAlertaVencimientoPago(@ParameterLayout(named="Alerta Vencimiento de Pago") final boolean polizaAlertaVencimientoPago){
		setPolizaAlertaVencimientoPago(polizaAlertaVencimientoPago);
		return this;
	}

	public boolean default0ActualizarPolizaAlertaVencimientoPago(){
		return getPolizaAlertaVencimientoPago();
	}    
    
    //polizaFechaBaja
	public RiesgoAutomotores actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
    
    //polizaMotivoBaja
	public RiesgoAutomotores actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
    
    //polizaImporteTotal
	public RiesgoAutomotores actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
    //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
    public RiesgoAutomotores actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Polizas polizaRenovacion) {
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
    
    public RiesgoAutomotores borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
    	return this;
    }
    
    //endregion

    //acciones

	@Action(
			invokeOn=InvokeOn.OBJECT_ONLY
			)
	@ActionLayout(named="Emitir Renovacion")
	public RiesgoAutomotores renovacion(
			@ParameterLayout(named="Número") final String polizaNumero,
            @ParameterLayout(named="Cliente") final Clientes polizaCliente,
            @ParameterLayout(named="Vehiculo") final Vehiculos riesgoAutomotorVehiculo,
            @ParameterLayout(named="Compañia") final Companias polizaCompania,
            @ParameterLayout(named="Tipo de Cobertura") final TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
    		@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named="Fecha Vencimiento Pago") final Date polizaFechaVencimientoPago,
			@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
			@ParameterLayout(named="Alerta Vencimiento Pago") final boolean polizaAlertaVencimientoPago,
			@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
        return riesgoAutomotoresRepository.renovacion(
        		polizaNumero, 
        		polizaCliente, 
        		riesgoAutomotorVehiculo, 
        		polizaCompania,
        		riesgoAutomotorTiposDeCoberturas,
        		polizaFechaEmision, 
        		polizaFechaVigencia, 
        		polizaFechaVencimiento, 
        		polizaFechaVencimientoPago, 
        		polizaPago, 
        		polizaAlertaVencimientoPago, 
        		polizaImporteTotal, this);
	}
	
    public List<Clientes> choices1Renovacion(){
    	return clientesRepository.listarActivos();
    }
    
    public List<Vehiculos> choices2Renovacion(){
    	return vehiculosRepository.listarActivos();
    }
    
    public List<Companias> choices3Renovacion(){
    	return companiaRepository.listarActivos();
    }	    
    
    public List<TiposDeCoberturas> choices4Renovacion(){
    	return tiposDeCoberturasRepository.listarActivos();
    }
    
    public List<DetalleTipoPagos> choices9Renovacion(){
    	return detalleTipoPagosRepository.listarActivos();
    }
    
    public Clientes default1Renovacion() {
    	return getPolizaCliente();
    }
    
    public Vehiculos default2Renovacion(){
    	return getRiesgoAutomotorVehiculo();
    }
    
    public Companias default3Renovacion(){
    	return getPolizaCompania();
    }
    
    public TiposDeCoberturas default4Renovacion(){
    	return getRiesgoAutomotorTipoDeCobertura();
    }
    
    public DetalleTipoPagos default9Renovacion(){
    	return getPolizaPago();
    }
    
    public boolean default10Renovacion(){
    	return getPolizaAlertaVencimientoPago();
    }
    
    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "polizaNumero");
    }
    @Override
    public int compareTo(final RiesgoAutomotores other) {
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
    RiesgoAutomotoresRepository riesgoAutomotoresRepository;
    
    //endregion

}
