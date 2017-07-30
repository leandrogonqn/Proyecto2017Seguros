package domainapp.dom.tipoVivienda;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TiposVivienda.class
)
public class TiposViviendaRepository {

    public List<TiposVivienda> listar() {
        return repositoryService.allInstances(TiposVivienda.class);
    }

    public List<TiposVivienda> buscarPorNombre(final String tipoViviendaNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		TiposVivienda.class,
                        "findByName",
                        "tipoViviendaNombre", tipoViviendaNombre.toLowerCase()));
    }

    public List<TiposVivienda> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   TiposVivienda.class,
                           "listarActivos"));
      }
       
       public List<TiposVivienda> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  TiposVivienda.class,
                              "listarInactivos"));
         }
    
    public TiposVivienda crear(final String tipoViviendaNombre, final String tipoViviendaDescripcion) {
        final TiposVivienda object = new TiposVivienda(tipoViviendaNombre,tipoViviendaDescripcion);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
