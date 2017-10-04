package domainapp.dom.tipoVivienda;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TipoVivienda.class
)
public class TipoViviendaRepository {

    public List<TipoVivienda> listar() {
        return repositoryService.allInstances(TipoVivienda.class);
    }

    public List<TipoVivienda> buscarPorNombre(final String tipoViviendaNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		TipoVivienda.class,
                        "findByName",
                        "tipoViviendaNombre", tipoViviendaNombre.toLowerCase()));
    }

    public List<TipoVivienda> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   TipoVivienda.class,
                           "listarActivos"));
      }
       
       public List<TipoVivienda> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  TipoVivienda.class,
                              "listarInactivos"));
         }
    
    public TipoVivienda crear(final String tipoViviendaNombre) {
        final TipoVivienda object = new TipoVivienda(tipoViviendaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
