package domainapp.dom.debitoAutomatico;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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

import domainapp.dom.banco.Banco;
import domainapp.dom.banco.BancoRepository;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DebitoAutomatico.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.3"
)
public class DebitoAutomaticoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<DebitoAutomaticoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Debito Automatico")
	    @MemberOrder(sequence = "1.2")
	    public DebitoAutomatico crear(
	    		@ParameterLayout(named="Titular") final String tipoPagoTitular,
	    		@ParameterLayout(named="Banco") final Banco debitoAutomaticoBanco,
	            @ParameterLayout(named="CBU") final BigInteger debitoAutomaticoCbu){
	        return debitoAutomaticoRepository.crear(tipoPagoTitular, debitoAutomaticoBanco, debitoAutomaticoCbu);
	    }
	    
	    public List<Banco> choices1Crear(){
	    	return debitoAutomaticoBancoRepository.listarActivos();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Debitos Automaticos")
	    @MemberOrder(sequence = "2")
	    public List<DebitoAutomatico> listarPagos() {
	        return debitoAutomaticoRepository.listar();
	    }

	    @javax.inject.Inject
	    DebitoAutomaticoRepository debitoAutomaticoRepository;
	    
	    @Inject
	    BancoRepository debitoAutomaticoBancoRepository;

}
