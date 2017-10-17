package domainapp.dom.polizaTRO;

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

import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoMenu;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaTRO.class
)
@DomainServiceLayout(
        named = "Polizas Crear",
        menuOrder = "20"
)
public class PolizaTROMenu {
	
	    @Action(invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza TRO")
	    @MemberOrder(sequence = "120")
	    public PolizaTRO crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Persona polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
					@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
					@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
	    			@ParameterLayout(named="Monto") final float riesgoTROMonto)
	    {
	        return polizasRepository.crear(
	        		polizaNumero,
	        		polizaCliente,
	        		polizaCompania,
	        		polizaFechaEmision,
	        		polizaFechaVigencia, 
	        		polizaFechaVencimiento,
	        		polizaTipoDePago,
	        		polizaPago,
	        		polizaImporteTotal,
	        		riesgoTROMonto);
	    }
	    
	    public List<Persona> choices1Crear(){
	    	return personaRepository.listarActivos();
	    }
	    
	    public List<Compania> choices2Crear(){
	    	return companiaRepository.listarActivos();
	    }	    
	    
	    public List<DetalleTipoPago> choices7Crear(			
				final String polizaNumero,
				final Persona polizaCliente,
				final Compania polizaCompania,
				final Date polizaFechaEmision,
				final Date polizaFechaVigencia,
				final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago,
				final DetalleTipoPago polizaPago,
				final double polizaImporteTotal,
				final float riesgoTROMonto) {
			return detalleTipoPagoMenu.buscarPorTipoDePagoCombo(polizaTipoDePago);
	    }

		public String validateCrear(			
				final String polizaNumero,
				final Persona polizaCliente,
				final Compania polizaCompania,
				final Date polizaFechaEmision,
				final Date polizaFechaVigencia,
				final Date polizaFechaVencimiento,
				final TipoPago polizaTipoDePago,
				final DetalleTipoPago polizaPago,
				final double polizaImporteTotal,
				final float riesgoTROMonto){
			if (polizaFechaVigencia.after(polizaFechaVencimiento)){
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
