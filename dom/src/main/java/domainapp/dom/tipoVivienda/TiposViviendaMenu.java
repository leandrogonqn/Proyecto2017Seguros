package domainapp.dom.tipoVivienda;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TiposVivienda.class
)
@DomainServiceLayout(
        named = "Combinado Familiar",
        menuOrder = "15"
)
public class TiposViviendaMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<TiposVivienda> listar() {
        return tipoViviendasRepository.listar();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<TiposVivienda> listarActivos() {
        return tipoViviendasRepository.listarActivos();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<TiposVivienda> listarInactivos() {
        return tipoViviendasRepository.listarInactivos();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
    @MemberOrder(sequence = "5")
    public List<TiposVivienda> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String tipoViviendaNombre
    ) {
        return tipoViviendasRepository.buscarPorNombre(tipoViviendaNombre);
    }
    
    @Property(
            editing = Editing.DISABLED, editingDisabledReason=" "
    )
    @MemberOrder(sequence="1")
    @ActionLayout(named="Tipos de vivienda")
    public void ocupacionesTitulo(){}

    public static class CreateDomainEvent extends ActionDomainEvent<TiposVivienda> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1.2")
    public TiposVivienda crear(@ParameterLayout(named="Nombre",labelPosition=LabelPosition.TOP)final String tipoViviendaNombre,@ParameterLayout(named="Descripción",labelPosition=LabelPosition.TOP)final String tipoViviendaDescripcion) {
        return tipoViviendasRepository.crear(tipoViviendaNombre,tipoViviendaDescripcion);
    }


    @javax.inject.Inject
    TiposViviendaRepository tipoViviendasRepository;
}
