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
package com.pacinetes.dom.polizatro;

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
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoMenu;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaTRO.class)
@DomainServiceLayout(named = "Polizas Crear", menuOrder = "20")
public class PolizaTROMenu {

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Crear Poliza TRO")
	@MemberOrder(sequence = "120")
	public PolizaTRO crear(/* 0 */ @ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Persona polizaCliente,
			@ParameterLayout(named = "Compañia") final Compania polizaCompania,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago") @Parameter(optionality = Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal,
			@ParameterLayout(named = "Monto") final float riesgoTROMonto) {
		Mail.enviarMailPoliza(polizaCliente);
		return polizasRepository.crear(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal,
				riesgoTROMonto);
	}

	public List<Persona> choices1Crear() {
		return personaRepository.listarActivos();
	}

	public List<Compania> choices2Crear() {
		return companiaRepository.listarActivos();
	}

	@SuppressWarnings("unchecked")
	public List<DetalleTipoPago> choices7Crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final Date polizaFechaEmision, final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, final float riesgoTROMonto) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	public String validateCrear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal,
			final float riesgoTROMonto) {
		if (polizaFechaVigencia.after(polizaFechaVencimiento)) {
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		return "";
	}

	@javax.inject.Inject
	PolizaTRORepository polizasRepository;
	@javax.inject.Inject
	PersonaRepository personaRepository;
	@Inject
	DetalleTipoPagoMenu detalleTipoPagoMenu;
	@Inject
	CompaniaRepository companiaRepository;
	@Inject
	TipoDeCoberturaRepository tiposDeCoberturasRepository;
}
