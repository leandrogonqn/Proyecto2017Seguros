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

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;


@DomainService(
        nature = NatureOfService.VIEW,
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
	    public PagosEfectivo crear(){
        	PagosEfectivo a;
	    	List<DetalleTipoPagos> listaEfectivo = listarEfectivo();
          if(listaEfectivo.isEmpty())
          {
          	a = pagoEfectivoRepository.crear();
          }
          else
          {
          	a = (PagosEfectivo) listaEfectivo.get(0);
          }
	        return (PagosEfectivo) a;
	    }

	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Efectivo")
	    public void titulo(){}
	    
		public List<DetalleTipoPagos> listarEfectivo(){
		List<DetalleTipoPagos> listaEfectivo = listarActivos();
    	Iterator<DetalleTipoPagos> it = listaEfectivo.iterator();
    	while (it.hasNext()) {
    	    DetalleTipoPagos lista = it.next();
    	    if (!lista.getTipoPagoNombre().equals("Efectivo")) {
    	    	it.remove();
    	    }
    	}
		return listaEfectivo;
		}
		
		  public List<DetalleTipoPagos> listarActivos(){
				 return repositoryService.allMatches(
			          new QueryDefault<>(
			                  DetalleTipoPagos.class,
			                  "listarActivos"));
			  }
			  
			@javax.inject.Inject
			RepositoryService repositoryService;  

	    @javax.inject.Inject
	    public PagosEfectivoRepository pagoEfectivoRepository;
	    
	    @Inject
	    public DetalleTipoPagos detalleTipoPagos;

}
