package domainapp.dom.marca;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Marca.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "3.1"
)
public class MarcaMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todas las Marcas")
	    @MemberOrder(sequence = "2")
	    public List<Marca> listar() {
	        return marcasRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Marca")
	    @MemberOrder(sequence = "5")
	    public List<Marca> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String marcaNombre){
	        return marcasRepository.buscarPorNombre(marcaNombre);

	    }
	    
	    public static class CreateDomainEvent extends ActionDomainEvent<MarcaMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Marca")
	    @MemberOrder(sequence = "1.2")
	    public Marca crear(
	            @ParameterLayout(named="Nombre") final String marcaNombre){
	        return marcasRepository.crear(marcaNombre);
	    }


	    @javax.inject.Inject
	    MarcaRepository marcasRepository;

}
