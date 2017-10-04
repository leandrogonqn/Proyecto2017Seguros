package domainapp.dom.polizaTRO;

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
import domainapp.dom.polizaRC.RiesgoRC;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaTRO.class
)
public class PolizaTRORepository {

	   public List<PolizaTRO> listar() {
	        return repositoryService.allInstances(PolizaTRO.class);
	    }

		public PolizaTRO crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoTROMonto) {
			final PolizaTRO object = new PolizaTRO(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoTROMonto);
	        serviceRegistry.injectServicesInto(object);
	        repositoryService.persist(object);
	        return object;
	    }
	    
	    public PolizaTRO renovacion(
	    		final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
				final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoTROMonto,
	    		Poliza riesgoTRO) {
	        final PolizaTRO object = new PolizaTRO(
	        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
					polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, 
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
