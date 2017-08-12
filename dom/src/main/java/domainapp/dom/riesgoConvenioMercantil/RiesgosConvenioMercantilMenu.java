package domainapp.dom.riesgoConvenioMercantil;

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
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = RiesgosConvenioMercantil.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1.3"
)
public class RiesgosConvenioMercantilMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Convenio Mercantil")
	  @MemberOrder(sequence = "2")
	  public List<RiesgosConvenioMercantil> listar() {
			  List<RiesgosConvenioMercantil> listaPolizaRiesgosConvenioMercantil = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosConvenioMercantil.size(); i++) {
				  listaPolizaRiesgosConvenioMercantil.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosConvenioMercantil;
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

	    public static class CreateDomainEvent extends ActionDomainEvent<RiesgosConvenioMercantilMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Convenio Mercantil ")
	    @MemberOrder(sequence = "1")
	    public RiesgosConvenioMercantil crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Clientes polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Companias polizaCompania,
/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPagos polizaPago,
/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
	    			@ParameterLayout(named="Monto") final float riesgoConvenioMercantilMonto)
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
	        		riesgoConvenioMercantilMonto);
	    }


	    @javax.inject.Inject
	    RiesgosConvenioMercantilRepository polizasRepository;
	    @javax.inject.Inject
	    ClientesRepository clientesRepository;
	    @Inject
	    DetalleTipoPagosRepository detalleTipoPagosRepository;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TiposDeCoberturasRepository tiposDeCoberturasRepository;
}
