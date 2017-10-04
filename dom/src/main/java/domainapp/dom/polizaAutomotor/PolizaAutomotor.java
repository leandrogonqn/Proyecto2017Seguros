package domainapp.dom.polizaAutomotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.Queries;
import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
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
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;
import domainapp.dom.vehiculo.Vehiculo;
import domainapp.dom.vehiculo.VehiculoRepository;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Polizas"
)
@Queries({
    @javax.jdo.annotations.Query(
            name = "listarPorEstado", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.Polizas "
                    + "WHERE polizaEstado == :polizaEstado")
})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value="RiesgoAutomotores")
public class PolizaAutomotor extends Poliza implements Comparable<PolizaAutomotor> {
	
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name","Poliza Automotor N°: " + getPolizaNumero());
    }
    //endregion

	// Constructor
	public PolizaAutomotor(
			String polizaNumero, 
			Cliente polizaCliente,
			Compania polizaCompania,
			List<Vehiculo> riesgoAutomotorListaVehiculos,
			TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			TipoPago polizaTipoDePago,
			DetalleTipoPago polizaPago,
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
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaImporteTotal(polizaImporteTotal);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public PolizaAutomotor(
			String polizaNumero, 
			Cliente polizaCliente,
			Compania polizaCompania,
			List<Vehiculo> riesgoAutomotorListaVehiculos,
			TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			Date polizaFechaEmision, 
			Date polizaFechaVigencia, 
			Date polizaFechaVencimiento,
			TipoPago polizaTipoDePago,
			DetalleTipoPago polizaPago,
			double polizaImporteTotal,
			Poliza riesgoAutomotores) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoAutomotorListaVehiculos(riesgoAutomotorListaVehiculos);
		setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaTipoDePago(polizaTipoDePago);
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
	private List<Vehiculo> riesgoAutomotorListaVehiculos; 
	
	public List<Vehiculo> getRiesgoAutomotorListaVehiculos() {
		return riesgoAutomotorListaVehiculos;
	}

	public void setRiesgoAutomotorListaVehiculos(List<Vehiculo> riesgoAutomotorListaVehiculos) {
		this.riesgoAutomotorListaVehiculos = riesgoAutomotorListaVehiculos;
	}	
	
	//Tipo Cobertura
	@Column(name="tipoDeCoberturaId")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Tipo de Cobertura")
	private TipoDeCobertura riesgoAutomotorTipoDeCobertura; 
	
	public TipoDeCobertura getRiesgoAutomotorTipoDeCobertura() {
		return riesgoAutomotorTipoDeCobertura;
	}

	public void setRiesgoAutomotorTipoDeCobertura(TipoDeCobertura riesgoAutomotorTipoDeCobertura) {
		this.riesgoAutomotorTipoDeCobertura = riesgoAutomotorTipoDeCobertura;
	}	
	    
    //Actualizar PolizaNumero
	public PolizaAutomotor actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
    
	//Actualizar Poliza Cliente
    public PolizaAutomotor actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Cliente polizaCliente) {
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
    public PolizaAutomotor actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
        actualizarPolizaCompania(polizaCompania);
        return this;
    }
    
    public List<Compania> choices0ActualizarPolizaCompania(){
    	return companiaRepository.listarActivos();
    }
      
    public Compania default0ActualizarPolizaCompania() {
    	return getPolizaCompania();
    }    
    
    //Actualizar riesgoAutomotorTiposDeCoberturas
    public PolizaAutomotor actualizarRiesgoAutomotorTiposDeCoberturas(@ParameterLayout(named="Tipos De Coberturas") final TipoDeCobertura riesgoAutomotorTiposDeCoberturas) {
    	setRiesgoAutomotorTipoDeCobertura(riesgoAutomotorTiposDeCoberturas);
        return this;
    }
    
    public List<TipoDeCobertura> choices0ActualizarRiesgoAutomotorTiposDeCoberturas(){
    	return tiposDeCoberturasRepository.listarActivos();
    }
      
    public TipoDeCobertura default0ActualizarRiesgoAutomotorTiposDeCoberturas() {
    	return getRiesgoAutomotorTipoDeCobertura();
    }
    
    //Actualizar polizaFechaEmision
	public PolizaAutomotor actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
    //Actualizar polizaFechaVigencia
	public PolizaAutomotor actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
	public String validateActualizarPolizaFechaVigencia(final Date polizaFechaVigencia){

		for (Vehiculo vehiculo : this.getRiesgoAutomotorListaVehiculos()) {
			if (validarModificarFechaVigencia(vehiculo, polizaFechaVigencia, this) == false) {
				return "ERROR: vehiculo existente en otra poliza vigente";
			}
		}
		if (polizaFechaVigencia.after(this.getPolizaFechaVencimiento())){
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		return "";
	}
	
    //polizaFechaVencimiento
	public PolizaAutomotor actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	
	public String validateActualizarPolizaFechaVencimiento(final Date polizaFechaVencimiento){
		for (Vehiculo vehiculo : this.getRiesgoAutomotorListaVehiculos()) {
			if (validarModificarFechaVencimiento(vehiculo,polizaFechaVencimiento, this) == false) {
				return "ERROR: vehiculo existente en otra poliza vigente";
			}
		}
		if (this.getPolizaFechaVigencia().after(polizaFechaVencimiento)){
			return "La fecha de vencimiento es menor a la de vigencia";
		}
		return "";
	}
	    
    //polizaPago
    public PolizaAutomotor actualizarPolizaPago(
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
	public PolizaAutomotor actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
    
    //polizaMotivoBaja
	public PolizaAutomotor actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
    
    //polizaImporteTotal
	public PolizaAutomotor actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
    //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
    public PolizaAutomotor actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
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
    
    public PolizaAutomotor borrarPolizaRenovacion() {
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
	public PolizaAutomotor renovacion(
			@ParameterLayout(named="Número") final String polizaNumero,
            @ParameterLayout(named="Cliente") final Cliente polizaCliente,
            @ParameterLayout(named="Compañia") final Compania polizaCompania,
            @ParameterLayout(named="Tipo de Cobertura") final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
    		@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
    	List<Vehiculo> riesgoAutomotorListaVehiculos = new ArrayList<>();
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
        		polizaTipoDePago,
        		polizaPago, 
        		polizaImporteTotal, this);
	}
	
	public String validateRenovacion(
			final String polizaNumero,
			final Cliente polizaCliente,
			final Compania polizaCompania,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision,
			final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago,
			final double polizaImporteTotal){
		if (polizaFechaVigencia.after(polizaFechaVencimiento)){
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		for (Vehiculo vehiculo : this.getRiesgoAutomotorListaVehiculos()) {
			if (riesgoAutomotoresRepository.validar(vehiculo,polizaFechaVigencia) == false) {
				return "ERROR: vehiculo existente en otra poliza vigente";
			}
		}
		return "";
	}
	
    public List<Cliente> choices1Renovacion(){
    	return clientesRepository.listarActivos();
    }
    
    public List<Compania> choices2Renovacion(){
    	return companiaRepository.listarActivos();
    }	    
    
    public List<TipoDeCobertura> choices3Renovacion(){
    	return tiposDeCoberturasRepository.listarActivos();
    }
    
    public List<DetalleTipoPago> choices8Renovacion(			
			final String polizaNumero,
			final Cliente polizaCliente,
			final Compania polizaCompania,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision,
			final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago,
			final double polizaImporteTotal) {
 		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
    }
    
    
    public Cliente default1Renovacion() {
    	return getPolizaCliente();
    }
    
    public Compania default2Renovacion(){
    	return getPolizaCompania();
    }
    
    public TipoDeCobertura default3Renovacion(){
    	return getRiesgoAutomotorTipoDeCobertura();
    }
    
    public TipoPago default7Renovacion(){
	   	return getPolizaTipoDePago();
	   }
   
   public DetalleTipoPago default8Renovacion(){
   	return getPolizaPago();
   }
    
    public PolizaAutomotor agregarVehiculo(
			@ParameterLayout(named = "Vehiculo") final Vehiculo riesgoAutomorVehiculo) {
		boolean validador = riesgoAutomotoresRepository.validar(riesgoAutomorVehiculo, this.getPolizaFechaVigencia());
		if (validador) {
			if (this.getRiesgoAutomotorListaVehiculos().contains(riesgoAutomorVehiculo)) {
				messageService.warnUser("ERROR: El vehiculo ya está agregado en la lista");
			} else {
				this.getRiesgoAutomotorListaVehiculos().add(riesgoAutomorVehiculo);
				this.setRiesgoAutomotorListaVehiculos(this.getRiesgoAutomotorListaVehiculos());
			}
		} else {
			messageService.warnUser("ERROR: vehiculo existente en otra poliza vigente");
		}
    	return this;
	}
    
	public List<Vehiculo> choices0AgregarVehiculo() {
		return vehiculosRepository.listarActivos();
	}
    
    public PolizaAutomotor modificarCoche(
    		@ParameterLayout(named = "Vehiculo a añadir") Vehiculo riesgoAutomotorVehiculoNuevo,
    		@ParameterLayout(named = "Vehiculo a quitar") Vehiculo riesgoAutomotorVehiculoViejo) {
		Iterator<Vehiculo> it = getRiesgoAutomotorListaVehiculos().iterator();
		if (riesgoAutomotorVehiculoNuevo.equals(riesgoAutomotorVehiculoViejo)) {
			messageService.warnUser("ERROR: el vehiculo a agregar y el vehiculo a quitar son el mismo");
		} else {
			if (this.getRiesgoAutomotorListaVehiculos().contains(riesgoAutomotorVehiculoNuevo)) {
				messageService.warnUser("ERROR: El vehiculo que quiere agregar ya está agregado en la lista");
			} else {
				while (it.hasNext()) {
					Vehiculo lista = it.next();
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
    
	public List<Vehiculo> choices0ModificarCoche(){
		return vehiculosRepository.listarActivos();
	}
    
	public List<Vehiculo> choices1ModificarCoche(){
		return getRiesgoAutomotorListaVehiculos();
	}
    
	public PolizaAutomotor quitarCoche(@ParameterLayout(named = "Vehiculo") Vehiculo riesgoAutomotorVehiculo) {
		Iterator<Vehiculo> it = getRiesgoAutomotorListaVehiculos().iterator();
		while (it.hasNext()) {
			Vehiculo lista = it.next();
			if (lista.equals(riesgoAutomotorVehiculo))
				it.remove();
		}
		return this;
	}
	
	public List<Vehiculo> choices0QuitarCoche(){
		return getRiesgoAutomotorListaVehiculos();
	}
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	public boolean validarModificarFechaVigencia(Vehiculo vehiculo, Date fechaVigencia, PolizaAutomotor riesgoAutomotor) {

		boolean validador = true;

		List<PolizaAutomotor> listaPolizas = riesgoAutomotoresRepository.listarPorEstado(Estado.vigente);
		listaPolizas.addAll(riesgoAutomotoresRepository.listarPorEstado(Estado.previgente));

		for (PolizaAutomotor r : listaPolizas) {
			if (r != riesgoAutomotor) {
				for (Vehiculo v : r.getRiesgoAutomotorListaVehiculos()) {
					if (vehiculo.equals(v)) {
						if ((fechaVigencia.before(r.getPolizaFechaVencimiento()))&(this.getPolizaFechaVencimiento().after(r.getPolizaFechaVigencia()))) {
							validador = false;
						}
					}
				}
			}
		}

		return validador;
	}
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	public boolean validarModificarFechaVencimiento(Vehiculo vehiculo, Date fechaVencimiento, PolizaAutomotor riesgoAutomotor) {

		boolean validador = true;

		List<PolizaAutomotor> listaPolizas = riesgoAutomotoresRepository.listarPorEstado(Estado.vigente);
		listaPolizas.addAll(riesgoAutomotoresRepository.listarPorEstado(Estado.previgente));

		for (PolizaAutomotor r : listaPolizas) {
			if (r != riesgoAutomotor) {
				for (Vehiculo v : r.getRiesgoAutomotorListaVehiculos()) {
					if (vehiculo.equals(v)) {
						if ((fechaVencimiento.after(r.getPolizaFechaVigencia()))&(this.getPolizaFechaVigencia().before(r.getPolizaFechaVencimiento()))) {
							validador = false;
						}
					}
				}
			}
		}

		return validador;
	}
	
    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "polizaNumero");
    }
    @Override
    public int compareTo(final PolizaAutomotor other) {
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
    PolizaAutomotoresRepository riesgoAutomotoresRepository;
    
    @Inject
    DetalleTipoPagoMenu detalleTipoPagoMenu;
    
    //endregion

}
