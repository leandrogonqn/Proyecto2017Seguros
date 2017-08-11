package domainapp.dom.riesgoART;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotores;
import domainapp.dom.tipoTitular.TipoTitulares;
import domainapp.dom.tipoVivienda.TiposVivienda;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgosART.class
)
public class RiesgosARTRepository {

    public List<RiesgosART> listar() {
        return repositoryService.allInstances(RiesgosART.class);
    }

	public RiesgosART crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoARTMonto) {
		final RiesgosART object = new RiesgosART(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoARTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgosART renovacion(
    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoARTMonto,
    		Polizas riesgoART) {
        final RiesgosART object = new RiesgosART(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
				riesgoART, riesgoARTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

