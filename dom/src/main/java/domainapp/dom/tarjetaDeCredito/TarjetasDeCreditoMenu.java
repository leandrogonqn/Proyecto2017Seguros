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

import domainapp.dom.banco.Bancos;
import domainapp.dom.banco.BancosRepository;
import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.ClientesRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.tipoTarjeta.TiposTarjetas;
import domainapp.dom.tipoTarjeta.TiposTarjetasRepository;
import domainapp.dom.cliente.ClientesMenu.CreateDomainEvent;
import domainapp.dom.debitoAutomatico.DebitosAutomaticos;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TarjetasDeCredito.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "10.3"
)
public class TarjetasDeCreditoMenu {
	
	    public static class CreateDomainEvent extends ActionDomainEvent<TarjetasDeCreditoMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Tarjeta de Credito")
	    public TarjetasDeCredito crear(
	    		@ParameterLayout(named="Titular") final String tipoPagoTitular,
	    		@ParameterLayout(named="Tipo de Tarjeta") final TiposTarjetas tipoTarjeta,
	    		@ParameterLayout(named="Banco") final Bancos banco,
	    		@Parameter(optionality=Optionality.OPTIONAL) 
	    		@ParameterLayout(named="N° de tarjeta") final long tarjetaDeCreditoNumero,
	            @ParameterLayout(named="Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento,
	            @ParameterLayout(named="Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento){
	        return debitoAutomaticoRepository.crear(tipoPagoTitular, tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
	    }
	    
	    public List<TiposTarjetas> choices1Crear(){
	    	return tipoTarjetasRepository.listarActivos();
	    }
	    
	    public List<Bancos> choices2Crear(){
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
	    public List<TarjetasDeCredito> listarPagos() {
	        return debitoAutomaticoRepository.listar();
	    }
	    
	    @javax.inject.Inject
	    TarjetasDeCreditoRepository debitoAutomaticoRepository;
	    
	    @Inject
	    TiposTarjetasRepository tipoTarjetasRepository;
	    
	    @Inject
	    BancosRepository bancoRepository;
	    
	    @javax.inject.Inject
	    RepositoryService repositoryService;
	    
	    

}
