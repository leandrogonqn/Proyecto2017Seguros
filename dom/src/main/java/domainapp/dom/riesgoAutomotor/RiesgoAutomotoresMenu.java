package domainapp.dom.riesgoAutomotor;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagosRepository;
import domainapp.dom.pagoEfectivo.PagosEfectivoMenu;
import domainapp.dom.poliza.Estado;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;
import domainapp.dom.vehiculo.Vehiculos;
import domainapp.dom.vehiculo.VehiculosRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = RiesgoAutomotores.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "4"
)
public class RiesgoAutomotoresMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<RiesgoAutomotores> listar() {
	        return polizasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<RiesgoAutomotores> buscarpolizaNumero(
	            @ParameterLayout(named="Numero")
	            final String polizaNumero){
	        return polizasRepository.buscarPolizaNumero(polizaNumero);
	    }
	    
	    public List<Clientes> choices1Crear(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    public List<Vehiculos> choices2Crear(){
	    	return vehiculosRepository.listarActivos();
	    }
	    
	    public List<Companias> choices3Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<TiposDeCoberturas> choices4Crear(){
	    	return tiposDeCoberturasRepository.listarActivos();
	    }
	    
	    public List<DetalleTipoPagos> choices9Crear(){
	    	return detalleTipoPagosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<RiesgoAutomotoresMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1")
	    public RiesgoAutomotores crear(
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
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Estado") final Estado polizaEstado)
	    {
	        return polizasRepository.crear(
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
	        		polizaImporteTotal, 
	        		polizaEstado);
	    }


	    @javax.inject.Inject
	    RiesgoAutomotoresRepository polizasRepository;
	    @javax.inject.Inject
	    ClientesRepository clientesRepository;
	    @Inject
	    VehiculosRepository vehiculosRepository;
	    @Inject
	    DetalleTipoPagosRepository detalleTipoPagosRepository;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TiposDeCoberturasRepository tiposDeCoberturasRepository;
}
