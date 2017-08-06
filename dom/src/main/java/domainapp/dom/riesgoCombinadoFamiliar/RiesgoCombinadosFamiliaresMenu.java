package domainapp.dom.riesgoCombinadoFamiliar;

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
import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.ocupacion.OcupacionesRepository;
import domainapp.dom.tipoTitular.TipoTitulares;
import domainapp.dom.tipoTitular.TipoTitularesRepository;
import domainapp.dom.tipoVivienda.TiposVivienda;
import domainapp.dom.tipoVivienda.TiposViviendaRepository;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = RiesgoCombinadosFamiliares.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1.3"
)
public class RiesgoCombinadosFamiliaresMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Combinados Familiares")
	  @MemberOrder(sequence = "2")
	  public List<RiesgoCombinadosFamiliares> listar() {
			  List<RiesgoCombinadosFamiliares> listaPolizaRiesgoCombinadosFamiliares = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoCombinadosFamiliares.size(); i++) {
				  listaPolizaRiesgoCombinadosFamiliares.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoCombinadosFamiliares;
	    }
	  
	    public List<Clientes> choices1Crear(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    public List<Ocupaciones> choices4Crear(){
	    	return ocupacionesRepository.listarActivos();
	    }
	    
	    public List<Companias> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<TipoTitulares> choices6Crear(){
	    	return tipoTitularesRepository.listarActivos();
	    }
	    
	    public List<TiposVivienda> choices5Crear(){
	    	return tiposViviendaRepository.listarActivos();
	    }
	    
	    public List<DetalleTipoPagos> choices11Crear(){
	    	return detalleTipoPagosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<RiesgoCombinadosFamiliaresMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Combinado Familiar")
	    @MemberOrder(sequence = "1")
	    public RiesgoCombinadosFamiliares crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Clientes polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Companias polizaCompania,
/*3*/	    		@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
/*4*/	            @ParameterLayout(named="Ocupación") final Ocupaciones riesgoCombinadosFamiliaresOcupacion,
/*5*/	            @ParameterLayout(named="Tipo de Vivienda") final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
/*6*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,
/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
/*10*/				@ParameterLayout(named="Fecha Vencimiento Pago") final Date polizaFechaVencimientoPago,
/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
/*12*/				@ParameterLayout(named="Alerta Vencimiento Pago") final boolean polizaAlertaVencimientoPago,
/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal)
	    {
	        return polizasRepository.crear(
	        		polizaNumero,
	        		polizaCliente,
	        		polizaCompania,
	        		riesgoCombinadosFamiliaresDomicilio,
	        		riesgoCombinadosFamiliaresOcupacion,
	        		riesgoCombinadosFamiliaresTipoVivienda,
	        		riesgoCombinadosFamiliaresTipoTitular,
	        		polizaFechaEmision,
	        		polizaFechaVigencia, 
	        		polizaFechaVencimiento,
	        		polizaFechaVencimientoPago, 
	        		polizaPago,
	        		polizaAlertaVencimientoPago,
	        		polizaImporteTotal);
	    }


	    @javax.inject.Inject
	    RiesgoCombinadosFamiliaresRepository polizasRepository;
	    @javax.inject.Inject
	    ClientesRepository clientesRepository;
	    @Inject
	    TiposViviendaRepository tiposViviendaRepository;
	    
	    @Inject
	    TipoTitularesRepository tipoTitularesRepository;

	    @Inject
	    OcupacionesRepository ocupacionesRepository;
	    
	    @Inject
	    DetalleTipoPagosRepository detalleTipoPagosRepository;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TiposDeCoberturasRepository tiposDeCoberturasRepository;

}