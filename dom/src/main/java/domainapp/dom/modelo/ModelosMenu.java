package domainapp.dom.modelo;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
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
        named = "Vehiculos",
        menuOrder = "3.3"
)
public class ModelosMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todos los Modelos")
	    @MemberOrder(sequence = "2")
	    public List<Modelos> listar() {
	        return modelosRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Modelos")
	    @MemberOrder(sequence = "5")
	    public List<Modelos> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String modeloNombre
	    ) {
	        return modelosRepository.buscarPorNombre(modeloNombre);
	    }
	    
	    public List<TipoVehiculo> choices1Crear(){
	    	return tipoVehiculoRepository.listarActivos();
	    }
	    
	     public List<Marcas> choices2Crear(){
	    	return  marcaRepository.listarActivos();
	    }

	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Modelo")
	    public Modelos crear(
	            @ParameterLayout(named="Nombre") final String modeloNombre,
	            @ParameterLayout(named="Tipo Vehiculo")final TipoVehiculo modeloTipoVehiculo,
	            @ParameterLayout(named="Marca") final Marcas modeloMarca){
	        return modelosRepository.crear(modeloNombre, modeloTipoVehiculo, modeloMarca);
	    }


	    @javax.inject.Inject
	    ModelosRepository modelosRepository;
	    

	    @javax.inject.Inject
	    MarcasRepository marcaRepository;
	    
	    @javax.inject.Inject
	    TipoVehiculoRepository tipoVehiculoRepository;

	    
	    @javax.inject.Inject
	    TipoVehiculoRepository vehiculoRepository;
}
