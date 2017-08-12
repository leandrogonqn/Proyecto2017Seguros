package domainapp.dom.riesgoIntegralComercio;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.InvokeOn;
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
import domainapp.dom.estado.Estado;
import domainapp.dom.pagoEfectivo.PagosEfectivoMenu;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;
import domainapp.dom.vehiculo.Vehiculos;
import domainapp.dom.vehiculo.VehiculosRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = RiesgoIntegralComercio.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "4"
)
public class RiesgoIntegralComercioMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Poliza Riesgo Integral Comercio")
	  @MemberOrder(sequence = "2")
	  public List<RiesgoIntegralComercio> listar() {
			  List<RiesgoIntegralComercio> listaPolizasRiesgoIntegralComercio = polizasRepository.listar();
			  for(int i=0; i< listaPolizasRiesgoIntegralComercio.size(); i++) {
				  listaPolizasRiesgoIntegralComercio.get(i).actualizarPoliza();
		        }
		      return listaPolizasRiesgoIntegralComercio;
	    }
	  
	    public List<Clientes> choices1Crear(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    public List<Companias> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<DetalleTipoPagos> choices6Crear(){
	    	return detalleTipoPagosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<RiesgoIntegralComercioMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Riesgo Integral Comercio")
	    @MemberOrder(sequence = "1")
	    public RiesgoIntegralComercio crear(
	            @ParameterLayout(named="Número") final String polizaNumero,
	            @ParameterLayout(named="Cliente") final Clientes polizaCliente,
	            @ParameterLayout(named="Compañia") final Companias polizaCompania,
	    		@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
	    		@ParameterLayout(named="Incendio Edificio") final float riesgoIntegralComercioIncendioEdificio,
	    		@ParameterLayout(named="Incendio Contenido") final float riesgoIntegralComercioIncendioContenido,
	    		@ParameterLayout(named="Robo") final float riesgoIntegralComercioRobo,
	    		@ParameterLayout(named="Cristales") final float riesgoIntegralComercioCristales,
	    		@ParameterLayout(named="RC") final float riesgoIntegralComercioRc,
	    		@ParameterLayout(named="RCL") final float riesgoIntegralComercioRcl,
	    		@ParameterLayout(named="Daño por Agua") final float riesgoIntegralComercioDanioPorAgua,
	    		@ParameterLayout(named = "RCC") float riesgoIntegralComercioRCC,
	    		@ParameterLayout(named="Otros Nombre") final String riesgoIntegralComercioOtrosNombre,
	    		@ParameterLayout(named="Otros Monto") final float riesgoIntegralComercioOtrosMonto)
	    {
	        return polizasRepository.crear(
	        		polizaNumero, 
	        		polizaCliente, 
	        		polizaCompania,
	        		polizaFechaEmision, 
	        		polizaFechaVigencia, 
	        		polizaFechaVencimiento, 
	        		polizaPago, 
	        		polizaImporteTotal,
	        		riesgoIntegralComercioRobo,
	        		riesgoIntegralComercioCristales,
	        		riesgoIntegralComercioIncendioEdificio,
	        		riesgoIntegralComercioIncendioContenido,
	        		riesgoIntegralComercioRc,
	        		riesgoIntegralComercioRcl,
	        		riesgoIntegralComercioDanioPorAgua,
	        		riesgoIntegralComercioRCC,
	        		riesgoIntegralComercioOtrosNombre,
	        		riesgoIntegralComercioOtrosMonto);
	    }


	    @javax.inject.Inject
	    RiesgoIntegralComercioRepository polizasRepository;
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
