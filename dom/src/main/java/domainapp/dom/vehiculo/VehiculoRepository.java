package domainapp.dom.vehiculo;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.modelo.Modelo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Vehiculo.class
)
public class VehiculoRepository {

    public List<Vehiculo> listar() {
        return repositoryService.allInstances(Vehiculo.class);
    }

    public List<Vehiculo> buscarPorDominio(final String vehiculoDominio) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculo.class,
                        "buscarPorDominio",
                        "vehiculoDominio", vehiculoDominio.toLowerCase()));
    }
    
    public List<Vehiculo> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculo.class,
                        "listarActivos"));
   }
    
    public List<Vehiculo> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Vehiculo.class,
                           "listarInactivos"));
      }
    
  

    public Vehiculo crear(String vehiculoDominio, int vehiculoAnio, String vehiculoNumeroMotor, String vehiculoNumeroChasis,Modelo vehiculoModelo) {
        final Vehiculo object = new Vehiculo(vehiculoDominio, vehiculoAnio, vehiculoNumeroMotor, vehiculoNumeroChasis,vehiculoModelo);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
