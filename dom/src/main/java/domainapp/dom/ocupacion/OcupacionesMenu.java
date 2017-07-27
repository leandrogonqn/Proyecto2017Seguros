package domainapp.dom.ocupacion;

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
        repositoryFor = Ocupaciones.class
)
@DomainServiceLayout(
        named = "Combinado Familiar",
        menuOrder = "15"
)
public class OcupacionesMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Ocupaciones> listar() {
        return ocupacionesRepository.listar();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<Ocupaciones> listarActivos() {
        return ocupacionesRepository.listarActivos();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<Ocupaciones> listarInactivos() {
        return ocupacionesRepository.listarInactivos();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
    @MemberOrder(sequence = "5")
    public List<Ocupaciones> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String ocupacionNombre
    ) {
        return ocupacionesRepository.buscarPorNombre(ocupacionNombre);
    }
    
    @Property(
            editing = Editing.DISABLED, editingDisabledReason=" "
    )
    @MemberOrder(sequence="1")
    @ActionLayout(named="Ocupación")
    public void ocupacionesTitulo(){}

    public static class CreateDomainEvent extends ActionDomainEvent<Ocupaciones> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1.2")
    public Ocupaciones crear(@ParameterLayout(named="Nombre")final String ocupacionNombre,@ParameterLayout(named="Descripcion")final String ocupacionDescripcion) {
        return ocupacionesRepository.crear(ocupacionNombre,ocupacionDescripcion);
    }


    @javax.inject.Inject
    OcupacionesRepository ocupacionesRepository;

}
