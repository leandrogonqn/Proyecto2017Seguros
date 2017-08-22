package domainapp.dom.riesgoAutomotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
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
			Companias polizaCompania,
			List<Vehiculos> riesgoAutomotorListaVehiculos,
			TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			DetalleTipoPagos polizaPago,
			double polizaImporteTotal) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoAutomotorListaVehiculos(riesgoAutomotorListaVehiculos);
		setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public RiesgoAutomotores(
			String polizaNumero, 
			Clientes polizaCliente,
			Companias polizaCompania,
			List<Vehiculos> riesgoAutomotorListaVehiculos,
			TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			DetalleTipoPagos polizaPago,
			double polizaImporteTotal,
			Polizas riesgoAutomotores) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoAutomotorListaVehiculos(riesgoAutomotorListaVehiculos);
		setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		riesgoAutomotores.setPolizaRenovacion(this);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
    public String iconName(){
    	String a;
    	if (this.getRiesgoAutomotorListaVehiculos().size()==1)
    		a="individual";
    	else
    		a="flotas";
    	return a;
    }
	
	//Vehiculo
	@Join
    @Property(
            editing = Editing.ENABLED
    )
	@CollectionLayout(named="Lista de vehiculos")
	private List<Vehiculos> riesgoAutomotorListaVehiculos; 
	
	public List<Vehiculos> getRiesgoAutomotorListaVehiculos() {
		return riesgoAutomotorListaVehiculos;
	}

	public void setRiesgoAutomotorListaVehiculos(List<Vehiculos> riesgoAutomotorListaVehiculos) {
		this.riesgoAutomotorListaVehiculos = riesgoAutomotorListaVehiculos;
	}	
	
	//Vehiculo
	@PropertyLayout(named="Vehiculo")
	@NotPersistent
	private Vehiculos riesgoAutomotorVehiculo; 
	
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
            @ParameterLayout(named="Compañia") final Companias polizaCompania,
            @ParameterLayout(named="Tipo de Cobertura") final TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
    		@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
			@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
    	List<Vehiculos> riesgoAutomotorListaVehiculos = new ArrayList<>();
    	riesgoAutomotorListaVehiculos = this.getRiesgoAutomotorListaVehiculos();
        return riesgoAutomotoresRepository.renovacion(
        		polizaNumero, 
        		polizaCliente, 
        		polizaCompania,
        		riesgoAutomotorListaVehiculos,
        		riesgoAutomotorTiposDeCoberturas,
        		polizaFechaEmision, 
        		polizaFechaVigencia, 
        		polizaFechaVencimiento, 
        		polizaPago, 
        		polizaImporteTotal, this);
	}
	
    public List<Clientes> choices1Renovacion(){
    	return clientesRepository.listarActivos();
    }
    
    public List<Companias> choices2Renovacion(){
    	return companiaRepository.listarActivos();
    }	    
    
    public List<TiposDeCoberturas> choices3Renovacion(){
    	return tiposDeCoberturasRepository.listarActivos();
    }
    
    public List<DetalleTipoPagos> choices7Renovacion(){
    	return detalleTipoPagosRepository.listarActivos();
    }
    
    public Clientes default1Renovacion() {
    	return getPolizaCliente();
    }
    
    public Companias default2Renovacion(){
    	return getPolizaCompania();
    }
    
    public TiposDeCoberturas default3Renovacion(){
    	return getRiesgoAutomotorTipoDeCobertura();
    }
    
    public DetalleTipoPagos default7Renovacion(){
    	return getPolizaPago();
    }
    
    public RiesgoAutomotores agregarVehiculo(
    		@ParameterLayout(named="Vehiculo") final Vehiculos riesgoAutomorVehiculo) {
    	if (this.getRiesgoAutomotorListaVehiculos().contains(riesgoAutomorVehiculo)){
    		messageService.informUser(String.format("El vehiculo ya está agregado en la lista", null));
		} 
    	else {
			this.getRiesgoAutomotorListaVehiculos().add(riesgoAutomorVehiculo);
			this.setRiesgoAutomotorListaVehiculos(this.getRiesgoAutomotorListaVehiculos());
		}
    	return this;
	}
    
	public List<Vehiculos> choices0AgregarVehiculo() {
		return vehiculosRepository.listarActivos();
	}
    
    public RiesgoAutomotores modificarCoche(
    		@ParameterLayout(named = "Vehiculo a añadir") Vehiculos riesgoAutomotorVehiculoNuevo,
    		@ParameterLayout(named = "Vehiculo a quitar") Vehiculos riesgoAutomotorVehiculoViejo) {
		Iterator<Vehiculos> it = getRiesgoAutomotorListaVehiculos().iterator();
		if (riesgoAutomotorVehiculoNuevo.equals(riesgoAutomotorVehiculoViejo)) {
			messageService.warnUser("ERROR: el vehiculo a agregar y el vehiculo a quitar son el mismo");
		} else {
			if (this.getRiesgoAutomotorListaVehiculos().contains(riesgoAutomotorVehiculoNuevo)) {
				messageService.warnUser("El vehiculo que quiere agregar ya está agregado en la lista");
			} else {
				while (it.hasNext()) {
					Vehiculos lista = it.next();
					if (lista.equals(riesgoAutomotorVehiculoViejo)) {
						it.remove();
						this.getRiesgoAutomotorListaVehiculos().add(riesgoAutomotorVehiculoNuevo);
						this.setRiesgoAutomotorListaVehiculos(this.getRiesgoAutomotorListaVehiculos());
					}
				}
			}
		}
		return this;
	}
    
	public List<Vehiculos> choices0ModificarCoche(){
		return vehiculosRepository.listarActivos();
	}
    
	public List<Vehiculos> choices1ModificarCoche(){
		return getRiesgoAutomotorListaVehiculos();
	}
    
	public RiesgoAutomotores quitarCoche(@ParameterLayout(named = "Vehiculo") Vehiculos riesgoAutomotorVehiculo) {
		Iterator<Vehiculos> it = getRiesgoAutomotorListaVehiculos().iterator();
		while (it.hasNext()) {
			Vehiculos lista = it.next();
			if (lista.equals(riesgoAutomotorVehiculo))
				it.remove();
		}
		return this;
	}
	
	public List<Vehiculos> choices0QuitarCoche(){
		return getRiesgoAutomotorListaVehiculos();
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
