package domainapp.dom.ocupacion;

import java.util.List;
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
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Ocupacion.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "10"
)
public class OcupacionMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Ocupaciones")
    @MemberOrder(sequence = "2")
    public List<Ocupacion> listar() {
        return ocupacionesRepository.listar();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", named="Buscar Ocupacion")
    @MemberOrder(sequence = "5")
    public List<Ocupacion> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String ocupacionNombre
    ) {
        return ocupacionesRepository.buscarPorNombre(ocupacionNombre);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<Ocupacion> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named="Crear Ocupacion")
    @MemberOrder(sequence = "1.2")
    public Ocupacion crear(@ParameterLayout(named="Nombre")final String ocupacionNombre,@ParameterLayout(named="Descripcion")final String ocupacionDescripcion) {
        return ocupacionesRepository.crear(ocupacionNombre,ocupacionDescripcion);
    }

    @javax.inject.Inject
    OcupacionRepository ocupacionesRepository;

}
