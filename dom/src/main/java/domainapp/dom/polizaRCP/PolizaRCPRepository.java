package domainapp.dom.polizaRCP;

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
import domainapp.dom.polizaRC.RiesgoRC;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaRCP.class
)
public class PolizaRCPRepository {
	   public List<PolizaRCP> listar() {
	        return repositoryService.allInstances(PolizaRCP.class);
	    }

		public PolizaRCP crear(final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoRCPMonto) {
			final PolizaRCP object = new PolizaRCP(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoRCPMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public PolizaRCP renovacion(
	    		final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoRCPMonto,
	    		Poliza riesgoRC) {
	        final PolizaRCP object = new PolizaRCP(
	        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
					riesgoRC, riesgoRCPMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    @javax.inject.Inject
	    RepositoryService repositoryService;
	    @javax.inject.Inject
	    ServiceRegistry2 serviceRegistry;
}
