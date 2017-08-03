package domainapp.dom.tiposDeCoberturas;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
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


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TiposDeCoberturas.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "2"
)
public class TiposDeCoberturasMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tipos de Coberturas")
	    @MemberOrder(sequence = "2")
	    public List<TiposDeCoberturas> listar() {
	        return tipoDeCoberturaRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Tipos de Coberturas")
	    @MemberOrder(sequence = "5")
	    public List<TiposDeCoberturas> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String tipoDeCoberturaNombre){
	        return tipoDeCoberturaRepository.buscarPorNombre(tipoDeCoberturaNombre);

	    }
	    
	    public static class CreateDomainEvent extends ActionDomainEvent<TiposDeCoberturasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Tipos de Coberturas")
	    @MemberOrder(sequence = "1.2")
	    public TiposDeCoberturas crear(
	            @ParameterLayout(named="Nombre") final String tipoDeCoberturaNombre){
	        return tipoDeCoberturaRepository.crear(tipoDeCoberturaNombre);
	    }


	    @javax.inject.Inject
	    TiposDeCoberturasRepository tipoDeCoberturaRepository;

}
