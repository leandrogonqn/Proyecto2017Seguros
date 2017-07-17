package domainapp.dom.provincia;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Provincias.class
)
public class ProvinciasRepository {

    public List<Provincias> listar() {
        return repositoryService.allInstances(Provincias.class);
    }


    public List<Provincias> buscarPorNombre(final String localidadNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Provincias.class,
                        "buscarPorNombre",
                        "localidadesNombre", localidadNombre.toLowerCase()));

    }
    
    public List<Provincias> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Provincias.class,
                        "listarActivos"));
   }
    
    public List<Provincias> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Provincias.class,
                           "listarInactivos"));
      }
    
  

    public Provincias crear(final String localidadNombre) {
        final Provincias object = new Provincias(localidadNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
