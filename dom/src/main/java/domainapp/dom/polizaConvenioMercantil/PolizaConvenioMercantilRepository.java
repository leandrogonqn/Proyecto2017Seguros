package domainapp.dom.polizaConvenioMercantil;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.persona.Persona;
import domainapp.dom.poliza.Poliza;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaConvenioMercantil.class
)
public class PolizaConvenioMercantilRepository {
	
	   public List<PolizaConvenioMercantil> listar() {
	        return repositoryService.allInstances(PolizaConvenioMercantil.class);
	    }

		public PolizaConvenioMercantil crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoConvenioMercantilMonto) {
			final PolizaConvenioMercantil object = new PolizaConvenioMercantil(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoConvenioMercantilMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public PolizaConvenioMercantil renovacion(
	    		final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoConvenioMercantilMonto,
	    		Poliza riesgoConvenioMercantil) {
	        final PolizaConvenioMercantil object = new PolizaConvenioMercantil(
	        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, 
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
