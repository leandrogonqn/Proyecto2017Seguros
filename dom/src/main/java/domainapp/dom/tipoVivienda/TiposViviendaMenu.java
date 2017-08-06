package domainapp.dom.tipoVivienda;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = TiposVivienda.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "15"
)
public class TiposViviendaMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tipos de Viviendas")
    @MemberOrder(sequence = "2")
    public List<TiposVivienda> listar() {
        return tipoViviendasRepository.listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", named="Buscar Tipos de Viviendas")
    @MemberOrder(sequence = "5")
    public List<TiposVivienda> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String tipoViviendaNombre
    ) {
        return tipoViviendasRepository.buscarPorNombre(tipoViviendaNombre);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<TiposVivienda> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named="Crear Tipo de Vivienda")
    @MemberOrder(sequence = "1.2")
    public TiposVivienda crear(@ParameterLayout(named="Nombre",labelPosition=LabelPosition.TOP)final String tipoViviendaNombre,@ParameterLayout(named="Descripción",labelPosition=LabelPosition.TOP)final String tipoViviendaDescripcion) {
        return tipoViviendasRepository.crear(tipoViviendaNombre,tipoViviendaDescripcion);
    }


    @javax.inject.Inject
    TiposViviendaRepository tipoViviendasRepository;
}
