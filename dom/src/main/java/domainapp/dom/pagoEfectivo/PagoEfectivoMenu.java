package domainapp.dom.pagoEfectivo;

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

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = PagoEfectivo.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.2"
)
public class PagoEfectivoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<PagoEfectivoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Efectivo")
	    public PagoEfectivo crear(){
        	PagoEfectivo a;
	    	List<DetalleTipoPago> listaEfectivo = pagoEfectivoRepository.listarEfectivo();
          if(listaEfectivo.isEmpty())
          {
          	a = pagoEfectivoRepository.crear();
          }
          else
          {
          	a = (PagoEfectivo) listaEfectivo.get(0);
          }
	        return (PagoEfectivo) a;
	    }

		@javax.inject.Inject
		RepositoryService repositoryService;  

	    @javax.inject.Inject
	    public PagoEfectivoRepository pagoEfectivoRepository;
	    
	    @Inject
	    public DetalleTipoPagoRepository detalleTipoPagosRepository;
	    

}
