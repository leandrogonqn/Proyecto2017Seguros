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

    public RiesgoCombinadosFamiliares crear(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,final String riesgoCombinadosFamiliaresDomicilio,final Ocupaciones riesgoCombinadosFamiliaresOcupacion,final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,final Date polizaFechaVencimientoPago, final DetalleTipoPagos polizaPago, final boolean polizaAlertaVencimientoPago, final double polizaImporteTotal) {
        final RiesgoCombinadosFamiliares object = new RiesgoCombinadosFamiliares(
        		polizaNumero,
        		polizaCliente,
        		polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaFechaVencimientoPago, 
        		polizaPago,
        		polizaAlertaVencimientoPago,
        		polizaImporteTotal);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgoCombinadosFamiliares renovacion(final String polizaNumero, final Clientes polizaCliente, final Companias polizaCompania,final String riesgoCombinadosFamiliaresDomicilio,final Ocupaciones riesgoCombinadosFamiliaresOcupacion,final TiposVivienda riesgoCombinadosFamiliaresTipoVivienda,final TipoTitulares riesgoCombinadosFamiliaresTipoTitular,final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,final Date polizaFechaVencimientoPago, final DetalleTipoPagos polizaPago, final boolean polizaAlertaVencimientoPago, final double polizaImporteTota,Polizas riesgoCombinadosFamiliares) {
        final RiesgoCombinadosFamiliares object = new RiesgoCombinadosFamiliares(
        		polizaNumero,
        		polizaCliente,
        		polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaFechaVencimientoPago, 
        		polizaPago,
        		polizaAlertaVencimientoPago,
        		polizaImporteTota);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

