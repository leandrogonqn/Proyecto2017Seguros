package domainapp.dom.polizaLCT;

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
        repositoryFor = PolizaLCT.class
)
public class PolizaLCTRepository {

    public List<PolizaLCT> listar() {
        return repositoryService.allInstances(PolizaLCT.class);
    }

	public PolizaLCT crear(final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto) {
		final PolizaLCT object = new PolizaLCT(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoLCTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaLCT renovacion(
    		final String polizaNumero, final Cliente polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto,
    		Poliza riesgoLCT) {
        final PolizaLCT object = new PolizaLCT(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, 
				riesgoLCT, riesgoLCTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

