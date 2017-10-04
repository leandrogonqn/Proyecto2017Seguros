package domainapp.dom.polizaART;

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
import domainapp.dom.ocupacion.Ocupacion;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.polizaAutomotor.PolizaAutomotor;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaART.class
)
public class PolizaARTRepository {

    public List<PolizaART> listar() {
        return repositoryService.allInstances(PolizaART.class);
    }

	public PolizaART crear(
			final String polizaNumero, 
			final Cliente polizaCliente, 
			final Compania polizaCompania,
			final Date polizaFechaEmision, 
			final Date polizaFechaVigencia, 
			final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, 
			final double polizaImporteTotal, 
			final float riesgoARTMonto) {
		final PolizaART object = new PolizaART(
				polizaNumero, 
				polizaCliente, 
				polizaCompania, 
				polizaFechaEmision,
				polizaFechaVigencia, 
				polizaFechaVencimiento, 
				polizaTipoDePago,
				polizaPago, 
				polizaImporteTotal, 
				riesgoARTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaART renovacion(
    		final String polizaNumero, 
    		final Cliente polizaCliente, 
    		final Compania polizaCompania,
			final Date polizaFechaEmision, 
			final Date polizaFechaVigencia, 
			final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, 
			final double polizaImporteTotal, 
			final float riesgoARTMonto,
    		Poliza riesgoART) {
        final PolizaART object = new PolizaART(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago,
				polizaPago, polizaImporteTotal, 
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

