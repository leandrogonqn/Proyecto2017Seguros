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

    public List<Vehiculos> buscarPorDominio(final String vehiculoDominio) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculos.class,
                        "buscarPorDominio",
                        "vehiculoDominio", vehiculoDominio.toLowerCase()));
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
    
  

    public Vehiculos crear(String vehiculoDominio, int vehiculoAnio, String vehiculoNumeroMotor, String vehiculoNumeroChasis,Modelos vehiculoModelo) {
        final Vehiculos object = new Vehiculos(vehiculoDominio, vehiculoAnio, vehiculoNumeroMotor, vehiculoNumeroChasis,vehiculoModelo);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
