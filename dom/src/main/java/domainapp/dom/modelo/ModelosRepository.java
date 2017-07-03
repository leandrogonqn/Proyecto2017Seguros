package domainapp.dom.modelo;

import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.marca.Marcas;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Modelos.class
)
public class ModelosRepository {

    public List<Modelos> listar() {
        return repositoryService.allInstances(Modelos.class);
    }

    public List<Modelos> buscarPorNombre(final String nombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelos.class,
                        "buscarPorNombre",
                        "nombre", nombre));
    }
    
    public List<Modelos> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelos.class,
                        "listarActivos"));
   }
    
    public List<Modelos> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Modelos.class,
                           "listarInactivos"));
      }
    
  

    public Modelos crear(final String nombre, final Marcas marca) {
        final Modelos object = new Modelos(nombre,marca);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
