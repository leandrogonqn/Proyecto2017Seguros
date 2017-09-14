package domainapp.dom.polizaCombinadoFamiliar;

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
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.ocupacion.Ocupacion;
import domainapp.dom.ocupacion.OcupacionRepository;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoTitular.TipoTitularRepository;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tipoVivienda.TipoViviendaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaCombinadoFamiliar.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1.3"
)
public class PolizaCombinadoFamiliarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Combinados Familiares")
	  @MemberOrder(sequence = "2")
	  public List<PolizaCombinadoFamiliar> listar() {
			  List<PolizaCombinadoFamiliar> listaPolizaRiesgoCombinadosFamiliares = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoCombinadosFamiliares.size(); i++) {
				  listaPolizaRiesgoCombinadosFamiliares.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoCombinadosFamiliares;
	    }
	  
	    public List<Cliente> choices1Crear(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    public List<Localidad> choices4Crear(){
		   	return localidadesRepository.listarActivos();
		   }	    
	    
	    public List<Ocupacion> choices5Crear(){
	    	return ocupacionesRepository.listarActivos();
	    }
	    
	    public List<Compania> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<TipoTitular> choices7Crear(){
	    	return tipoTitularesRepository.listarActivos();
	    }
	    
	    public List<TipoVivienda> choices6Crear(){
	    	return tiposViviendaRepository.listarActivos();
	    }
	    
	    public List<DetalleTipoPago> choices11Crear(){
	    	return detalleTipoPagosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<PolizaCombinadoFamiliarMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Combinado Familiar")
	    @MemberOrder(sequence = "1")
	    public PolizaCombinadoFamiliar crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Cliente polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
/*3*/	    		@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
/*4*/					@ParameterLayout(named="Localidad") final Localidad riesgoCombinadoFamiliarLocalidad,
/*5*/	            @ParameterLayout(named="Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion,
/*6*/	            @ParameterLayout(named="Tipo de Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
/*7*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
/*8*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*9*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*10*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago,
/*12*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal)
	    {
	        return polizasRepository.crear(
	        		polizaNumero,
	        		polizaCliente,
	        		polizaCompania,
	        		riesgoCombinadosFamiliaresDomicilio,
	        		riesgoCombinadoFamiliarLocalidad,
	        		riesgoCombinadosFamiliaresOcupacion,
	        		riesgoCombinadosFamiliaresTipoVivienda,
	        		riesgoCombinadosFamiliaresTipoTitular,
	        		polizaFechaEmision,
	        		polizaFechaVigencia, 
	        		polizaFechaVencimiento,
	        		polizaPago,
	        		polizaImporteTotal);
	    }


	    @javax.inject.Inject
	    PolizaCombinadoFamiliarRepository polizasRepository;
	    @javax.inject.Inject
	    ClienteRepository clientesRepository;
	    @Inject
	    TipoViviendaRepository tiposViviendaRepository;
	    
	    @Inject
	    TipoTitularRepository tipoTitularesRepository;

	    @Inject
	    OcupacionRepository ocupacionesRepository;
	    
	    @Inject
	    DetalleTipoPagoRepository detalleTipoPagosRepository;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TipoDeCoberturaRepository tiposDeCoberturasRepository;
	    @Inject
	    LocalidadRepository localidadesRepository;

}