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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.swing.JOptionPane;
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
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;

import com.pacinetes.dom.adjunto.Adjunto;
import com.pacinetes.dom.adjunto.AdjuntoRepository;
import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.cliente.ClienteRepository;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoMenu;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoRepository;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.localidad.LocalidadRepository;
import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.ocupacion.Ocupacion;
import com.pacinetes.dom.ocupacion.OcupacionRepository;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;
import com.pacinetes.dom.polizaart.PolizaART;
import com.pacinetes.dom.polizaautomotor.PolizaAutomotor;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;
import com.pacinetes.dom.tipotitular.TipoTitular;
import com.pacinetes.dom.tipotitular.TipoTitularRepository;
import com.pacinetes.dom.tipovivienda.TipoVivienda;
import com.pacinetes.dom.tipovivienda.TipoViviendaRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Polizas"
)
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
@Discriminator(value="RiesgoCombinadosFamiliares")
public class PolizaCombinadoFamiliar extends Poliza {

	
	 //region > title
   public TranslatableString title() {
       return TranslatableString.tr("{name}", "name","Poliza Combinado Familiar N°: " + getPolizaNumero());
   }
   //endregion

	// Constructor
	public PolizaCombinadoFamiliar(String polizaNumero, Persona polizaCliente, Compania polizaCompania,
			String riesgoCombinadosFamiliaresDomicilio, Localidad riesgoCombinadosFamiliaresLocalidad, Ocupacion riesgoCombinadosFamiliaresOcupacion,
			TipoVivienda riesgoCombinadosFamiliaresTipoVivienda, TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			TipoPago polizaTipoDePago, DetalleTipoPago polizaPago, 
			double polizaImporteTotal, List<Adjunto> riesgoAutomotorListaAdjunto) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoAutomotorAdjunto(riesgoAutomotorListaAdjunto);
		setPolizaEstado(Estado.previgente);
		polizaEstado.actualizarEstado(this);
	}
	
	public PolizaCombinadoFamiliar(
			String polizaNumero,
			Persona polizaCliente,
			Compania polizaCompania,
			String riesgoCombinadosFamiliaresDomicilio,
			Localidad riesgoCombinadosFamiliaresLocalidad, 
			Ocupacion riesgoCombinadosFamiliaresOcupacion,
			TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			Date polizaFechaEmision,Date polizaFechaVigencia,
			Date polizaFechaVencimiento,
			TipoPago polizaTipoDePago,
			DetalleTipoPago polizaPago,
			double polizaImporteTotal,
			List<Adjunto> riesgoAutomotorListaAdjunto,
			Poliza riesgoCombinadoFamiliar) {
		setPolizaNumero(polizaNumero);
		setPolizasCliente(polizaCliente);
		setPolizasCompania(polizaCompania);
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		setRiesgoCombinadosFamiliaresOcupacion(riesgoCombinadosFamiliaresOcupacion);
		setRiesgoCombinadosFamiliaresTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
		setRiesgoCombinadosFamiliaresTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
		setPolizaFechaEmision(polizaFechaEmision);
		setPolizaFechaVigencia(polizaFechaVigencia);
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		setPolizaTipoDePago(polizaTipoDePago);
		setPolizaPago(polizaPago);
		setPolizaImporteTotal(polizaImporteTotal);
		setRiesgoAutomotorAdjunto(riesgoAutomotorListaAdjunto);
		setPolizaEstado(Estado.previgente);
		riesgoCombinadoFamiliar.setPolizaRenovacion(this);
		polizaEstado.actualizarEstado(this);
	}
	
	//adjunto
	@Join
	@Column(allowsNull="false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Adjunto")
	private List<Adjunto> riesgoAutomotorAdjunto; 
	
	public List<Adjunto> getRiesgoAutomotorAdjunto() {
		return riesgoAutomotorAdjunto;
	}

	public void setRiesgoAutomotorAdjunto(List<Adjunto> riesgoAutomotorAdjunto) {
		this.riesgoAutomotorAdjunto = riesgoAutomotorAdjunto;
	}	
	
	//Domicilio
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Domicilio")
   @Column(allowsNull = "false")
	private String riesgoCombinadosFamiliaresDomicilio; 
	
	public String getRiesgoCombinadosFamiliaresDomicilio() {
		return riesgoCombinadosFamiliaresDomicilio;
	}

	public void setRiesgoCombinadosFamiliaresDomicilio(String riesgoCombinadosFamiliaresDomicilio) {
		this.riesgoCombinadosFamiliaresDomicilio = riesgoCombinadosFamiliaresDomicilio;
	}
	
	//Localidad
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Localidad")
	@Column(allowsNull = "false", name="localidadId")
	private Localidad riesgoCombinadosFamiliaresLocalidad;

	public Localidad getRiesgoCombinadosFamiliaresLocalidad() {
		return riesgoCombinadosFamiliaresLocalidad;
	}

	public void setRiesgoCombinadosFamiliaresLocalidad(Localidad riesgoCombinadosFamiliaresLocalidad) {
		this.riesgoCombinadosFamiliaresLocalidad = riesgoCombinadosFamiliaresLocalidad;
	}

	//Ocupación
	@Column(name="ocupacionId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Ocupación")
	private Ocupacion riesgoCombinadosFamiliaresOcupacion; 
	
	public Ocupacion getRiesgoCombinadosFamiliaresOcupacion() {
		return riesgoCombinadosFamiliaresOcupacion;
	}

	public void setRiesgoCombinadosFamiliaresOcupacion(Ocupacion riesgoCombinadosFamiliaresOcupacion) {
		this.riesgoCombinadosFamiliaresOcupacion = riesgoCombinadosFamiliaresOcupacion;
	}

	//Tipo Vivienda
	@Column(name="tipoViviendaId")
   @Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de vivienda")
	private TipoVivienda riesgoCombinadosFamiliaresTipoVivienda; 
	
	public TipoVivienda getRiesgoCombinadosFamiliaresTipoVivienda() {
		return riesgoCombinadosFamiliaresTipoVivienda;
	}

	public void setRiesgoCombinadosFamiliaresTipoVivienda(TipoVivienda riesgoCombinadosFamiliaresTipoVivienda) {
		this.riesgoCombinadosFamiliaresTipoVivienda = riesgoCombinadosFamiliaresTipoVivienda;
	}


	//Tipo Titular
	@Column(name="tipoTitularId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named="Tipo de Titular")
	private TipoTitular riesgoCombinadosFamiliaresTipoTitular; 

	public TipoTitular getRiesgoCombinadosFamiliaresTipoTitular() {
		return riesgoCombinadosFamiliaresTipoTitular;
	}

	public void setRiesgoCombinadosFamiliaresTipoTitular(TipoTitular riesgoCombinadosFamiliaresTipoTitular) {
		this.riesgoCombinadosFamiliaresTipoTitular = riesgoCombinadosFamiliaresTipoTitular;
	}

	//region > delete (action)
   @Action(
           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
   )
   
   //Actualizar PolizaNumero
	public PolizaCombinadoFamiliar actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
		setPolizaNumero(polizaNumero);
		return this;
	}

	public String default0ActualizarPolizaNumero(){
		return getPolizaNumero();
	}
   
	//Actualizar Poliza Cliente
   public PolizaCombinadoFamiliar actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Persona polizaCliente) {
       setPolizasCliente(polizaCliente);
       return this;
   }
   
   public List<Persona> choices0ActualizarPolizaCliente(){
   	return personaRepository.listarActivos();
   }
     
   public Persona default0ActualizarPolizaCliente() {
   	return getPolizaCliente();
   }
   
   //Actualizar polizaCompania
   public PolizaCombinadoFamiliar actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
       actualizarPolizaCompania(polizaCompania);
       return this;
   }
   
   public List<Compania> choices0ActualizarPolizaCompania(){
   	return companiaRepository.listarActivos();
   }
     
   public Compania default0ActualizarPolizaCompania() {
   	return getPolizaCompania();
   }    
   
   //Actualizar poliza Domicilio
	public PolizaCombinadoFamiliar actualizarPolizaDomicilio(@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio){
		setRiesgoCombinadosFamiliaresDomicilio(riesgoCombinadosFamiliaresDomicilio);
		return this;
	}

	public String default0ActualizarPolizaDomicilio() {
		return getRiesgoCombinadosFamiliaresDomicilio();
	}

	// Actualizar poliza Localidad
	public PolizaCombinadoFamiliar actualizarPolizaLocalidad(@ParameterLayout(named = "Localidad") final Localidad riesgoCombinadosFamiliaresLocalidad) {
		setRiesgoCombinadosFamiliaresLocalidad(riesgoCombinadosFamiliaresLocalidad);
		return this;
	}

	public Localidad default0ActualizarPolizaLocalidad() {
		return getRiesgoCombinadosFamiliaresLocalidad();
	}

	// Actualizar poliza Ocupacion
	   public PolizaCombinadoFamiliar actualizarPolizaOcupacion(@ParameterLayout(named="Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion) {
		   actualizarPolizaOcupacion(riesgoCombinadosFamiliaresOcupacion);
	       return this;
	   }
	   
	   public List<Ocupacion> choices0ActualizarPolizaOcupacion(){
	   	return ocupacionesRepository.listarActivos();
	   }
	     
	   public Ocupacion default0ActualizarPolizaOcupacion() {
	   	return getRiesgoCombinadosFamiliaresOcupacion();
	   }   
	   
	
   //Actualizar poliza Tipo Vivienda
   public PolizaCombinadoFamiliar actualizarPolizaTipoVivienda(@ParameterLayout(named="Tipo Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda) {
	   actualizarPolizaTipoVivienda(riesgoCombinadosFamiliaresTipoVivienda);
       return this;
   }
   
   public List<TipoVivienda> choices0ActualizarPolizaTipoVivienda(){
   	return tiposViviendaRepository.listarActivos();
   }
     
   public TipoVivienda default0ActualizarPolizaTipoVivienda() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
   
   //Actualizar poliza Tipo Titular
   public PolizaCombinadoFamiliar actualizarPolizaTipoTitular(@ParameterLayout(named="Tipo Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular) {
	   actualizarPolizaTipoTitular(riesgoCombinadosFamiliaresTipoTitular);
       return this;
   }
   
   public List<TipoTitular> choices0ActualizarPolizaTipoTitular(){
   	return tipoTitularesRepository.listarActivos();
   }

   public TipoTitular default0ActualizarPolizaTipoTitular() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  

   //Actualizar polizaFechaEmision
	public PolizaCombinadoFamiliar actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
		setPolizaFechaEmision(polizaFechaEmision);
		return this;
	}

	public Date default0ActualizarPolizaFechaEmision(){
		return getPolizaFechaEmision();
	}
	
   //Actualizar polizaFechaVigencia
	public PolizaCombinadoFamiliar actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
		setPolizaFechaVigencia(polizaFechaVigencia);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVigencia(){
		return getPolizaFechaVigencia();
	}
	
	public String validateActualizarPolizaFechaVigencia(final Date polizaFechaVigencia) {

		if (polizaFechaVigencia.after(this.getPolizaFechaVencimiento())) {
			return "La fecha de vigencia es mayor a la de vencimiento";
		}
		return "";
	}
	
   //polizaFechaVencimiento
	public PolizaCombinadoFamiliar actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
		setPolizaFechaVencimiento(polizaFechaVencimiento);
		polizaEstado.actualizarEstado(this);
		return this;
	}

	public Date default0ActualizarPolizaFechaVencimiento(){
		return getPolizaFechaVencimiento();
	}
	
	public String validateActualizarPolizaFechaVencimiento(final Date polizaFechaVencimiento){
		if (this.getPolizaFechaVigencia().after(polizaFechaVencimiento)){
			return "La fecha de vencimiento es menor a la de vigencia";
		}
		return "";
	}
	    
    //polizaPago
    public PolizaCombinadoFamiliar actualizarPolizaPago(
    		@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
			@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago) {
        setPolizaTipoDePago(polizaTipoDePago);
    	setPolizaPago(polizaPago);
        return this;
    }
    
    public List<DetalleTipoPago> choices1ActualizarPolizaPago(			
 			final TipoPago polizaTipoDePago,
 			final DetalleTipoPago polizaPago) {
 		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
    }
    
    public TipoPago default0ActualizarPolizaPago() {
    	return getPolizaTipoDePago();
    }
      
    public DetalleTipoPago default1ActualizarPolizaPago() {
    	return getPolizaPago();
    }
   //polizaFechaBaja
	public PolizaCombinadoFamiliar actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
		setPolizaFechaBaja(polizaFechaBaja);
		return this;
	}

	public Date default0ActualizarPolizaFechaBaja(){
		return getPolizaFechaBaja();
	}    
   
   //polizaMotivoBaja
	public PolizaCombinadoFamiliar actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		setPolizaMotivoBaja(polizaMotivoBaja);
		return this;
	}

	public String default0ActualizarPolizaMotivoBaja(){
		return getPolizaMotivoBaja();
	}    
   
   //polizaImporteTotal
	public PolizaCombinadoFamiliar actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
		setPolizaImporteTotal(polizaImporteTotal);
		return this;
	}

	public double default0ActualizarPolizaImporteTotal(){
		return getPolizaImporteTotal();
	}    
	
   //polizaRenovacion
	@ActionLayout(named="Actualizar Renovacion")
   public PolizaCombinadoFamiliar actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
       setPolizaRenovacion(polizaRenovacion);
       polizaEstado.actualizarEstado(this);
       return this;
   }
   
   public List<Poliza> choices0ActualizarPolizaRenovacion(){
   	return polizasRepository.listar();
   }
     
   public Poliza default0ActualizarPolizaRenovacion() {
   	return getPolizaRenovacion();
   }
   
   public PolizaCombinadoFamiliar borrarPolizaRenovacion() {
		setPolizaRenovacion(null);
		polizaEstado.actualizarEstado(this);
   	return this;
   }
   
   //endregion

   //acciones

	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
	@ActionLayout(named="Emitir Renovacion")
	public PolizaCombinadoFamiliar renovacion(
			/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
			/*1*/	            @ParameterLayout(named="Cliente") final Persona polizaCliente,
			/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
			/*3*/	    		@ParameterLayout(named="Domicilio") final String riesgoCombinadosFamiliaresDomicilio,
			/*4*/				@ParameterLayout(named="Localidad") final Localidad riesgoCombinadoFamiliarLocalidad,
			/*5*/	            @ParameterLayout(named="Ocupación") final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			/*6*/	            @ParameterLayout(named="Tipo de Vivienda") final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			/*7*/	            @ParameterLayout(named="Tipo de Titular") final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			/*8*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
			/*9*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
			/*10*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
								@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
								@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
			/*12*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal){
    	List<Adjunto> riesgoAutomotorListaAdjunto = new ArrayList<>();
    	riesgoAutomotorListaAdjunto = this.getRiesgoAutomotorAdjunto();
    	Mail.enviarMailPoliza(polizaCliente);
		return riesgoCombinadosFamiliaresRepository.renovacion(
    		polizaNumero,
       		polizaCliente,
       		polizaCompania,
       		riesgoCombinadosFamiliaresDomicilio,
       		riesgoCombinadoFamiliarLocalidad,
       		riesgoCombinadosFamiliaresOcupacion,
       		riesgoCombinadosFamiliaresTipoVivienda,
       		riesgoCombinadosFamiliaresTipoTitular,
       		polizaFechaEmision,
       		polizaFechaVigencia, 
       		polizaFechaVencimiento,
       		polizaTipoDePago,
       		polizaPago,
       		polizaImporteTotal,
       		riesgoAutomotorListaAdjunto, this);
	}
	
   public List<Persona> choices1Renovacion(){
   	return personaRepository.listarActivos();
   }
   
   public List<Compania> choices2Renovacion(){
   	return companiaRepository.listarActivos();
   }	   
   
   public List<Localidad> choices4Renovacion(){
	   	return localidadesRepository.listarActivos();
	   }	    
   
   public List<Ocupacion> choices5Renovacion(){
   	return ocupacionesRepository.listarActivos();
   }
   public List<TipoVivienda> choices6Renovacion(){
	   	return tiposViviendaRepository.listarActivos();
   }
	     
   public List<TipoTitular> choices7Renovacion(){
	   	return tipoTitularesRepository.listarActivos();
	   }
   
   public List<DetalleTipoPago> choices12Renovacion(			
			final String polizaNumero,
			final Persona polizaCliente,
			final Compania polizaCompania,
			final String riesgoCombinadosFamiliaresDomicilio,
			final Localidad riesgoCombinadoFamiliarLocalidad,
			final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
			final Date polizaFechaEmision,
			final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago,
			final double polizaImporteTotal) {
		return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
   }
   
   public Persona default1Renovacion() {
   	return getPolizaCliente();
   }
   public String default3Renovacion() {
	   	return getRiesgoCombinadosFamiliaresDomicilio();
	   }

   public Compania default2Renovacion(){
   	return getPolizaCompania();
   }
   
   public Localidad default4Renovacion(){
	   	return getRiesgoCombinadosFamiliaresLocalidad();
	   }
   
   public Ocupacion default5Renovacion() {
   	return getRiesgoCombinadosFamiliaresOcupacion();
   }   
   public TipoVivienda default6Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoVivienda();
   }   
	   
   public TipoTitular default7Renovacion() {
   	return getRiesgoCombinadosFamiliaresTipoTitular();
   }  

   public TipoPago default11Renovacion(){
	   	return getPolizaTipoDePago();
	   }
  
  public DetalleTipoPago default12Renovacion(){
  	return getPolizaPago();
  }
  
	@ActionLayout(named="Crear y Agregar Adjunto")
    public PolizaCombinadoFamiliar crearAdjunto(
    	@ParameterLayout(named = "Descripcion") final String riesgoAutomotorAdjuntoDescripcion,
		@ParameterLayout(named = "Imagen") final Blob riesgoAutomorAdjunto) {
    		this.getRiesgoAutomotorAdjunto().add(adjuntoRepository.crear(riesgoAutomotorAdjuntoDescripcion, riesgoAutomorAdjunto));
    		this.setRiesgoAutomotorAdjunto(this.getRiesgoAutomotorAdjunto());
    		return this;
	}
	
	@ActionLayout(named="Agregar adjunto")
	public PolizaCombinadoFamiliar agregarAdjunto(
			@ParameterLayout(named="Adjunto") final Adjunto riesgoAutomotorAdjunto){
		if (this.getRiesgoAutomotorAdjunto().contains(riesgoAutomotorAdjunto)) {
			messageService.warnUser("ERROR: La imagen ya está agregada en la lista");
		} else {
			this.getRiesgoAutomotorAdjunto().add(riesgoAutomotorAdjunto);
			this.setRiesgoAutomotorAdjunto(this.getRiesgoAutomotorAdjunto());
		}
		return this;
	}
	
	public List<Adjunto> choices0AgregarAdjunto() {
		return adjuntoRepository.listarActivos();
	}
    
    public PolizaCombinadoFamiliar quitarAdjunto(@ParameterLayout(named="Imagen") Adjunto adjunto) {
    	Iterator<Adjunto> it = getRiesgoAutomotorAdjunto().iterator();
    	while (it.hasNext()) {
    		Adjunto lista = it.next();
    		if (lista.equals(adjunto))
    			it.remove();
    	}
    	return this;
    }
    
    public List<Adjunto> choices0QuitarAdjunto(){
    	return getRiesgoAutomotorAdjunto();
    }
   
   //region > toString, compareTo
   @Override
   public String toString() {
       return ObjectContracts.toString(this, "polizaNumero");
   }

   //endregion

   //region > injected dependencies

   @Inject
   RepositoryService repositoryService;

   @Inject
   TitleService titleService;

   @Inject
   MessageService messageService;
   
   @Inject
   PersonaRepository personaRepository;

   @Inject
   TipoViviendaRepository tiposViviendaRepository;
   
   @Inject
   TipoTitularRepository tipoTitularesRepository;

   @Inject
   OcupacionRepository ocupacionesRepository;
   
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
   PolizaCombinadoFamiliarRepository riesgoCombinadosFamiliaresRepository;
   
   @Inject
   LocalidadRepository localidadesRepository;
   
   @Inject
   AdjuntoRepository adjuntoRepository;
   
   //endregion

	
}
