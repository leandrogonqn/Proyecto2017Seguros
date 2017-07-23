package domainapp.dom.riesgoAutomotor;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.poliza.Estado;
import domainapp.dom.vehiculo.Vehiculos;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgoAutomores.class
)
public class RiesgoAutomotoresRepository {

    public List<RiesgoAutomores> listar() {
        return repositoryService.allInstances(RiesgoAutomores.class);
    }


    public List<RiesgoAutomores> buscarPolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        RiesgoAutomores.class,
                        "buscarPolizaNumero",
                        "polizaNumero", polizaNumero));
    }
    
    public List<RiesgoAutomores> buscarPorCliente(final Clientes polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        RiesgoAutomores.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
    
  
    public RiesgoAutomores crear(
    		final String polizaNumero, 
    		final Clientes polizaCliente, 
    		final Vehiculos riesgoAutomotorVehiculo, 
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final Date polizaFechaVencimientoPago, 
    		final DetalleTipoPagos polizaPago, 
    		final boolean polizaAlertaVencimientoPago, 
    		final double polizaImporteTotal, 
    		Estado polizaEstado) {
        final RiesgoAutomores object = new RiesgoAutomores(
        		polizaNumero,
        		polizaCliente,
        		riesgoAutomotorVehiculo,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaFechaVencimientoPago, 
        		polizaPago,
        		polizaAlertaVencimientoPago,
        		polizaImporteTotal,
        		polizaEstado);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
