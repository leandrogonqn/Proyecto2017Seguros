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

import domainapp.dom.banco.Bancos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DetalleTipoPagos.class
)
public class DetalleTipoPagosRepository {
	
    public List<DetalleTipoPagos> listar() {
        return repositoryService.allInstances(DetalleTipoPagos.class);
    }
	
	public List<DetalleTipoPagos> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	                  DetalleTipoPagos.class,
	                  "listarActivos"));
	  }
	
	public List<DetalleTipoPagos> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	                  DetalleTipoPagos.class,
	                  "listarInactivos"));
	  }
	
    public List<DetalleTipoPagos> buscarPorTitular(String tipoPagoTitular) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		DetalleTipoPagos.class,
                        "buscarPorTitular",
                        "tipoPagoTitular", tipoPagoTitular.toLowerCase()));
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
