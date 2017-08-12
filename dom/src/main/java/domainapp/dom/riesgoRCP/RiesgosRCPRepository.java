package domainapp.dom.riesgoRCP;

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
import domainapp.dom.riesgoRC.RiesgosRC;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgosRCP.class
)
public class RiesgosRCPRepository {
	   public List<RiesgosRCP> listar() {
	        return repositoryService.allInstances(RiesgosRCP.class);
	    }

		public RiesgosRCP crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoRCPMonto) {
			final RiesgosRCP object = new RiesgosRCP(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoRCPMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public RiesgosRCP renovacion(
	    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoRCPMonto,
	    		Polizas riesgoRC) {
	        final RiesgosRCP object = new RiesgosRCP(
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
