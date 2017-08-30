package domainapp.dom.riesgoAutomotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.message.MessageService;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagosRepository;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturasRepository;
import domainapp.dom.vehiculo.Vehiculos;
import domainapp.dom.vehiculo.VehiculosRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = RiesgoAutomotores.class)
@DomainServiceLayout(named = "Polizas", menuOrder = "4")
public class RiesgoAutomotoresMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Polizas Auto")
	@MemberOrder(sequence = "2")
	public List<RiesgoAutomotores> listar() {
		List<RiesgoAutomotores> listaPolizasRiesgoAutomotores = polizasRepository.listar();
		for (int i = 0; i < listaPolizasRiesgoAutomotores.size(); i++) {
			listaPolizasRiesgoAutomotores.get(i).actualizarPoliza();
		}
		return listaPolizasRiesgoAutomotores;
	}

	public List<Clientes> choices1Crear() {
		return clientesRepository.listarActivos();
	}

	public List<Vehiculos> choices3Crear() {
		return vehiculosRepository.listarActivos();
	}

	public List<Companias> choices2Crear() {
		return companiaRepository.listarActivos();
	}

	public List<TiposDeCoberturas> choices4Crear() {
		return tiposDeCoberturasRepository.listarActivos();
	}

	public List<DetalleTipoPagos> choices8Crear() {
		return detalleTipoPagosRepository.listarActivos();
	}

	public static class CreateDomainEvent extends ActionDomainEvent<RiesgoAutomotoresMenu> {
	}

	@Action(domainEvent = CreateDomainEvent.class, invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Crear Poliza Auto")
	@MemberOrder(sequence = "1")
	public RiesgoAutomotores crear(@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Clientes polizaCliente,
			@ParameterLayout(named = "Compañia") final Companias polizaCompania,
			@ParameterLayout(named = "Vehiculo") final Vehiculos riesgoAutomotorVehiculo,
			@ParameterLayout(named = "Tipo de Cobertura") final TiposDeCoberturas riesgoAutomotorTiposDeCoberturas,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Pago") final DetalleTipoPagos polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal) {
		List<Vehiculos> riesgoAutomotorListaVehiculos = new ArrayList<>();
		riesgoAutomotorListaVehiculos.add(riesgoAutomotorVehiculo);
		return polizasRepository.crear(polizaNumero, polizaCliente, polizaCompania,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaPago, polizaImporteTotal);
	}

	@javax.inject.Inject
	RiesgoAutomotoresRepository polizasRepository;
	@javax.inject.Inject
	ClientesRepository clientesRepository;
	@Inject
	VehiculosRepository vehiculosRepository;
	@Inject
	DetalleTipoPagosRepository detalleTipoPagosRepository;
	@Inject
	CompaniaRepository companiaRepository;
	@Inject
	TiposDeCoberturasRepository tiposDeCoberturasRepository;
	@Inject
	MessageService messageService;
	@Inject
	RiesgoAutomotoresRepository riesgoAutomotoresRepository;
}
