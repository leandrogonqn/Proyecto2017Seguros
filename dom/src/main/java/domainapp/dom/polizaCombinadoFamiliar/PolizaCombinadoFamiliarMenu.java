package domainapp.dom.polizaCombinadoFamiliar;

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
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.adjunto.Adjunto;
import domainapp.dom.adjunto.AdjuntoRepository;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoMenu;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.ocupacion.Ocupacion;
import domainapp.dom.ocupacion.OcupacionRepository;
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoTitular.TipoTitularRepository;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tipoVivienda.TipoViviendaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;
import domainapp.dom.vehiculo.Vehiculo;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaCombinadoFamiliar.class
)
@DomainServiceLayout(
        named = "Polizas Crear",
        menuOrder = "19.2"
)
public class PolizaCombinadoFamiliarMenu {
	
	    @Action(invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Combinado Familiar")
	    @MemberOrder(sequence = "20")
	    public PolizaCombinadoFamiliar crear(
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
/*12*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
					@ParameterLayout(named = "Adjunto") final Adjunto riesgoCombinadoFamiliarAdjunto)
	    {
			List<Adjunto> riesgoCombinadoFamiliarListaAdjunto = new ArrayList<>();
			riesgoCombinadoFamiliarListaAdjunto.add(riesgoCombinadoFamiliarAdjunto);
	        return polizasRepository.crear(
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
	        		riesgoCombinadoFamiliarListaAdjunto);
	    }

	    public List<Persona> choices1Crear(){
	    	return personaRepository.listarActivos();
	    }
	    
	    public List<Localidad> choices4Crear(){
		   	return localidadesRepository.listarActivos();
		   }	    
	    
	    public List<Ocupacion> choices5Crear(){
	    	return ocupacionesRepository.listarActivos();
	    }
	    
	    public List<Compania> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<TipoTitular> choices7Crear(){
	    	return tipoTitularesRepository.listarActivos();
	    }
	    
	    public List<TipoVivienda> choices6Crear(){
	    	return tiposViviendaRepository.listarActivos();
	    }
	    
	    public List<DetalleTipoPago> choices12Crear(
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
	    		final double polizaImporteTotal,
				final Adjunto riesgoAutomotorAdjunto) {
			return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	    }
	    
		public List<Adjunto> choices14Crear() {
			return adjuntoRepository.listarActivos();
		}
	    
		public String validateCrear(
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
	    		final double polizaImporteTotal,
				final Adjunto riesgoAutomotorAdjunto){
			if (polizaFechaVigencia.after(polizaFechaVencimiento)){
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
	    @Inject
	    AdjuntoRepository adjuntoRepository;

}