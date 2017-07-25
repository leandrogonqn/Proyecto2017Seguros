package domainapp.dom.riesgoAutomotor;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.poliza.Estado;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgoAutomotores.class
)
public class RiesgoAutomotoresRepository {

    public List<RiesgoAutomotores> listar() {
        return repositoryService.allInstances(RiesgoAutomotores.class);
    }


    public List<RiesgoAutomotores> buscarPolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        RiesgoAutomotores.class,
                        "buscarPolizaNumero",
                        "polizaNumero", polizaNumero));
    }
    
    public List<RiesgoAutomotores> buscarPorCliente(final Clientes polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        RiesgoAutomotores.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
    
  
    public RiesgoAutomotores crear(
    		final String polizaNumero, 
    		final Clientes polizaCliente, 
    		final Vehiculos riesgoAutomotorVehiculo, 
    		final Companias polizaCompanias,
    		final TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final Date polizaFechaVencimientoPago, 
    		final DetalleTipoPagos polizaPago, 
    		final boolean polizaAlertaVencimientoPago, 
    		final double polizaImporteTotal, 
    		Estado polizaEstado) {
        final RiesgoAutomotores object = new RiesgoAutomotores(
        		polizaNumero,
        		polizaCliente,
        		riesgoAutomotorVehiculo,
        		polizaCompanias,
        		riesgoAutomotorTiposDeCoberturas,
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
