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

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Provincias.class
)
@DomainServiceLayout(
        named = "Clientes",
        menuOrder = "2"
)
public class ProvinciasMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todas las Provincias")
	    @MemberOrder(sequence = "2")
	    public List<Provincias> listar() {
	        return provinciasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Provincia Por Nombre")
	    @MemberOrder(sequence = "5")
	    public List<Provincias> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String provinciaNombre){
	        return provinciasRepository.buscarPorNombre(provinciaNombre);

	    }
	    
	    public static class CreateDomainEvent extends ActionDomainEvent<ProvinciasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Provincia")
	    @MemberOrder(sequence = "1.2")
	    public Provincias crear(
	            @ParameterLayout(named="Nombre") final String provinciaNombre){
	        return provinciasRepository.crear(provinciaNombre);
	    }


	    @javax.inject.Inject
	    ProvinciasRepository provinciasRepository;

}
