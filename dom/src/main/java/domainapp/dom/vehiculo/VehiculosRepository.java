package domainapp.dom.vehiculo;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.modelo.Modelos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Vehiculos.class
)
public class VehiculosRepository {

    public List<Vehiculos> listar() {
        return repositoryService.allInstances(Vehiculos.class);
    }

    public List<Vehiculos> buscarPorDominio(final String dominio) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculos.class,
                        "buscarPorDominio",
                        "dominio", dominio));
    }
    
    public List<Vehiculos> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculos.class,
                        "listarActivos"));
   }
    
    public List<Vehiculos> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Vehiculos.class,
                           "listarInactivos"));
      }
    
  

    public Vehiculos crear(String dominio, int anio, String numeroMotor, String numeroChasis,Modelos modelo) {
        final Vehiculos object = new Vehiculos(dominio, anio, numeroMotor, numeroChasis,modelo);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
