package domainapp.dom.detalleTipoPago;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;

import domainapp.dom.debitoAutomatico.DebitoAutomaticoRepository;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;
import domainapp.dom.tarjetaDeCredito.TarjetaDeCreditoRepository;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DetalleTipoPago.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.1"
)
public class DetalleTipoPagoMenu {
	
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Todos los Pagos")
	    @MemberOrder(sequence = "2")
	    public List<DetalleTipoPago> listarPagos() {
	        return detalleTipoPagosRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "5")
	    public List<DetalleTipoPago> buscarPorTitular(
	            @ParameterLayout(named="Titular")
	            final String tipoPagoTitular
	    ) {
	        return detalleTipoPagosRepository.buscarPorTitular(tipoPagoTitular);
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "6")
	    public List buscarPorTipoDePago(
	            @ParameterLayout(named="Tipo de Pago")
	            final TipoPago polizaTipoDePago
	    ) {
	    	if (polizaTipoDePago == TipoPago.TarjetaDeCredito){
	    		return tarjetaDeCreditoRepository.listar();
	    	}else
	    	{
	    		if (polizaTipoDePago == TipoPago.DebitoAutomatico){
	    			return debitoAutomaticoRepository.listar();
	    		}else
	    		{
	    			if (polizaTipoDePago == TipoPago.Efectivo)
	    	    	messageService.warnUser("No se puede mostrar una lista de Efectivo");
	    		}
	    	}

	    	return null;
	        //return detalleTipoPagosRepository.buscarPorTipoDePago(polizaTipoDePago.toString());
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", hidden=Where.EVERYWHERE)
	    @MemberOrder(sequence = "6")
	    public List buscarPorTipoDePagoCombo(
	            @ParameterLayout(named="Tipo de Pago")
	            final TipoPago polizaTipoDePago
	    ) {
	    	if (polizaTipoDePago == TipoPago.TarjetaDeCredito){
	    		return tarjetaDeCreditoRepository.listarActivos();
	    	}else
	    	{
	    		if (polizaTipoDePago == TipoPago.DebitoAutomatico){
	    			return debitoAutomaticoRepository.listarActivos();
	    		}
	    	}
	    	return null;
	    }
	    
			  
		@javax.inject.Inject
		DetalleTipoPagoRepository detalleTipoPagosRepository;
		@Inject
		TarjetaDeCreditoRepository tarjetaDeCreditoRepository;
		@Inject 
		DebitoAutomaticoRepository debitoAutomaticoRepository;
		@Inject
		MessageService messageService;
		
}
