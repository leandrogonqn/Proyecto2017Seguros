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
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoVehiculo.TipoVehiculo;
import domainapp.dom.tipoVehiculo.TipoVehiculoRepository;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;
import domainapp.dom.marca.Marcas;
import domainapp.dom.marca.MarcasRepository;


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
	    @MemberOrder(sequence = "1")
	    public Modelos crear(
	            @ParameterLayout(named="Nombre") final String nombre,
	            @ParameterLayout(named="Tipo Vehiculo")final TipoVehiculo tipoVehiculo,
	            @ParameterLayout(named="Marca") final Marcas marcas){
	        return modelosRepository.crear(nombre, tipoVehiculo, marcas);
	    }


	    @javax.inject.Inject
	    ModelosRepository modelosRepository;
	    

	    @javax.inject.Inject
	    MarcasRepository marcaRepository;

	    
	    @javax.inject.Inject
	    TipoVehiculoRepository vehiculoRepository;
}
