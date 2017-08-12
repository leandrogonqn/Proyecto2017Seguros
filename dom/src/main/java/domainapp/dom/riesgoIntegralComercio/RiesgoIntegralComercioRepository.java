package domainapp.dom.riesgoIntegralComercio;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.estado.Estado;
import domainapp.dom.marca.Marcas;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = RiesgoIntegralComercio.class
)
public class RiesgoIntegralComercioRepository {

    public List<RiesgoIntegralComercio> listar() {
        return repositoryService.allInstances(RiesgoIntegralComercio.class);
    }

    public RiesgoIntegralComercio crear(
    		final String polizaNumero, 
    		final Clientes polizaCliente, 
    		final Companias polizaCompanias,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final DetalleTipoPagos polizaPago, 
    		final double polizaImporteTotal, 
    		float riesgoIntegralComercioRobo, 
    		float riesgoIntegralComercioCristales, 
    		float riesgoIntegralComercioIncendioEdificio, 
    		float riesgoIntegralComercioIncendioContenido, 
    		float riesgoIntegralComercioRc, 
    		float riesgoIntegralComercioRcl, 
    		float riesgoIntegralComercioDanioPorAgua,
    		float riesgoIntegralComercioRCC,
    		final String riesgoIntegralComercioOtrosNombre,
    		final float riesgoIntegralComercioOtrosMonto) {
        final RiesgoIntegralComercio object = new RiesgoIntegralComercio(
        		polizaNumero,
        		polizaCliente,
        		polizaCompanias,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaPago,
        		polizaImporteTotal,
        		riesgoIntegralComercioRobo,
	    		riesgoIntegralComercioCristales,
        		riesgoIntegralComercioIncendioEdificio,
        		riesgoIntegralComercioIncendioContenido, 
        		riesgoIntegralComercioRc,
        		riesgoIntegralComercioRcl,
        		riesgoIntegralComercioDanioPorAgua,
        		riesgoIntegralComercioRCC,
        		riesgoIntegralComercioOtrosNombre,
        		riesgoIntegralComercioOtrosMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public RiesgoIntegralComercio renovacion(
    		final String polizaNumero, 
    		final Clientes polizaCliente, 
    		final Companias polizaCompanias,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final DetalleTipoPagos polizaPago, 
    		final double polizaImporteTotal, 
    		float riesgoIntegralComercioRobo, 
    		float riesgoIntegralComercioCristales, 
    		float riesgoIntegralComercioIncendioEdificio, 
    		float riesgoIntegralComercioIncendioContenido, 
    		float riesgoIntegralComercioRc, 
    		float riesgoIntegralComercioRcl, 
    		float riesgoIntegralComercioDanioPorAgua,
    		float riesgoIntegralComercioRCC,
    		final String riesgoIntegralComercioOtrosNombre,
    		final float riesgoIntegralComercioOtrosMonto, 
    		final Polizas riesgoAutomotor) {
        final RiesgoIntegralComercio object = new RiesgoIntegralComercio(
        		polizaNumero,
        		polizaCliente,
        		polizaCompanias,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaPago,
        		polizaImporteTotal,
        		riesgoIntegralComercioRobo,
	    		riesgoIntegralComercioCristales,
        		riesgoIntegralComercioIncendioEdificio,
        		riesgoIntegralComercioIncendioContenido, 
        		riesgoIntegralComercioRc,
        		riesgoIntegralComercioRcl,
        		riesgoIntegralComercioDanioPorAgua,
        		riesgoIntegralComercioRCC,
        		riesgoIntegralComercioOtrosNombre,
        		riesgoIntegralComercioOtrosMonto,
        		riesgoAutomotor);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
