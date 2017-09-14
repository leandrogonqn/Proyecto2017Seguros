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
        repositoryFor = Provincia.class
)
public class ProvinciaRepository {

    public List<Provincia> listar() {
        return repositoryService.allInstances(Provincia.class);
    }


    public List<Provincia> buscarPorNombre(final String localidadNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Provincia.class,
                        "buscarPorNombre",
                        "localidadesNombre", localidadNombre.toLowerCase()));

    }
    
    public List<Provincia> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Provincia.class,
                        "listarActivos"));
   }
    
    public List<Provincia> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Provincia.class,
                           "listarInactivos"));
      }
    
  

    public Provincia crear(final String localidadNombre) {
        final Provincia object = new Provincia(localidadNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
