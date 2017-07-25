package domainapp.dom.tiposDeCoberturas;

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


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TiposDeCoberturas.class
)
@DomainServiceLayout(
        named = "Tipos De Coberturas",
        menuOrder = "3.1"
)
public class TiposDeCoberturasMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "2")
	    public List<TiposDeCoberturas> listar() {
	        return tipoDeCoberturaRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "3")
	    public List<TiposDeCoberturas> listarActivos() {
	        return tipoDeCoberturaRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "4")
	    public List<TiposDeCoberturas> listarInactivos() {
	        return tipoDeCoberturaRepository.listarInactivos();
	    }


	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	    @MemberOrder(sequence = "5")
	    public List<TiposDeCoberturas> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String tipoDeCoberturaNombre){
	        return tipoDeCoberturaRepository.buscarPorNombre(tipoDeCoberturaNombre);

	    }
	    
	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Tipo De Cobertura")
	    public void vehiculoTitulo(){}

	    public static class CreateDomainEvent extends ActionDomainEvent<TiposDeCoberturasMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public TiposDeCoberturas crear(
	            @ParameterLayout(named="Nombre") final String tipoDeCoberturaNombre){
	        return tipoDeCoberturaRepository.crear(tipoDeCoberturaNombre);
	    }


	    @javax.inject.Inject
	    TiposDeCoberturasRepository tipoDeCoberturaRepository;

}
