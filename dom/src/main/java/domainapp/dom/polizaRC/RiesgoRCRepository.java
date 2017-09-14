package domainapp.dom.polizaRC;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.poliza.Poliza;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgoRC.class
)
public class RiesgoRCRepository {

    public List<RiesgoRC> listar() {
        return repositoryService.allInstances(RiesgoRC.class);
    }

	public RiesgoRC crear(final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoRCMonto) {
		final RiesgoRC object = new RiesgoRC(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoRCMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgoRC renovacion(
    		final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoRCMonto,
    		Poliza riesgoRC) {
        final RiesgoRC object = new RiesgoRC(
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
