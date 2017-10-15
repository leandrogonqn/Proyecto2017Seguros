package domainapp.dom.polizaAutomotor;

import java.util.Date;
import java.util.List;


import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import domainapp.dom.adjunto.Adjunto;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.estado.Estado;
import domainapp.dom.persona.Persona;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaAutomotor.class
)
public class PolizaAutomotoresRepository {

    public List<PolizaAutomotor> listar() {
        return repositoryService.allInstances(PolizaAutomotor.class);
    }

    public PolizaAutomotor crear(
    		final String polizaNumero, 
    		final Persona polizaCliente, 
    		final Compania polizaCompanias,
			final List<Vehiculo> riesgoAutomotorListaVehiculos,
    		final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
    		final DetalleTipoPago polizaPago, 
    		final double polizaImporteTotal,
    		final List<Adjunto> riesgoAutomotorListaAdjunto) {
        final PolizaAutomotor object = new PolizaAutomotor(
        		polizaNumero,
        		polizaCliente,
        		polizaCompanias,
        		riesgoAutomotorListaVehiculos,
        		riesgoAutomotorTiposDeCoberturas,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, 
				polizaTipoDePago,
				polizaPago, polizaImporteTotal, riesgoAutomotorListaAdjunto);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public PolizaAutomotor renovacion(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompanias, final List<Vehiculo> riesgoAutomotorListaVehiculos,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, 
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, final List<Adjunto> riesgoAutomotorAdjunto, final Poliza riesgoAutomotor) {
		final PolizaAutomotor object = new PolizaAutomotor(polizaNumero, polizaCliente, polizaCompanias,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoAutomotorAdjunto, riesgoAutomotor);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public List<PolizaAutomotor> listarPorEstado(final Estado polizaEstado) {
		return repositoryService.allMatches(
				new QueryDefault<>(PolizaAutomotor.class, "listarPorEstado", "polizaEstado", polizaEstado));
	}

	public boolean validar(Vehiculo vehiculo, Date fechaVigencia) {

		boolean validador = true;

		List<PolizaAutomotor> listaPolizas = listarPorEstado(Estado.vigente);
		listaPolizas.addAll(listarPorEstado(Estado.previgente));

		for (PolizaAutomotor r : listaPolizas) {
			for (Vehiculo v : r.getRiesgoAutomotorListaVehiculos()) {
				if (vehiculo.equals(v)) {
					if (fechaVigencia.before(r.getPolizaFechaVencimiento())) {
						validador = false;
					}
				}
			}
		}

		return validador;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
