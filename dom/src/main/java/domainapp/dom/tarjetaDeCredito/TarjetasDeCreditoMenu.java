package domainapp.dom.tarjetaDeCredito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.banco.Bancos;
import domainapp.dom.banco.BancosRepository;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoTarjeta.TiposTarjetas;
import domainapp.dom.tipoTarjeta.TiposTarjetasRepository;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TarjetasDeCredito.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.1"
)
public class TarjetasDeCreditoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<TarjetasDeCreditoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    public TarjetasDeCredito crear(
	    		@ParameterLayout(named="Tipo de Tarjeta") final TiposTarjetas tipoTarjeta,
	    		@ParameterLayout(named="Banco") final Bancos banco,
	            @ParameterLayout(named="N° de tarjeta") final long tarjetaDeCreditoNumero,
	            @ParameterLayout(named="Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento,
	            @ParameterLayout(named="Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento,
	    		@ParameterLayout(named="Importe") final float tipoPagoImporte){
	        return debitoAutomaticoRepository.crear(tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento, tipoPagoImporte);
	    }
	    
	    public List<TiposTarjetas> choices0Crear(){
	    	return tipoTarjetasRepository.listarActivos();
	    }
	    
	    public List<Bancos> choices1Crear(){
	    	return bancoRepository.listarActivos();
	    }
	    
	    public Collection<Integer> choices3Crear(){
	    	ArrayList<Integer> numbers = new ArrayList<Integer>();
	    	for (int i = 1; i <= 12 ; i++){
	    		numbers.add(i);
	    	}
	    	return numbers;
	    }
	    
	    public Collection<Integer> choices4Crear(){
	    	ArrayList<Integer> numbers = new ArrayList<Integer>();
	    	Calendar hoy= Calendar.getInstance(); 
	    	int año= hoy.get(Calendar.YEAR); 
	    	int añoposterios = año + 30;
	    	for (int i = año; i <= añoposterios; i++)
	    	{
	    	   numbers.add(i);
	    	}
	    	return numbers;
	    }
	    
	    @Property(
	            editing = Editing.DISABLED, editingDisabledReason=" "
	    )
	    @MemberOrder(sequence="1.1")
	    @ActionLayout(named="Tarjeta de Credito")
	    public void titulo(){}

	    @javax.inject.Inject
	    TarjetasDeCreditoRepository debitoAutomaticoRepository;
	    
	    @Inject
	    TiposTarjetasRepository tipoTarjetasRepository;
	    
	    @Inject
	    BancosRepository bancoRepository;

}
