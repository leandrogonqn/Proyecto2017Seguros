/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.pacinetes.dom.polizaautomotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.message.MessageService;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.debitoautomatico.DebitoAutomaticoRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoMenu;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.tarjetadecredito.TarjetaDeCreditoRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCobertura;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;
import com.pacinetes.dom.vehiculo.Vehiculo;
import com.pacinetes.dom.vehiculo.VehiculoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaAutomotor.class)
@DomainServiceLayout(named = "Polizas Crear", menuOrder = "19.1")
public class PolizaAutomotorMenu {

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Crear Poliza Auto")
	@MemberOrder(sequence = "10.1")
	public PolizaAutomotor crear(@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Persona polizaCliente,
			@ParameterLayout(named = "Compañia") final Compania polizaCompania,
			@ParameterLayout(named = "Vehiculo") final Vehiculo riesgoAutomotorVehiculo,
			@ParameterLayout(named = "Tipo de Cobertura") final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago") @Parameter(optionality = Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal) {
		List<Vehiculo> riesgoAutomotorListaVehiculos = new ArrayList<>();
		riesgoAutomotorListaVehiculos.add(riesgoAutomotorVehiculo);
		Mail.enviarMailPoliza(polizaCliente);
		return riesgoAutomotorRepository.crear(polizaNumero, polizaCliente, polizaCompania,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal);
	}

	public List<Persona> choices1Crear() {
		return personaRepository.listarActivos();
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

	@SuppressWarnings("unchecked")
	public List<DetalleTipoPago> choices9Crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final Vehiculo riesgoAutomotorVehiculo,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	@SuppressWarnings("deprecation")
	public String validateCrear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Vehiculo riesgoAutomotorVehiculo, final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal) {
		polizaFechaVigencia.setHours(12);
		polizaFechaVigencia.setSeconds(1);
		polizaFechaVencimiento.setHours(12);
		if (polizaFechaVigencia.after(polizaFechaVencimiento)) {
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		if (riesgoAutomotorRepository.validar(riesgoAutomotorVehiculo, polizaFechaVigencia) == false) {
			return "ERROR: vehiculo existente en otra poliza vigente";
		}
		return "";
	}

	@javax.inject.Inject
	PersonaRepository personaRepository;
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
