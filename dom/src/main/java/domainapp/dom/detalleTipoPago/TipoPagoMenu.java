package domainapp.dom.detalleTipoPago;

import javax.inject.Inject;
import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.pagoEfectivo.PagosEfectivo;
import domainapp.dom.pagoEfectivo.PagosEfectivoMenu;
import domainapp.dom.pagoEfectivo.PagosEfectivoRepository;
import domainapp.dom.pagoEfectivo.PagosEfectivoMenu.CreateDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = PagosEfectivoMenu.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10"
)
public class TipoPagoMenu {
	
	public static class CreateDomainEvent extends ActionDomainEvent<TipoPagoMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1.2")
    public DetalleTipoPagos crear(
    		@ParameterLayout(named="Tipo de Pago") final TipoPagos tipoPago){
    	DetalleTipoPagos a = null;
    	if (tipoPago.toString()=="Efectivo"){
    		float tipoPagoImporte = 0;
    		
//    		JOptionPane.showMessageDialog(null, "efectivo");
    	}
    	return a;
    }

    @Property(
            editing = Editing.DISABLED, editingDisabledReason=" "
    )
    @MemberOrder(sequence="1.1")
    @ActionLayout(named="General")
    public void titulo(){}

    @javax.inject.Inject
    PagosEfectivoRepository pagoEfectivoRepository;
    
    @Inject 
    PagosEfectivoMenu PagosEfectivoRepository;

}
