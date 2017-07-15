package domainapp.dom.poliza;

import java.util.Date;
import java.util.List;
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

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Polizas.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "4"
)
public class PolizasMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Polizas> listar() {
	        return polizasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Polizas> buscarpolizaNumero(
	            @ParameterLayout(named="Nombre")
	            final String polizaNumero){
	        return polizasRepository.buscarpolizaNumero(polizaNumero);

	    }
	    
	    public List<Clientes> choices1Crear(){
	    	
	    	return clientesRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<PolizasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1")
	    public Polizas crear(
	            @ParameterLayout(named="Número") final String polizaNumero,
	            @ParameterLayout(named="Cliente") final Clientes cliente,
	    		@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named="Fecha Vencimiento Pago") final Date polizaFechaVencimientoPago,
				@ParameterLayout(named="Precio Total") final double polizaPrecioTotal)
	    {
	        return polizasRepository.crear(polizaNumero,polizaFechaEmision,polizaFechaVigencia, polizaFechaVencimiento,
	    			polizaFechaVencimientoPago, polizaPrecioTotal,cliente);
	    }


	    @javax.inject.Inject
	    PolizasRepository polizasRepository;
	    @javax.inject.Inject
	    ClientesRepository clientesRepository;
}
