package domainapp.dom.vehiculo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
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
import domainapp.dom.modelo.Modelos;
import domainapp.dom.modelo.ModelosRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Vehiculos.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "3"
)
public class VehiculosMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Vehiculos> listar() {
	        return vehiculosRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Vehiculos> listarActivos() {
	        return vehiculosRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<Vehiculos> listarInactivos() {
	        return vehiculosRepository.listarInactivos();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "5")
	    public List<Vehiculos> buscarPorDominio(
	            @ParameterLayout(named="Dominio")
	            final String vehiculoDominio
	    ) {
	        return vehiculosRepository.buscarPorDominio(vehiculoDominio);
	    }
	    
	    public List<Modelos> choices4Crear(){
	    	return modelosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<VehiculosMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, semantics=SemanticsOf.IDEMPOTENT)
	    @MemberOrder(sequence = "1")
	    @ActionLayout()
	    public Vehiculos crear(
	            @ParameterLayout(named="Dominio") final String vehiculoDominio,
	    		@ParameterLayout(named="Año") final int vehiculoAnio,
	    		@ParameterLayout(named="Numero de Motor") final String vehiculoNumeroMotor,
	    		@ParameterLayout(named="Numero de Chasis") final String vehiculoNumeroChasis,
	    		@ParameterLayout(named="Modelo") final Modelos vehiculoModelo){
	        return vehiculosRepository.crear(vehiculoDominio, vehiculoAnio, vehiculoNumeroMotor, vehiculoNumeroChasis,vehiculoModelo);
	    }
	    
	    public Collection<Integer> choices1Crear(){
	    	ArrayList<Integer> numbers = new ArrayList<Integer>();
	    	Calendar hoy= Calendar.getInstance(); 
	    	int año= hoy.get(Calendar.YEAR); 
	    	for (int i = 1910; i <= año; i++)
	    	{
	    	   numbers.add(i);
	    	}
	    	return numbers;
	    }

	    @javax.inject.Inject
	    VehiculosRepository vehiculosRepository;
	    
	    @javax.inject.Inject
	    ModelosRepository modelosRepository;


}
