package domainapp.dom.tipoTitular;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TipoTitular.class
)
public class TipoTitularRepository {

    public List<TipoTitular> listar() {
        return repositoryService.allInstances(TipoTitular.class);
    }

    public List<TipoTitular> buscarPorNombre(final String tipoTitularNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		TipoTitular.class,
                        "findByName",
                        "tipoTitularNombre", tipoTitularNombre.toLowerCase()));
    }

    public List<TipoTitular> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   TipoTitular.class,
                           "listarActivos"));
      }
       
       public List<TipoTitular> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  TipoTitular.class,
                              "listarInactivos"));
         }
    
    public TipoTitular crear(final String tipoTitularNombre) {
        final TipoTitular object = new TipoTitular(tipoTitularNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
