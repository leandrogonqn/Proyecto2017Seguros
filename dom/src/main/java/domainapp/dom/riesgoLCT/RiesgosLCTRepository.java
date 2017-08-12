package domainapp.dom.riesgoLCT;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotores;
import domainapp.dom.tipoTitular.TipoTitulares;
import domainapp.dom.tipoVivienda.TiposVivienda;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgosLCT.class
)
public class RiesgosLCTRepository {

    public List<RiesgosLCT> listar() {
        return repositoryService.allInstances(RiesgosLCT.class);
    }

	public RiesgosLCT crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto) {
		final RiesgosLCT object = new RiesgosLCT(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, riesgoLCTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgosLCT renovacion(
    		final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final DetalleTipoPagos polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto,
    		Polizas riesgoLCT) {
        final RiesgosLCT object = new RiesgosLCT(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal, 
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
