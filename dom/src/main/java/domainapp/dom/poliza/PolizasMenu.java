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
import domainapp.dom.estado.Estado;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Polizas.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1"
)
public class PolizasMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todas las polizas")
	    @MemberOrder(sequence = "2")
	    public List<Polizas> listar() {
		  List<Polizas> listaPolizas = polizasRepository.listar();
		  for(int i=0; i< listaPolizas.size(); i++) {
	            listaPolizas.get(i).actualizarPoliza();
	        }
	      return listaPolizas;
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar poliza por Numero")
	    @MemberOrder(sequence = "1.1")
	    public List<Polizas> buscarpolizaNumero(
	            @ParameterLayout(named="Numero")
	            final String polizaNumero){
	        return polizasRepository.buscarpolizaNumero(polizaNumero);
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar poliza por Clientes")
	    @MemberOrder(sequence = "1.2")
	    public List<Polizas> buscarPorCliente(
	    		@ParameterLayout(named="Cliente") final Clientes polizaCliente){
	    	return polizasRepository.buscarPorCliente(polizaCliente);
	    }
	    
	    public List<Clientes> choices0BuscarPorCliente(){
	    	return clientesRepository.listarActivos();
	    }
	    
	    @MemberOrder(sequence = "3")	    
	    @ActionLayout(named="Listar por Estados")
	    public List<Polizas> listarPorEstado(final Estado estado){
			return polizasRepository.listarPorEstado(estado);
		}
	    
	    @javax.inject.Inject
	    PolizasRepository polizasRepository;
	    @javax.inject.Inject
	    ClientesRepository clientesRepository;
}
