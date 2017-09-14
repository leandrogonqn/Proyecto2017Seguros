package domainapp.dom.detalleTipoPago;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DetalleTipoPago.class
)
public class DetalleTipoPagoRepository {
	
    public List<DetalleTipoPago> listar() {
        return repositoryService.allInstances(DetalleTipoPago.class);
    }
	
	public List<DetalleTipoPago> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	                  DetalleTipoPago.class,
	                  "listarActivos"));
	  }
	
	public List<DetalleTipoPago> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	                  DetalleTipoPago.class,
	                  "listarInactivos"));
	  }
	
    public List<DetalleTipoPago> buscarPorTitular(String tipoPagoTitular) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		DetalleTipoPago.class,
                        "buscarPorTitular",
                        "tipoPagoTitular", tipoPagoTitular.toLowerCase()));
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
