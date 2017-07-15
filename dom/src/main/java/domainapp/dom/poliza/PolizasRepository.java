package domainapp.dom.poliza;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;


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
                        "buscarpolizaNumero",
                        "polizaNumero", polizaNumero.toLowerCase()));

    }
    
  
    public Polizas crear(final String polizaNumero, final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,final Date polizaFechaVencimientoPago,final double polizaPrecioTotal,Clientes cliente) {
        final Polizas object = new Polizas(polizaNumero,polizaFechaEmision,polizaFechaVigencia, polizaFechaVencimiento,polizaFechaVencimientoPago, polizaPrecioTotal,cliente);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
