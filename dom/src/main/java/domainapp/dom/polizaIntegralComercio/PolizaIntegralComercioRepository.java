package domainapp.dom.polizaIntegralComercio;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.estado.Estado;
import domainapp.dom.marca.Marca;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaIntegralComercio.class
)
public class PolizaIntegralComercioRepository {

    public List<PolizaIntegralComercio> listar() {
        return repositoryService.allInstances(PolizaIntegralComercio.class);
    }

    public PolizaIntegralComercio crear(
    		final String polizaNumero, 
    		final Cliente polizaCliente, 
    		final Compania polizaCompanias,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
    		final DetalleTipoPago polizaPago, 
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
    		final double riesgoIntegralComercioOtrosMonto) {
        final PolizaIntegralComercio object = new PolizaIntegralComercio(
        		polizaNumero,
        		polizaCliente,
        		polizaCompanias,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
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
    
    public PolizaIntegralComercio renovacion(
    		final String polizaNumero, 
    		final Cliente polizaCliente, 
    		final Compania polizaCompanias,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
    		final DetalleTipoPago polizaPago, 
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
    		final double riesgoIntegralComercioOtrosMonto, 
    		final Poliza riesgoAutomotor) {
        final PolizaIntegralComercio object = new PolizaIntegralComercio(
        		polizaNumero,
        		polizaCliente,
        		polizaCompanias,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
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
