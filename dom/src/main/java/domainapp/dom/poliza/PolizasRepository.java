package domainapp.dom.poliza;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.estado.Estado;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Polizas.class
)
public class PolizasRepository {

    public List<Polizas> listar() {
        return repositoryService.allInstances(Polizas.class);
    }

    public List<Polizas> buscarpolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Polizas.class,
                        "buscarPorNumeroPoliza",
                        "polizaNumero", polizaNumero.toLowerCase()));
    }
    
    public List<Polizas> listarPorEstado(final Estado polizaEstado) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Polizas.class,
                        "listarPorEstado",
                        "polizaEstado", polizaEstado));
    }
    
    public List<Polizas> buscarPorCliente(final Clientes polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Polizas.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
  
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
