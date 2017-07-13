package domainapp.dom.pagoEfectivo;

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
        repositoryFor = PagosEfectivo.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.2"
)
public class PagosEfectivoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<PagosEfectivoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public PagosEfectivo crear(
	    		@ParameterLayout(named="Importe") final float tipoPagoImporte){
	        return pagoEfectivoRepository.crear(tipoPagoImporte);
	    }

	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Efectivo")
	    public void titulo(){}

	    @javax.inject.Inject
	    PagosEfectivoRepository pagoEfectivoRepository;

}
