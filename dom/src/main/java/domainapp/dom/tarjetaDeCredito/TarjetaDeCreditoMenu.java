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
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;
import domainapp.dom.banco.BancoRepository;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.tipoTarjeta.TipoTarjeta;
import domainapp.dom.tipoTarjeta.TipoTarjetaRepository;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;
import domainapp.dom.debitoAutomatico.DebitoAutomatico;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TarjetaDeCredito.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "60.3"
)
public class TarjetaDeCreditoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<TarjetaDeCreditoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Tarjeta de Credito")
	    public TarjetaDeCredito crear(
	    		@ParameterLayout(named="Titular") final String tipoPagoTitular,
	    		@ParameterLayout(named="Tipo de Tarjeta") final TipoTarjeta tipoTarjeta,
	    		@ParameterLayout(named="Banco") final Banco banco,
	    		@Parameter(optionality=Optionality.OPTIONAL) 
	    		@ParameterLayout(named="N° de tarjeta") final long tarjetaDeCreditoNumero,
	            @ParameterLayout(named="Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento,
	            @ParameterLayout(named="Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento){
	        return debitoAutomaticoRepository.crear(tipoPagoTitular, tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
	    }
	    
	    public List<TipoTarjeta> choices1Crear(){
	    	return tipoTarjetasRepository.listarActivos();
	    }
	    
	    public List<Banco> choices2Crear(){
	    	return bancoRepository.listarActivos();
	    }
	    
	    public Collection<Integer> choices4Crear(){
	    	ArrayList<Integer> numbers = new ArrayList<Integer>();
	    	for (int i = 1; i <= 12 ; i++){
	    		numbers.add(i);
	    	}
	    	return numbers;
	    }
	    
	    public Collection<Integer> choices5Crear(){
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
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Tarjetas de credito")
	    @MemberOrder(sequence = "2")
	    public List<TarjetaDeCredito> listarPagos() {
	        return debitoAutomaticoRepository.listar();
	    }
	    
	    @javax.inject.Inject
	    TarjetaDeCreditoRepository debitoAutomaticoRepository;
	    
	    @Inject
	    TipoTarjetaRepository tipoTarjetasRepository;
	    
	    @Inject
	    BancoRepository bancoRepository;
	    
	    @javax.inject.Inject
	    RepositoryService repositoryService;
	    
	    

}
