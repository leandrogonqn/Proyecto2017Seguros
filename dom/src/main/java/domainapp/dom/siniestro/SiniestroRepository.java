package domainapp.dom.siniestro;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.poliza.Poliza;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Siniestro.class
)
public class SiniestroRepository {

    public List<Siniestro> listar() {
        return repositoryService.allInstances(Siniestro.class);
    }

    public List<Siniestro> buscarPorNombre(final String siniestroDescripcion) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Siniestro.class,
                        "findByName",
                        "siniestroDescripcion", siniestroDescripcion.toLowerCase()));
    }

    public List<Siniestro> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Siniestro.class,
                           "listarActivos"));
      }
       
       public List<Siniestro> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  Siniestro.class,
                              "listarInactivos"));
         }
    
    public Siniestro crear(final String siniestroDescripcion, Poliza siniestroPoliza, Date siniestroFecha) {
        final Siniestro object = new Siniestro(siniestroDescripcion, siniestroPoliza, siniestroFecha);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
