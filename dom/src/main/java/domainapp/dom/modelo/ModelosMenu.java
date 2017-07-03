package domainapp.dom.modelo;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

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
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.marca.Marcas;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Modelos.class
)
@DomainServiceLayout(
        named = "Modelos",
        menuOrder = "5"
)
public class ModelosMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Modelos> listar() {
	        return modelosRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Modelos> listarActivos() {
	        return modelosRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<Modelos> listarInactivos() {
	        return modelosRepository.listarInactivos();
	    }


	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "5")
	    public List<Modelos> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String nombre
	    ) {
	        return modelosRepository.buscarPorNombre(nombre);
	    }
	    

	    public static class CreateDomainEvent extends ActionDomainEvent<ModelosMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1")
	    public Modelos crear(
	            @ParameterLayout(named="Nombre") final String nombre,
	            @ParameterLayout(named="Marca") final Marcas marca){
	        return modelosRepository.crear(nombre, marca);
	    }


	    @javax.inject.Inject
	    ModelosRepository modelosRepository;

}
