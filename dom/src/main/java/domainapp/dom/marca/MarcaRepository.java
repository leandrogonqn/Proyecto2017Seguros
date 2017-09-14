package domainapp.dom.marca;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Marca.class
)
public class MarcaRepository {

    public List<Marca> listar() {
        return repositoryService.allInstances(Marca.class);
    }


    public List<Marca> buscarPorNombre(final String marcaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Marca.class,
                        "buscarPorNombre",
                        "marcasNombre", marcaNombre.toLowerCase()));

    }
    
    public List<Marca> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Marca.class,
                        "listarActivos"));
   }
    
    public List<Marca> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Marca.class,
                           "listarInactivos"));
      }
    
  

    public Marca crear(final String marcaNombre) {
        final Marca object = new Marca(marcaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
