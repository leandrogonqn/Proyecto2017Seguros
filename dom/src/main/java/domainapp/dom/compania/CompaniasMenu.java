package domainapp.dom.compania;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Companias.class
)
@DomainServiceLayout(
        named = "Companias",
        menuOrder = "8"
)
public class CompaniasMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<Companias> listar() {
	        return companiasRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<Companias> listarActivos() {
	        return companiasRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<Companias> listarInactivos() {
	        return companiasRepository.listarInactivos();
	    }


	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "5")
	    public List<Companias> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String companiaNombre){
	        return companiasRepository.buscarPorNombre(companiaNombre);

	    }
	    
	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="COMPANIAS")
	    public void vehiculoTitulo(){}

	    public static class CreateDomainEvent extends ActionDomainEvent<CompaniasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public Companias crear(
	            @ParameterLayout(named="Nombre") final String companiaNombre){
	        return companiasRepository.crear(companiaNombre);
	    }


	    @javax.inject.Inject
	    CompaniaRepository companiasRepository;
}
