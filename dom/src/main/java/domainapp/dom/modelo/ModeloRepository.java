package domainapp.dom.modelo;

import java.security.Timestamp;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.marca.Marca;
import domainapp.dom.marca.MarcaRepository;
import domainapp.dom.tipoVehiculo.TipoVehiculo;
import domainapp.dom.tipoVehiculo.TipoVehiculoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Modelo.class
)
public class ModeloRepository {

    public List<Modelo> listar() {
        return repositoryService.allInstances(Modelo.class);
    }

    public List<Modelo> buscarPorNombre(final String modeloNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelo.class,
                        "buscarPorNombre",
                        "modeloNombre", modeloNombre.toLowerCase()));
    }
    
    public List<Modelo> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelo.class,
                        "listarActivos"));
   }
    
    public List<Modelo> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Modelo.class,
                           "listarInactivos"));
      }
    
    public Modelo crear(final String modeloNombre, TipoVehiculo modeloTipoVehiculo, Marca modeloMarcas) {
        final Modelo object = new Modelo(modeloNombre, modeloTipoVehiculo, modeloMarcas);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    MarcaRepository marcaRepository;

}
