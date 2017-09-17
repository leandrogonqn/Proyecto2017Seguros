package domainapp.dom.poliza;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.estado.Estado;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Poliza.class
)
public class PolizaRepository {

    public List<Poliza> listar() {
        return repositoryService.allInstances(Poliza.class);
    }

    public List<Poliza> buscarpolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorNumeroPoliza",
                        "polizaNumero", polizaNumero.toLowerCase()));
    }
    
    public List<Poliza> listarPorEstado(final Estado polizaEstado) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Poliza.class,
                        "listarPorEstado",
                        "polizaEstado", polizaEstado));
    }
    
    public List<Poliza> buscarPorCliente(final Cliente polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
  
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}