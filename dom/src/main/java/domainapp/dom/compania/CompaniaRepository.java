package domainapp.dom.compania;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.marca.Marca;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Compania.class
)
public class CompaniaRepository {

    public List<Compania> listar() {
        return repositoryService.allInstances(Compania.class);
    }


    public List<Compania> buscarPorNombre(final String companiaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Compania.class,
                        "buscarPorNombre",
                        "companiaNombre", companiaNombre.toLowerCase()));

    }
    
    public List<Compania> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                		Compania.class,
                        "listarActivos"));
   }
    
    public List<Compania> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Compania.class,
                           "listarInactivos"));
      }
    
  

    public Compania crear(
    		final String companiaNombre, 
    		final String companiaDireccion, 
    		final String companiaTelefono) {
        final Compania object = new Compania(
        		companiaNombre,
        		companiaDireccion,
        		companiaTelefono);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
