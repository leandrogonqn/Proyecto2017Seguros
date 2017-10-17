package domainapp.dom.polizaCombinadoFamiliar;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.adjunto.Adjunto;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.localidad.Localidad;
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
        repositoryFor = PolizaCombinadoFamiliar.class
)
public class PolizaCombinadoFamiliarRepository {

    public List<PolizaCombinadoFamiliar> listar() {
        return repositoryService.allInstances(PolizaCombinadoFamiliar.class);
    }

	public PolizaCombinadoFamiliar crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final String riesgoCombinadosFamiliaresDomicilio,
			final Localidad riesgoCombinadosFamiliaresLocalidad,
			final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitular riesgoCombinadosFamiliaresTipoTitular, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, 
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, 
			final double polizaImporteTotal,
    		final List<Adjunto> riesgoAutomotorListaAdjunto) {
		final PolizaCombinadoFamiliar object = new PolizaCombinadoFamiliar(polizaNumero, polizaCliente,
				polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresLocalidad,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
        		polizaPago,
        		polizaImporteTotal, 
        		riesgoAutomotorListaAdjunto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaCombinadoFamiliar renovacion(
    		final String polizaNumero, 
    		final Persona polizaCliente, 
    		final Compania polizaCompania,
    		final String riesgoCombinadosFamiliaresDomicilio,
    		final Localidad riesgoCombinadosFamiliaresLocalidad,
    		final Ocupacion riesgoCombinadosFamiliaresOcupacion,
    		final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
    		final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
    		final DetalleTipoPago polizaPago, 
    		final double polizaImporteTotal,
    		final List<Adjunto> riesgoAutomotorListaAdjunto,
    		Poliza riesgoCombinadosFamiliares) {
        final PolizaCombinadoFamiliar object = new PolizaCombinadoFamiliar(
        		polizaNumero,
        		polizaCliente,
        		polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresLocalidad,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
        		polizaPago,
        		polizaImporteTotal, 
        		riesgoAutomotorListaAdjunto,
        		riesgoCombinadosFamiliares);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

