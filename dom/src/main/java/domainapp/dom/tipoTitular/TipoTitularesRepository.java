package domainapp.dom.tipoTitular;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TipoTitulares.class
)
public class TipoTitularesRepository {

    public List<TipoTitulares> listar() {
        return repositoryService.allInstances(TipoTitulares.class);
    }

    public List<TipoTitulares> buscarPorNombre(final String tipoTitularNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		TipoTitulares.class,
                        "findByName",
                        "tipoTitularNombre", tipoTitularNombre.toLowerCase()));
    }

    public List<TipoTitulares> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   TipoTitulares.class,
                           "listarActivos"));
      }
       
       public List<TipoTitulares> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  TipoTitulares.class,
                              "listarInactivos"));
         }
    
    public TipoTitulares crear(final String tipoTitularNombre) {
        final TipoTitulares object = new TipoTitulares(tipoTitularNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
