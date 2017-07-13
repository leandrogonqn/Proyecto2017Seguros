package domainapp.dom.debitoAutomatico;

import java.math.BigInteger;
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
        repositoryFor = DebitosAutomaticos.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10"
)
public class DebitosAutomaticosMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<DebitosAutomaticosMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public DebitosAutomaticos crear(
	            @ParameterLayout(named="CBU") final BigInteger debitoAutomaticoCbu,
	    		@ParameterLayout(named="Importe") final float tipoPagoImporte){
	        return debitoAutomaticoRepository.crear(debitoAutomaticoCbu, tipoPagoImporte);
	    }

	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Debito Automatico")
	    public void titulo(){}

	    @javax.inject.Inject
	    DebitosAutomaticosRepository debitoAutomaticoRepository;

}
