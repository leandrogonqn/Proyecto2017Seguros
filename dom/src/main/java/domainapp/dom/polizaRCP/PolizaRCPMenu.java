package domainapp.dom.polizaRCP;

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
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaRCP.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1.3"
)
public class PolizaRCPMenu {
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas RCP")
	  @MemberOrder(sequence = "2")
	  public List<PolizaRCP> listar() {
			  List<PolizaRCP> listaPolizaRiesgosRCP = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosRCP.size(); i++) {
				  listaPolizaRiesgosRCP.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosRCP;
	    }
	  
	    public List<Cliente> choices1Crear(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    public List<Compania> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<DetalleTipoPago> choices6Crear(){
	    	return detalleTipoPagosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<PolizaRCPMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza RCP")
	    @MemberOrder(sequence = "1")
	    public PolizaRCP crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Cliente polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
/*11*/				@ParameterLayout(named="Pago") final DetalleTipoPago polizaPago,
/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
	    			@ParameterLayout(named="Monto") final float riesgoRCPMonto)
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
	        		riesgoRCPMonto);
	    }


	    @javax.inject.Inject
	    PolizaRCPRepository polizasRepository;
	    @javax.inject.Inject
	    ClienteRepository clientesRepository;
	    @Inject
	    DetalleTipoPagoRepository detalleTipoPagosRepository;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TipoDeCoberturaRepository tiposDeCoberturasRepository;
}
