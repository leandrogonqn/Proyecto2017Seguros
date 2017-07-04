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
        repositoryFor = Marcas.class
)
public class MarcasRepository {

    public List<Marcas> listar() {
        return repositoryService.allInstances(Marcas.class);
    }


    public List<Marcas> buscarPorNombre(final String marcaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Marcas.class,
                        "buscarPorNombre",

                        "marcaNombre", marcaNombre));

    }
    
    public List<Marcas> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Marcas.class,
                        "listarActivos"));
   }
    
    public List<Marcas> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Marcas.class,
                           "listarInactivos"));
      }
    
  

    public Marcas crear(final String marcaNombre) {
        final Marcas object = new Marcas(marcaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
