package domainapp.dom.tipoTitular;

import java.util.List;

import javax.jdo.annotations.Column;

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
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.ocupacion.OcupacionesRepository;
import domainapp.dom.ocupacion.OcupacionesMenu.CreateDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = TipoTitulares.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "20"
)
public class TipoTitularesMenu {
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tipos de Titulares")
    @MemberOrder(sequence = "2")
    public List<TipoTitulares> listar() {
        return TipoTitularesRepository.listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", named="Buscar Tipos de Titulares")
    @MemberOrder(sequence = "5")
    public List<TipoTitulares> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String ocupacionNombre
    ) {
        return TipoTitularesRepository.buscarPorNombre(ocupacionNombre);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<TipoTitulares> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1.2")
    @ActionLayout(named="Crear Tipo de Titulare")
    public TipoTitulares crear(@ParameterLayout(named="Nombre",labelPosition=LabelPosition.TOP)@Parameter(optionality=Optionality.OPTIONAL) final String tipoTitularNombre){
        return TipoTitularesRepository.crear(tipoTitularNombre);
    }

    @javax.inject.Inject
    TipoTitularesRepository TipoTitularesRepository;
}
