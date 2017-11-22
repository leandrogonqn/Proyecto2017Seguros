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
package com.pacinetes.dom.polizacombinadofamiliar;

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
import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.localidad.LocalidadRepository;
import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.ocupacion.Ocupacion;
import com.pacinetes.dom.ocupacion.OcupacionRepository;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;
import com.pacinetes.dom.tipotitular.TipoTitular;
import com.pacinetes.dom.tipotitular.TipoTitularRepository;
import com.pacinetes.dom.tipovivienda.TipoVivienda;
import com.pacinetes.dom.tipovivienda.TipoViviendaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaCombinadoFamiliar.class)
@DomainServiceLayout(named = "Polizas Crear", menuOrder = "19.2")
public class PolizaCombinadoFamiliarMenu {

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Crear Poliza Combinado Familiar")
	@MemberOrder(sequence = "20")
	public PolizaCombinadoFamiliar crear(@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Persona polizaCliente,
			@ParameterLayout(named = "Compañia") final Compania polizaCompania,
			@ParameterLayout(named = "Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
			@ParameterLayout(named = "Localidad") final Localidad riesgoCombinadoFamiliarLocalidad,
			@ParameterLayout(named = "Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			@ParameterLayout(named = "Tipo de Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			@ParameterLayout(named = "Tipo de Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago") @Parameter(optionality = Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal) {
		Mail.enviarMailPoliza(polizaCliente);
		return polizasRepository.crear(polizaNumero, polizaCliente, polizaCompania, riesgoCombinadosFamiliaresDomicilio,
				riesgoCombinadoFamiliarLocalidad, riesgoCombinadosFamiliaresOcupacion,
				riesgoCombinadosFamiliaresTipoVivienda, riesgoCombinadosFamiliaresTipoTitular, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal);
	}

	public List<Persona> choices1Crear() {
		return personaRepository.listarActivos();
	}

	public List<Localidad> choices4Crear() {
		return localidadesRepository.listarActivos();
	}

	public List<Ocupacion> choices5Crear() {
		return ocupacionesRepository.listarActivos();
	}

	public List<Compania> choices2Crear() {
		return companiaRepository.listarActivos();
	}

	public List<TipoTitular> choices7Crear() {
		return tipoTitularesRepository.listarActivos();
	}

	public List<TipoVivienda> choices6Crear() {
		return tiposViviendaRepository.listarActivos();
	}

	@SuppressWarnings("unchecked")
	public List<DetalleTipoPago> choices12Crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final String riesgoCombinadosFamiliaresDomicilio,
			final Localidad riesgoCombinadoFamiliarLocalidad, final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitular riesgoCombinadosFamiliaresTipoTitular, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	public String validateCrear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final String riesgoCombinadosFamiliaresDomicilio, final Localidad riesgoCombinadoFamiliarLocalidad,
			final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitular riesgoCombinadosFamiliaresTipoTitular, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal) {
		if (polizaFechaVigencia.after(polizaFechaVencimiento)) {
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		return "";
	}

	@javax.inject.Inject
	PolizaCombinadoFamiliarRepository polizasRepository;
	@javax.inject.Inject
	PersonaRepository personaRepository;
	@Inject
	TipoViviendaRepository tiposViviendaRepository;

	@Inject
	TipoTitularRepository tipoTitularesRepository;

	@Inject
	OcupacionRepository ocupacionesRepository;

	@Inject
	DetalleTipoPagoMenu detalleTipoPagoMenu;
	@Inject
	CompaniaRepository companiaRepository;
	@Inject
	TipoDeCoberturaRepository tiposDeCoberturasRepository;
	@Inject
	LocalidadRepository localidadesRepository;
}
