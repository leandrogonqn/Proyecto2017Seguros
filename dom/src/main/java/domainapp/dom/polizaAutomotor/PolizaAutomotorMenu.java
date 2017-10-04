package domainapp.dom.polizaAutomotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.debitoAutomatico.DebitoAutomaticoRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoMenu;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.tarjetaDeCredito.TarjetaDeCreditoRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;
import domainapp.dom.vehiculo.Vehiculo;
import domainapp.dom.vehiculo.VehiculoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaAutomotor.class)
@DomainServiceLayout(named = "Polizas", menuOrder = "4")
public class PolizaAutomotorMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Polizas Auto")
	@MemberOrder(sequence = "2")
	public List<PolizaAutomotor> listar() {
		List<PolizaAutomotor> listaPolizasRiesgoAutomotores = riesgoAutomotorRepository.listar();
		for (int i = 0; i < listaPolizasRiesgoAutomotores.size(); i++) {
			listaPolizasRiesgoAutomotores.get(i).actualizarPoliza();
		}
		return listaPolizasRiesgoAutomotores;
	}


	
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Crear Poliza Auto")
	@MemberOrder(sequence = "1")
	public PolizaAutomotor crear(
			@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Cliente polizaCliente,
			@ParameterLayout(named = "Compañia") final Compania polizaCompania,
			@ParameterLayout(named = "Vehiculo") final Vehiculo riesgoAutomotorVehiculo,
			@ParameterLayout(named = "Tipo de Cobertura") final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal) {
		List<Vehiculo> riesgoAutomotorListaVehiculos = new ArrayList<>();
		riesgoAutomotorListaVehiculos.add(riesgoAutomotorVehiculo);
		return riesgoAutomotorRepository.crear(polizaNumero, polizaCliente, polizaCompania,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal);
	}
	
	public List<Cliente> choices1Crear() {
		return clientesRepository.listarActivos();
	}

	public List<Compania> choices2Crear() {
		return companiaRepository.listarActivos();
	}
	
	public List<Vehiculo> choices3Crear() {
		return vehiculosRepository.listarActivos();
	}

	public List<TipoDeCobertura> choices4Crear() {
		return tiposDeCoberturasRepository.listarActivos();
	}
	
	public List<DetalleTipoPago> choices9Crear(			
			final String polizaNumero,
			final Cliente polizaCliente,
			final Compania polizaCompania,
			final Vehiculo riesgoAutomotorVehiculo,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision,
			final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago,
			final double polizaImporteTotal) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	public String validateCrear(
			final String polizaNumero,
			final Cliente polizaCliente,
			final Compania polizaCompania,
			final Vehiculo riesgoAutomotorVehiculo,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision,
			final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago,
			final double polizaImporteTotal){
		if (polizaFechaVigencia.after(polizaFechaVencimiento)){
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		if (riesgoAutomotorRepository.validar(riesgoAutomotorVehiculo, polizaFechaVigencia)==false){
			return "ERROR: vehiculo existente en otra poliza vigente";
		}
		return "";
	}

	@javax.inject.Inject
	ClienteRepository clientesRepository;
	@Inject
	VehiculoRepository vehiculosRepository;
	@Inject
	CompaniaRepository companiaRepository;
	@Inject
	TipoDeCoberturaRepository tiposDeCoberturasRepository;
	@Inject
	MessageService messageService;
	@Inject
	PolizaAutomotoresRepository riesgoAutomotorRepository;
	@Inject
	TarjetaDeCreditoRepository tarjetaDeCreditoRepository;
	@Inject
	DebitoAutomaticoRepository debitoAutomaticoRepository;
	@Inject
	DetalleTipoPagoMenu detalleTipoPagoMenu;
}
