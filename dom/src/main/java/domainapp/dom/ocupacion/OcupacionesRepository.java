package domainapp.dom.ocupacion;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Ocupaciones.class
)
public class OcupacionesRepository {

    public List<Ocupaciones> listar() {
        return repositoryService.allInstances(Ocupaciones.class);
    }

    public List<Ocupaciones> buscarPorNombre(final String ocupacionNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Ocupaciones.class,
                        "findByName",
                        "ocupacionNombre", ocupacionNombre.toLowerCase()));
    }

    public List<Ocupaciones> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Ocupaciones.class,
                           "listarActivos"));
      }
       
       public List<Ocupaciones> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  Ocupaciones.class,
                              "listarInactivos"));
         }
    
    public Ocupaciones crear(final String ocupacionNombre, final String ocupacionDescripcion) {
        final Ocupaciones object = new Ocupaciones(ocupacionNombre,ocupacionDescripcion);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
