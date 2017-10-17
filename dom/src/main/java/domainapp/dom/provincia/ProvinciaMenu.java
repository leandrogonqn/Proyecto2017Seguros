package domainapp.dom.provincia;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
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
        repositoryFor = Provincia.class
)
@DomainServiceLayout(
        named = "Clientes",
        menuOrder = "10"
)
public class ProvinciaMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todas las Provincias")
	    @MemberOrder(sequence = "2")
	    public List<Provincia> listar() {
	        return provinciasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Provincia Por Nombre")
	    @MemberOrder(sequence = "5")
	    public List<Provincia> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String provinciaNombre){
	        return provinciasRepository.buscarPorNombre(provinciaNombre);

	    }
	    
	    public static class CreateDomainEvent extends ActionDomainEvent<ProvinciaMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Provincia")
	    @MemberOrder(sequence = "1.2")
	    public Provincia crear(
	            @ParameterLayout(named="Nombre") final String provinciaNombre){
	        return provinciasRepository.crear(provinciaNombre);
	    }


	    @javax.inject.Inject
	    ProvinciaRepository provinciasRepository;

}
