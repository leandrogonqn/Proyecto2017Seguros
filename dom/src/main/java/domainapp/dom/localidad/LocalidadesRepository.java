package domainapp.dom.localidad;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.provincia.Provincias;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Localidades.class
)
public class LocalidadesRepository {

    public List<Localidades> listar() {
        return repositoryService.allInstances(Localidades.class);
    }


    public List<Localidades> buscarPorNombre(final String localidadNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Localidades.class,
                        "buscarPorNombre",
                        "localidadesNombre", localidadNombre.toLowerCase()));

    }
    
    public List<Localidades> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Localidades.class,
                        "listarActivos"));
   }
    
    public List<Localidades> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Localidades.class,
                           "listarInactivos"));
      }
    
  

    public Localidades crear(final String localidadNombre, Provincias localidadProvincia) {
        final Localidades object = new Localidades(localidadNombre, localidadProvincia);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
