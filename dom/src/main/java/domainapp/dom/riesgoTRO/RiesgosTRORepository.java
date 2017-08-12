package domainapp.dom.riesgoTRO;

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
        repositoryFor = RiesgosTRO.class
)
public class RiesgosTRORepository {

	   public List<RiesgosTRO> listar() {
	        return repositoryService.allInstances(RiesgosTRO.class);
	    }

		public RiesgosTRO crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoTROMonto) {
			final RiesgosTRO object = new RiesgosTRO(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoTROMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public RiesgosTRO renovacion(
	    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoTROMonto,
	    		Polizas riesgoTRO) {
	        final RiesgosTRO object = new RiesgosTRO(
	        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
					riesgoTRO, riesgoTROMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    @javax.inject.Inject
	    RepositoryService repositoryService;
	    @javax.inject.Inject
	    ServiceRegistry2 serviceRegistry;
}
