package domainapp.dom.riesgoCombinadoFamiliar;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.localidad.Localidades;
import domainapp.dom.ocupacion.Ocupaciones;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotores;
import domainapp.dom.tipoTitular.TipoTitulares;
import domainapp.dom.tipoVivienda.TiposVivienda;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgoCombinadosFamiliares.class
)
public class RiesgoCombinadosFamiliaresRepository {

    public List<RiesgoCombinadosFamiliares> listar() {
        return repositoryService.allInstances(RiesgoCombinadosFamiliares.class);
    }

	public RiesgoCombinadosFamiliares crear(final String polizaNumero, final Clientes polizaCliente,
			final Companias polizaCompania, final String riesgoCombinadosFamiliaresDomicilio,
			final Localidades riesgoCombinadosFamiliaresLocalidad,
			final Ocupaciones riesgoCombinadosFamiliaresOcupacion,
			final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitulares riesgoCombinadosFamiliaresTipoTitular, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, 
			final DetalleTipoPagos polizaPago, 
			final double polizaImporteTotal) {
		final RiesgoCombinadosFamiliares object = new RiesgoCombinadosFamiliares(polizaNumero, polizaCliente,
				polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresLocalidad,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaPago,
        		polizaImporteTotal);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgoCombinadosFamiliares renovacion(
    		final String polizaNumero, 
    		final Clientes polizaCliente, 
    		final Companias polizaCompania,
    		final String riesgoCombinadosFamiliaresDomicilio,
    		final Localidades riesgoCombinadosFamiliaresLocalidad,
    		final Ocupaciones riesgoCombinadosFamiliaresOcupacion,
    		final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,
    		final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final DetalleTipoPagos polizaPago, 
    		final double polizaImporteTotal,
    		Polizas riesgoCombinadosFamiliares) {
        final RiesgoCombinadosFamiliares object = new RiesgoCombinadosFamiliares(
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
        		polizaPago,
        		polizaImporteTotal,
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

