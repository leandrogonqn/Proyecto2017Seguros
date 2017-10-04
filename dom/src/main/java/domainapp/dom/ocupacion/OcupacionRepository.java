package domainapp.dom.ocupacion;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Ocupacion.class
)
public class OcupacionRepository {

    public List<Ocupacion> listar() {
        return repositoryService.allInstances(Ocupacion.class);
    }

    public List<Ocupacion> buscarPorNombre(final String ocupacionNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Ocupacion.class,
                        "findByName",
                        "ocupacionNombre", ocupacionNombre.toLowerCase()));
    }

    public List<Ocupacion> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Ocupacion.class,
                           "listarActivos"));
      }
       
       public List<Ocupacion> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  Ocupacion.class,
                              "listarInactivos"));
         }
    
    public Ocupacion crear(final String ocupacionNombre) {
        final Ocupacion object = new Ocupacion(ocupacionNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
