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
import domainapp.dom.localidad.Localidades;
import domainapp.dom.localidad.LocalidadesRepository;
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
	    
	    public List<Localidades> choices4Crear(){
		   	return localidadesRepository.listarActivos();
		   }	    
	    
	    public List<Ocupaciones> choices5Crear(){
	    	return ocupacionesRepository.listarActivos();
	    }
	    
	    public List<Companias> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<TipoTitulares> choices7Crear(){
	    	return tipoTitularesRepository.listarActivos();
	    }
	    
	    public List<TiposVivienda> choices6Crear(){
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
/*4*/					@ParameterLayout(named="Localidad") final Localidades riesgoCombinadoFamiliarLocalidad,
/*5*/	            @ParameterLayout(named="Ocupación") final Ocupaciones riesgoCombinadosFamiliaresOcupacion,
/*6*/	            @ParameterLayout(named="Tipo de Vivienda") final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
/*7*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,
/*8*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*9*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*10*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
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
	    @Inject
	    LocalidadesRepository localidadesRepository;

}