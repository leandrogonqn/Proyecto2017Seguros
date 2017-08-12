package domainapp.dom.riesgoRC;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.poliza.Polizas;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgosRC.class
)
public class RiesgosRCRepository {

    public List<RiesgosRC> listar() {
        return repositoryService.allInstances(RiesgosRC.class);
    }

	public RiesgosRC crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoRCMonto) {
		final RiesgosRC object = new RiesgosRC(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoRCMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgosRC renovacion(
    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoRCMonto,
    		Polizas riesgoRC) {
        final RiesgosRC object = new RiesgosRC(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
				riesgoRC, riesgoRCMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
