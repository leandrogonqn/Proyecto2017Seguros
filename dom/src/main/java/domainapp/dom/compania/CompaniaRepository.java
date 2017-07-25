package domainapp.dom.compania;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.marca.Marcas;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Companias.class
)
public class CompaniaRepository {

    public List<Companias> listar() {
        return repositoryService.allInstances(Companias.class);
    }


    public List<Companias> buscarPorNombre(final String companiaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Companias.class,
                        "buscarPorNombre",
                        "companiaNombre", companiaNombre.toLowerCase()));

    }
    
    public List<Companias> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                		Companias.class,
                        "listarActivos"));
   }
    
    public List<Companias> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Companias.class,
                           "listarInactivos"));
      }
    
  

    public Companias crear(
    		final String companiaNombre, 
    		final String companiaDireccion, 
    		final String companiaTelefono) {
        final Companias object = new Companias(
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
