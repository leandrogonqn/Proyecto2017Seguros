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
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import domainapp.dom.modelo.Modelo;
import domainapp.dom.modelo.ModeloRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "3"
)
public class VehiculoMenu {
	
	  	@Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Vehiculos")
	    @MemberOrder(sequence = "2")
	    public List<Vehiculo> listar() {
	        return vehiculosRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Vehiculo por Dominio")
	    @MemberOrder(sequence = "5")
	    public List<Vehiculo> buscarPorDominio(
	            @ParameterLayout(named="Dominio")
	            final String vehiculoDominio
	    ) {
	        return vehiculosRepository.buscarPorDominio(vehiculoDominio);
	    }
	    
	    public List<Modelo> choices4Crear(){
	    	return modelosRepository.listarActivos();
	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<VehiculoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, semantics=SemanticsOf.IDEMPOTENT)
	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Vehiculo")
	    public Vehiculo crear(
	            @ParameterLayout(named="Dominio") final String vehiculoDominio,
	    		@ParameterLayout(named="Año") final int vehiculoAnio,
	    		@ParameterLayout(named="Numero de Motor") final String vehiculoNumeroMotor,
	    		@ParameterLayout(named="Numero de Chasis") final String vehiculoNumeroChasis,
	    		@ParameterLayout(named="Modelo") final Modelo vehiculoModelo){
	        return vehiculosRepository.crear(vehiculoDominio, vehiculoAnio, vehiculoNumeroMotor, vehiculoNumeroChasis,vehiculoModelo);
	    }
	    
	    public Collection<Integer> choices1Crear(){
	    	ArrayList<Integer> numbers = new ArrayList<Integer>();
	    	Calendar hoy= Calendar.getInstance(); 
	    	int año= hoy.get(Calendar.YEAR); 
	    	for (int i = 1980; i <= año; i++)
	    	{
	    	   numbers.add(i);
	    	}
	    	return numbers;
	    }

	    @javax.inject.Inject
	    VehiculoRepository vehiculosRepository;
	    
	    @javax.inject.Inject
	    ModeloRepository modelosRepository;


}
