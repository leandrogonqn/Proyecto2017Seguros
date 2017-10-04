package domainapp.dom.debitoAutomatico;

import java.math.BigInteger;
import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.tarjetaDeCredito.TarjetaDeCredito;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DebitoAutomatico.class
)
public class DebitoAutomaticoRepository {

    public DebitoAutomatico crear(String tipoPagoTitular, Banco debitoAutomaticoBanco, BigInteger debitoAutomaticoCbu) {
        final DebitoAutomatico object = new DebitoAutomatico(tipoPagoTitular, debitoAutomaticoBanco, debitoAutomaticoCbu);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public List<DebitoAutomatico> listar() {
        return repositoryService.allInstances(DebitoAutomatico.class);
    }

	public List<DebitoAutomatico> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  DebitoAutomatico.class,
	                  "listarActivos"));
	  }
	
	public List<DebitoAutomatico> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  DebitoAutomatico.class,
	                  "listarInactivos"));
	  }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
