package domainapp.dom.riesgoAutomotor;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.estado.Estado;
import domainapp.dom.marca.Marcas;
import domainapp.dom.poliza.Polizas;
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
    		final double polizaImporteTotal) {
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
        		polizaImporteTotal);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgoAutomotores renovacion(
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
    		final Polizas riesgoAutomotor) {
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
        		riesgoAutomotor);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
