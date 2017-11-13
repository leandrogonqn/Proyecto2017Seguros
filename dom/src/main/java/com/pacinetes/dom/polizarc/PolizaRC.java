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
package com.pacinetes.dom.polizarc;

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
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
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
import com.pacinetes.dom.reportes.PolizaRCReporte;
import com.pacinetes.dom.reportes.ReporteRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCoberturaRepository;

import net.sf.jasperreports.engine.JRException;

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
@Discriminator(value="RiesgoCombinadosRiesgosRC")
public class PolizaRC extends Poliza {
	 //region > title
	   public TranslatableString title() {
	       return TranslatableString.tr("{name}", "name","Poliza RC N°: " + getPolizaNumero());
	   }
	   //endregion

		// Constructor
		public PolizaRC(String polizaNumero, Persona polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, float riesgoRCMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoRCMonto(riesgoRCMonto);
			setPolizaEstado(Estado.previgente);
			polizaEstado.actualizarEstado(this);
		}
		
		public PolizaRC(
				String polizaNumero, Persona polizaCliente, Compania polizaCompania,
				Date polizaFechaEmision, Date polizaFechaVigencia,
				Date polizaFechaVencimiento, TipoPago polizaTipoDePago, DetalleTipoPago polizaPago,
				double polizaImporteTotal, 
				Poliza riesgoRC,
				float riesgoRCMonto) {
			setPolizaNumero(polizaNumero);
			setPolizasCliente(polizaCliente);
			setPolizasCompania(polizaCompania);
			setPolizaFechaEmision(polizaFechaEmision);
			setPolizaFechaVigencia(polizaFechaVigencia);
			setPolizaFechaVencimiento(polizaFechaVencimiento);
			setPolizaTipoDePago(polizaTipoDePago);
			setPolizaPago(polizaPago);
			setPolizaImporteTotal(polizaImporteTotal);
			setRiesgoRCMonto(riesgoRCMonto);
			setPolizaEstado(Estado.previgente);
			riesgoRC.setPolizaRenovacion(this);
			polizaEstado.actualizarEstado(this);
		}
		
		//Monto
	   @Property(editing = Editing.DISABLED)
		@PropertyLayout(named="Monto")
	   @Column(allowsNull = "false")
		private float riesgoRCMonto; 
		
	public float getRiesgoRCMonto() {
		return riesgoRCMonto;
		}

	public void setRiesgoRCMonto(float riesgoRCMonto) {
		this.riesgoRCMonto = riesgoRCMonto;
		}

	//region > delete (action)
	   @Action(
	           semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
	   )
	   
	   //Actualizar PolizaNumero
		public PolizaRC actualizarPolizaNumero(@ParameterLayout(named="Numero") final String polizaNumero){
			setPolizaNumero(polizaNumero);
			return this;
		}

		public String default0ActualizarPolizaNumero(){
			return getPolizaNumero();
		}
	   
