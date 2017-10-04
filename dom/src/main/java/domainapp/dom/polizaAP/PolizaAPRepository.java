package domainapp.dom.polizaAP;

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
import domainapp.dom.persona.Persona;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.polizaAutomotor.PolizaAutomotor;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaAP.class
)
public class PolizaAPRepository {

    public List<PolizaAP> listar() {
        return repositoryService.allInstances(PolizaAP.class);
    }

	public PolizaAP crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoAPMuerte, 
			final float riesgoAPInvalidez, final float riesgoAPAMF) {
		final PolizaAP object = new PolizaAP(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoAPMuerte, 
				riesgoAPInvalidez, riesgoAPAMF);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaAP renovacion(
    		final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoAPMuerte, 
			final float riesgoAPInvalidez, final float riesgoAPAMF, Poliza riesgoAP) {
        final PolizaAP object = new PolizaAP(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, 
				riesgoAP, riesgoAPMuerte,riesgoAPInvalidez, riesgoAPAMF);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

