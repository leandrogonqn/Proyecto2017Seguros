package domainapp.dom.riesgoConvenioMercantil;

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
        repositoryFor = RiesgosConvenioMercantil.class
)
public class RiesgosConvenioMercantilRepository {
	
	   public List<RiesgosConvenioMercantil> listar() {
	        return repositoryService.allInstances(RiesgosConvenioMercantil.class);
	    }

		public RiesgosConvenioMercantil crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoConvenioMercantilMonto) {
			final RiesgosConvenioMercantil object = new RiesgosConvenioMercantil(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoConvenioMercantilMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public RiesgosConvenioMercantil renovacion(
	    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoConvenioMercantilMonto,
	    		Polizas riesgoConvenioMercantil) {
	        final RiesgosConvenioMercantil object = new RiesgosConvenioMercantil(
	        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
					riesgoConvenioMercantil, riesgoConvenioMercantilMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    @javax.inject.Inject
	    RepositoryService repositoryService;
	    @javax.inject.Inject
	    ServiceRegistry2 serviceRegistry;
}