		//Actualizar Poliza Cliente
	   public PolizaRC actualizarPolizaCliente(@ParameterLayout(named="Cliente") final Persona polizaCliente) {
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
	   public PolizaRC actualizarPolizaCompania(@ParameterLayout(named="Compañia") final Compania polizaCompania) {
		   setPolizasCompania(polizaCompania);
	       return this;
	   }
	   
	   public List<Compania> choices0ActualizarPolizaCompania(){
	   	return companiaRepository.listarActivos();
	   }
	     
	   public Compania default0ActualizarPolizaCompania() {
	   	return getPolizaCompania();
	   }    
	   

	   //Actualizar polizaFechaEmision
		public PolizaRC actualizarPolizaFechaEmision(@ParameterLayout(named="Fecha de Emision") final Date polizaFechaEmision){
			setPolizaFechaEmision(polizaFechaEmision);
			return this;
		}

		public Date default0ActualizarPolizaFechaEmision(){
			return getPolizaFechaEmision();
		}
		
	   //Actualizar polizaFechaVigencia
		public PolizaRC actualizarPolizaFechaVigencia(@ParameterLayout(named="Fecha de Vigencia") final Date polizaFechaVigencia){
			setPolizaFechaVigencia(polizaFechaVigencia);
			polizaEstado.actualizarEstado(this);
			JOptionPane.showMessageDialog(null, getPolizaEstado().toString());
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
		public PolizaRC actualizarPolizaFechaVencimiento(@ParameterLayout(named="Fecha de Vencimiento") final Date polizaFechaVencimiento){
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
	    public PolizaRC actualizarPolizaPago(
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
		public PolizaRC actualizarPolizaFechaBaja(@ParameterLayout(named="Fecha de Baja") final Date polizaFechaBaja){
			setPolizaFechaBaja(polizaFechaBaja);
			return this;
		}

		public Date default0ActualizarPolizaFechaBaja(){
			return getPolizaFechaBaja();
		}    
	   
	   //polizaMotivoBaja
		public PolizaRC actualizarPolizaMotivoBaja(@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
			setPolizaMotivoBaja(polizaMotivoBaja);
			return this;
		}

		public String default0ActualizarPolizaMotivoBaja(){
			return getPolizaMotivoBaja();
		}    
	   
	   //polizaImporteTotal
		public PolizaRC actualizarPolizaImporteTotal(@ParameterLayout(named="Importe Total") final double polizaImporteTotal){
			setPolizaImporteTotal(polizaImporteTotal);
			return this;
		}

		public double default0ActualizarPolizaImporteTotal(){
			return getPolizaImporteTotal();
		}    
		
		// riesgoARTMonto
		public PolizaRC actualizarRiesgoRCMonto(
				@ParameterLayout(named = "Monto asegurado") final float riesgoRCMonto) {
			setRiesgoRCMonto(riesgoRCMonto);
			return this;
		}

		public float default0ActualizarRiesgoRCMonto() {
			return getRiesgoRCMonto();
		}

	   //polizaRenovacion
		@ActionLayout(named="Actualizar Renovacion")
	   public PolizaRC actualizarPolizaRenovacion(@ParameterLayout(named="Renovacion") final Poliza polizaRenovacion) {
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
	   
	   public PolizaRC borrarPolizaRenovacion() {
			setPolizaRenovacion(null);
			polizaEstado.actualizarEstado(this);
	   	return this;
	   }
	   
	   //endregion

	   //acciones

		@Action(invokeOn=InvokeOn.OBJECT_ONLY)
		@ActionLayout(named="Emitir Renovacion")
		public PolizaRC renovacion(
				@ParameterLayout(named="Número") final String polizaNumero,
				@ParameterLayout(named="Cliente") final Persona polizaCliente,
				@ParameterLayout(named="Compañia") final Compania polizaCompania,
				@ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
				@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
				@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
				@ParameterLayout(named="Monto") final float riesgoRCMonto){
			Mail.enviarMailPoliza(polizaCliente);
	       return riesgosRCRepository.renovacion(
	    		polizaNumero,
	       		polizaCliente,
	       		polizaCompania,
	       		polizaFechaEmision,
	       		polizaFechaVigencia, 
	       		polizaFechaVencimiento,
	       		polizaTipoDePago,
	       		polizaPago,
	       		polizaImporteTotal,
	       		riesgoRCMonto,this);
		}
		
	   public List<Persona> choices1Renovacion(){
	   	return personaRepository.listarActivos();
	   }
	   
	   public List<Compania> choices2Renovacion(){
	   	return companiaRepository.listarActivos();
	   }	    
	   
	   public List<DetalleTipoPago> choices7Renovacion(			
				final String polizaNumero,
				final Persona polizaCliente,
				final Compania polizaCompania,
				final Date polizaFechaEmision,
				final Date polizaFechaVigencia,
				final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago,
				final DetalleTipoPago polizaPago,
				final double polizaImporteTotal,
				final float riesgoRCMonto) {
			return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	   }
	   
	   public Persona default1Renovacion() {
	   	return getPolizaCliente();
	   }

	   public TipoPago default6Renovacion(){
		   	return getPolizaTipoDePago();
		   }
	   
	   public DetalleTipoPago default7Renovacion(){
	   	return getPolizaPago();
	   }
	   
	   public Blob imprimirPoliza() throws JRException, IOException{
			
			List<Object> objectsReport = new ArrayList<Object>();
			
			PolizaRCReporte polizaRCReporte = new PolizaRCReporte();
			polizaRCReporte.setPolizaCliente(getPolizaCliente().toString());
			polizaRCReporte.setPolizaNumero(getPolizaNumero());
			polizaRCReporte.setPolizaCompania(getPolizaCompania().getCompaniaNombre());
			polizaRCReporte.setPolizaFechaEmision(getPolizaFechaEmision());
			polizaRCReporte.setPolizaFechaVigencia(getPolizaFechaVigencia());
			polizaRCReporte.setPolizaFechaVencimiento(getPolizaFechaVencimiento());
			polizaRCReporte.setRiesgoRCMonto(getRiesgoRCMonto());
			polizaRCReporte.setPolizaImporteTotal(getPolizaImporteTotal());
			polizaRCReporte.setPolizaEstado(getPolizaEstado().toString());
			
			objectsReport.add(polizaRCReporte);
			String jrxml = "PolizaRC.jrxml";
			String nombreArchivo = "PolizaRC_"+getPolizaCliente().toString().replaceAll("\\s","_")+"_"+getPolizaNumero();
			
			return reporteRepository.imprimirReporteIndividual(objectsReport,jrxml, nombreArchivo);
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
	   PolizaRCRepository riesgosRCRepository;
	   
	   @Inject
	   ReporteRepository reporteRepository;
	   
	   //endregion

}
