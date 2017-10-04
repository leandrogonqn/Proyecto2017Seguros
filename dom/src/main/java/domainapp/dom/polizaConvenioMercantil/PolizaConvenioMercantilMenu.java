package domainapp.dom.polizaConvenioMercantil;

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
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.compania.CompaniaRepository;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoMenu;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCoberturaRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaConvenioMercantil.class
)
@DomainServiceLayout(
        named = "Polizas",
        menuOrder = "1.3"
)
public class PolizaConvenioMercantilMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Convenio Mercantil")
	  @MemberOrder(sequence = "2")
	  public List<PolizaConvenioMercantil> listar() {
			  List<PolizaConvenioMercantil> listaPolizaRiesgosConvenioMercantil = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosConvenioMercantil.size(); i++) {
				  listaPolizaRiesgosConvenioMercantil.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosConvenioMercantil;
	    }
	  
	    public static class CreateDomainEvent extends ActionDomainEvent<PolizaConvenioMercantilMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class, invokeOn=InvokeOn.OBJECT_ONLY)
	    @ActionLayout(named="Crear Poliza Convenio Mercantil ")
	    @MemberOrder(sequence = "1")
	    public PolizaConvenioMercantil crear(
/*0*/	            @ParameterLayout(named="Número") final String polizaNumero,
/*1*/	            @ParameterLayout(named="Cliente") final Persona polizaCliente,
/*2*/	            @ParameterLayout(named="Compañia") final Compania polizaCompania,
/*7*/	            @ParameterLayout(named="Fecha Emision") final Date polizaFechaEmision,
/*8*/				@ParameterLayout(named="Fecha Vigencia") final Date polizaFechaVigencia,
/*9*/				@ParameterLayout(named="Fecha Vencimiento") final Date polizaFechaVencimiento,
					@ParameterLayout(named = "Tipo de Pago") final TipoPago polizaTipoDePago,
					@Nullable @ParameterLayout(named = "Detalle del Pago")@Parameter(optionality =Optionality.OPTIONAL) final DetalleTipoPago polizaPago,
/*13*/				@ParameterLayout(named="Precio Total") final double polizaImporteTotal,
	    			@ParameterLayout(named="Monto") final float riesgoConvenioMercantilMonto)
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
	        		riesgoConvenioMercantilMonto);
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
				final float riesgoConvenioMercantilMonto) {
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
				final float riesgoConvenioMercantilMonto){
			if (polizaFechaVigencia.after(polizaFechaVencimiento)){
				return "La fecha de vigencia es mayor a la de vencimiento";
			}
			return "";
		}


	    @javax.inject.Inject
	    PolizaConvenioMercantilRepository polizasRepository;
	    @javax.inject.Inject
	    PersonaRepository personaRepository;
	    @Inject
	    DetalleTipoPagoMenu detalleTipoPagoMenu;
	    @Inject
	    CompaniaRepository companiaRepository;
	    @Inject
	    TipoDeCoberturaRepository tiposDeCoberturasRepository;
}
