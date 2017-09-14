package domainapp.dom.detalleTipoPago;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DetalleTipoPago.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.1"
)
public class DetalleTipoPagoMenu {
	
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Todos los Pagos")
	    @MemberOrder(sequence = "2")
	    public List<DetalleTipoPago> listarPagos() {
	        return detalleTipoPagosRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "5")
	    public List<DetalleTipoPago> buscarPorTitular(
	            @ParameterLayout(named="Titular")
	            final String tipoPagoTitular
	    ) {
	        return detalleTipoPagosRepository.buscarPorTitular(tipoPagoTitular);
	    }
	    
			  
		@javax.inject.Inject
		DetalleTipoPagoRepository detalleTipoPagosRepository;
		
}
