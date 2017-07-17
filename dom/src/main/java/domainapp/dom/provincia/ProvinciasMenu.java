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
        named = "Lugares",
        menuOrder = "6.1"
)
public class ProvinciasMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Provincias> listar() {
	        return provinciasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Provincias> listarActivos() {
	        return provinciasRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<Provincias> listarInactivos() {
	        return provinciasRepository.listarInactivos();
	    }


	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "5")
	    public List<Provincias> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String provinciaNombre){
	        return provinciasRepository.buscarPorNombre(provinciaNombre);

	    }
	    
	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Provincias")
	    public void vehiculoTitulo(){}

	    public static class CreateDomainEvent extends ActionDomainEvent<ProvinciasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public Provincias crear(
	            @ParameterLayout(named="Nombre") final String provinciaNombre){
	        return provinciasRepository.crear(provinciaNombre);
	    }


	    @javax.inject.Inject
	    ProvinciasRepository provinciasRepository;

}
