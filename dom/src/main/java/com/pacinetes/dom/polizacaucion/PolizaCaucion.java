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
package com.pacinetes.dom.polizacaucion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoMenu;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoRepository;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;
import com.pacinetes.dom.reportes.PolizaCaucionReporte;
import com.pacinetes.dom.reportes.ReporteRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;
import net.sf.jasperreports.engine.JRException;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Polizas")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value = "RiesgoCombinadosRiesgosCaucion")
public class PolizaCaucion extends Poliza {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", "Poliza Caucion N°: " + getPolizaNumero());
	}
	// endregion

	// Constructor
	@SuppressWarnings("deprecation")
	public PolizaCaucion(String polizaNumero, Persona polizaCliente, Compania polizaCompania, Date polizaFechaEmision,
			Date polizaFechaVigencia, Date polizaFechaVencimiento, TipoPago polizaTipoDePago,
			DetalleTipoPago polizaPago, double polizaImporteTotal, float riesgoCaucionMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		polizaFechaVigencia.setHours(12);
		polizaFechaVigencia.setSeconds(1);
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaFechaVencimiento.setHours(12);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoCaucionMonto(riesgoCaucionMonto);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}

	@SuppressWarnings("deprecation")
	public PolizaCaucion(String polizaNumero, Persona polizaCliente, Compania polizaCompania, Date polizaFechaEmision,
			Date polizaFechaVigencia, Date polizaFechaVencimiento, TipoPago polizaTipoDePago,
			DetalleTipoPago polizaPago, double polizaImporteTotal, Poliza riesgoCaucion, float riesgoCaucionMonto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setPolizaFechaEmision(polizaFechaEmision);
		polizaFechaVigencia.setHours(12);
		polizaFechaVigencia.setSeconds(1);
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaFechaVencimiento.setHours(12);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoCaucionMonto(riesgoCaucionMonto);
		setPolizaEstado(Estado.previgente);
		riesgoCaucion.setPolizaRenovacion(this);
		polizaEstado.actualizarEstado(this);
	}

	// Monto
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Monto")
	@Column(allowsNull = "false")
	private float riesgoCaucionMonto;

	public float getRiesgoCaucionMonto() {
		return riesgoCaucionMonto;
	}

	public void setRiesgoCaucionMonto(float riesgoCaucionMonto) {
		this.riesgoCaucionMonto = riesgoCaucionMonto;
	}

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)

	// Actualizar PolizaNumero
	public PolizaCaucion actualizarPolizaNumero(@ParameterLayout(named = "Numero") final String polizaNumero) {
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero() {
		return getPolizaNumero();
	}

	// Actualizar Poliza Cliente
	public PolizaCaucion actualizarPolizaCliente(@ParameterLayout(named = "Cliente") final Persona polizaCliente) {
		setPolizasCliente(polizaCliente);
		return this;
	}

	public List<Persona> choices0ActualizarPolizaCliente() {
		return personaRepository.listarActivos();
	}

	public Persona default0ActualizarPolizaCliente() {
		return getPolizaCliente();
	}

	// Actualizar polizaCompania
	public PolizaCaucion actualizarPolizaCompania(@ParameterLayout(named = "Compañia") final Compania polizaCompania) {
		setPolizasCompania(polizaCompania);
		return this;
	}

	public List<Compania> choices0ActualizarPolizaCompania() {
		return companiaRepository.listarActivos();
	}

	public Compania default0ActualizarPolizaCompania() {
		return getPolizaCompania();
	}

	// Actualizar polizaFechaEmision
	public PolizaCaucion actualizarPolizaFechaEmision(
			@ParameterLayout(named = "Fecha de Emision") final Date polizaFechaEmision) {
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision() {
		return getPolizaFechaEmision();
	}

	// Actualizar polizaFechaVigencia
	@SuppressWarnings("deprecation")
	public PolizaCaucion actualizarPolizaFechaVigencia(
			@ParameterLayout(named = "Fecha de Vigencia") final Date polizaFechaVigencia) {
		polizaFechaVigencia.setHours(12);
		polizaFechaVigencia.setSeconds(1);
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia() {
		return getPolizaFechaVigencia();
	}

	public String validateActualizarPolizaFechaVigencia(final Date polizaFechaVigencia) {
		if (polizaFechaVigencia.after(this.getPolizaFechaVencimiento())) {
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		return "";
	}

	// Actualizar riesgoCaucionMonto
	public PolizaCaucion actualizarRiesgoCaucionMonto(
			@ParameterLayout(named = "Monto Caucion") final float riesgoCaucionMonto) {
		setRiesgoCaucionMonto(riesgoCaucionMonto);
		return (this);
	}

	public float default0ActualizarRiesgoCaucionMonto() {
		return getRiesgoCaucionMonto();
	}

	// polizaFechaVencimiento
	@SuppressWarnings("deprecation")
	public PolizaCaucion actualizarPolizaFechaVencimiento(
			@ParameterLayout(named = "Fecha de Vencimiento") final Date polizaFechaVencimiento) {
		polizaFechaVencimiento.setHours(12);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento() {
		return getPolizaFechaVencimiento();
	}

	public String validateActualizarPolizaFechaVencimiento(final Date polizaFechaVencimiento) {
		if (this.getPolizaFechaVigencia().after(polizaFechaVencimiento)) {
			return "La fecha de vencimiento es menor a la de vigencia";
		}
		return "";
	}

	// polizaPago
	public PolizaCaucion actualizarPolizaPago(@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago") @Parameter(optionality = Optionality.OPTIONAL) final DetalleTipoPago polizaPago) {
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaPago(polizaPago);
		return this;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleTipoPago> choices1ActualizarPolizaPago(final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	public TipoPago default0ActualizarPolizaPago() {
		return getPolizaTipoDePago();
	}

	public DetalleTipoPago default1ActualizarPolizaPago() {
		return getPolizaPago();
	}

	// polizaFechaBaja
	public PolizaCaucion actualizarPolizaFechaBaja(
			@ParameterLayout(named = "Fecha de Baja") final Date polizaFechaBaja) {
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja() {
		return getPolizaFechaBaja();
	}

	// polizaMotivoBaja
	public PolizaCaucion actualizarPolizaMotivoBaja(
			@ParameterLayout(named = "Motivo de la Baja") final String polizaMotivoBaja) {
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja() {
		return getPolizaMotivoBaja();
	}

	// polizaImporteTotal
	public PolizaCaucion actualizarPolizaImporteTotal(
			@ParameterLayout(named = "Importe Total") final double polizaImporteTotal) {
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal() {
		return getPolizaImporteTotal();
	}

	// polizaRenovacion
	@ActionLayout(named = "Actualizar Renovacion")
	public PolizaCaucion actualizarPolizaRenovacion(
			@ParameterLayout(named = "Renovacion") final Poliza polizaRenovacion) {
		setPolizaRenovacion(polizaRenovacion);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public List<Poliza> choices0ActualizarPolizaRenovacion() {
		return polizasRepository.listar();
	}

	public Poliza default0ActualizarPolizaRenovacion() {
		return getPolizaRenovacion();
	}

	public PolizaCaucion borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
		return this;
	}
	// endregion

	// acciones
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Emitir Renovacion")
	public PolizaCaucion renovacion(@ParameterLayout(named = "Número") final String polizaNumero,
			@ParameterLayout(named = "Cliente") final Persona polizaCliente,
			@ParameterLayout(named = "Compañia") final Compania polizaCompania,
			@ParameterLayout(named = "Fecha Emision") final Date polizaFechaEmision,
			@ParameterLayout(named = "Fecha Vigencia") final Date polizaFechaVigencia,
			@ParameterLayout(named = "Fecha Vencimiento") final Date polizaFechaVencimiento,
			@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago") @Parameter(optionality = Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			@ParameterLayout(named = "Precio Total") final double polizaImporteTotal,
			@ParameterLayout(named = "Monto") final float riesgoCaucionMonto) {
		Mail.enviarMailPoliza(polizaCliente);
		return riesgosCaucionRepository.renovacion(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal,
				riesgoCaucionMonto, this);
	}

	public List<Persona> choices1Renovacion() {
		return personaRepository.listarActivos();
	}

	public List<Compania> choices2Renovacion() {
		return companiaRepository.listarActivos();
	}

	@SuppressWarnings("unchecked")
	public List<DetalleTipoPago> choices7Renovacion(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final Date polizaFechaEmision, final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, final float riesgoCaucionMonto) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	}

	public Persona default1Renovacion() {
		return getPolizaCliente();
	}

	public Compania default2Renovacion() {
		return getPolizaCompania();
	}

	public TipoPago default6Renovacion() {
		return getPolizaTipoDePago();
	}

	public DetalleTipoPago default7Renovacion() {
		return getPolizaPago();
	}

	public Blob imprimirPoliza() throws JRException, IOException {

		List<Object> objectsReport = new ArrayList<Object>();

		PolizaCaucionReporte polizaCaucionReporte = new PolizaCaucionReporte();
		polizaCaucionReporte.setPolizaCliente(getPolizaCliente().toString());
		polizaCaucionReporte.setPolizaNumero(getPolizaNumero());
		polizaCaucionReporte.setPolizaCompania(getPolizaCompania().getCompaniaNombre());
		polizaCaucionReporte.setPolizaFechaEmision(getPolizaFechaEmision());
		polizaCaucionReporte.setPolizaFechaVigencia(getPolizaFechaVigencia());
		polizaCaucionReporte.setPolizaFechaVencimiento(getPolizaFechaVencimiento());
		polizaCaucionReporte.setRiesgoCaucionMonto(getRiesgoCaucionMonto());
		polizaCaucionReporte.setPolizaImporteTotal(getPolizaImporteTotal());
		polizaCaucionReporte.setPolizaEstado(getPolizaEstado().toString());

		objectsReport.add(polizaCaucionReporte);
		String jrxml = "PolizaCaucion.jrxml";
		String nombreArchivo = "PolizaCaucion_" + getPolizaCliente().toString().replaceAll("\\s", "_") + "_"
				+ getPolizaNumero();

		return ReporteRepository.imprimirReporteIndividual(objectsReport, jrxml, nombreArchivo);
	}

	// region > toString, compareTo
	@Override
	public String toString() {
		return "Poliza Caucion Numero: " + getPolizaNumero();
	}

	// endregion

	// region > injected dependencies

	@Inject
	RepositoryService repositoryService;

	@Inject
	TitleService titleService;

	@Inject
	MessageService messageService;

	@Inject
	PersonaRepository personaRepository;

	@Inject
	DetalleTipoPagoRepository detalleTipoPagosRepository;

	@Inject
	DetalleTipoPagoMenu detalleTipoPagoMenu;

	@Inject
	CompaniaRepository companiaRepository;

	@Inject
	TipoDeCoberturaRepository tiposDeCoberturasRepository;

	@Inject
	PolizaRepository polizasRepository;

	@Inject
	PolizaCaucionRepository riesgosCaucionRepository;
	// endregion

}
