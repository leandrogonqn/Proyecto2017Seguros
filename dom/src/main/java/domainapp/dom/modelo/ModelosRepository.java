package domainapp.dom.modelo;

import java.security.Timestamp;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.marca.Marcas;
import domainapp.dom.marca.MarcasRepository;
import domainapp.dom.tipoVehiculo.TipoVehiculo;
import domainapp.dom.tipoVehiculo.TipoVehiculoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Modelos.class
)
public class ModelosRepository {

    public List<Modelos> listar() {
        return repositoryService.allInstances(Modelos.class);
    }

    public List<Modelos> buscarPorNombre(final String modeloNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelos.class,
                        "buscarPorNombre",
                        "modeloNombre", modeloNombre));
    }
    
    public List<Modelos> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelos.class,
                        "listarActivos"));
   }
    
    public List<Modelos> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Modelos.class,
                           "listarInactivos"));
      }
    
    public Modelos crear(final String modeloNombre, TipoVehiculo modeloTipoVehiculo, Marcas modeloMarcas) {
        final Modelos object = new Modelos(modeloNombre, modeloTipoVehiculo, modeloMarcas);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    MarcasRepository marcaRepository;

}
