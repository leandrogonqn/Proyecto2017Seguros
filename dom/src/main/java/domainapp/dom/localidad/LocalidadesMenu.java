package domainapp.dom.localidad;

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
import domainapp.dom.provincia.Provincias;
import domainapp.dom.provincia.ProvinciasRepository;
import domainapp.dom.tipoVehiculo.TipoVehiculo;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Localidades.class
)
@DomainServiceLayout(
        named = "Lugares",
        menuOrder = "6.2"
)
public class LocalidadesMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Localidades> listar() {
	        return localidadesRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Localidades> listarActivos() {
	        return localidadesRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<Localidades> listarInactivos() {
	        return localidadesRepository.listarInactivos();
	    }


	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "5")
	    public List<Localidades> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String localidadNombre){
	        return localidadesRepository.buscarPorNombre(localidadNombre);

	    }
	    
	    public List<Provincias> choices1Crear(){
	    	return provinciasRepository.listarActivos();
	    }
	    
	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Localidades")
	    public void vehiculoTitulo(){}

	    public static class CreateDomainEvent extends ActionDomainEvent<LocalidadesMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public Localidades crear(
	            @ParameterLayout(named="Nombre") final String localidadNombre,
	            @ParameterLayout(named="Provincia") final Provincias localidadProvincia){
	        return localidadesRepository.crear(localidadNombre, localidadProvincia);
	    }


	    @javax.inject.Inject
	    LocalidadesRepository localidadesRepository;
	    
	    @javax.inject.Inject
	    ProvinciasRepository provinciasRepository;

}
