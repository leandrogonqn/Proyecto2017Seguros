//package domainapp.dom.detalleTipoPago;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.swing.JOptionPane;
//
//import org.apache.isis.applib.annotation.Action;
//import org.apache.isis.applib.annotation.ActionLayout;
//import org.apache.isis.applib.annotation.DomainService;
//import org.apache.isis.applib.annotation.DomainServiceLayout;
//import org.apache.isis.applib.annotation.Editing;
//import org.apache.isis.applib.annotation.MemberOrder;
//import org.apache.isis.applib.annotation.NatureOfService;
//import org.apache.isis.applib.annotation.Optionality;
//import org.apache.isis.applib.annotation.Parameter;
//import org.apache.isis.applib.annotation.ParameterLayout;
//import org.apache.isis.applib.annotation.Property;
//import org.apache.isis.applib.query.QueryDefault;
//import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
//import org.apache.isis.applib.services.repository.RepositoryService;
//
//import domainapp.dom.banco.Bancos;
//import domainapp.dom.banco.BancosRepository;
//import domainapp.dom.debitoAutomatico.DebitosAutomaticosMenu;
//import domainapp.dom.debitoAutomatico.DebitosAutomaticosRepository;
//import domainapp.dom.marca.Marcas;
//import domainapp.dom.pagoEfectivo.PagosEfectivoMenu;
//import domainapp.dom.pagoEfectivo.PagosEfectivoRepository;
//import domainapp.dom.tarjetaDeCredito.TarjetasDeCreditoMenu;
//import domainapp.dom.tarjetaDeCredito.TarjetasDeCreditoRepository;
//import domainapp.dom.tipoTarjeta.TiposTarjetas;
//import domainapp.dom.tipoTarjeta.TiposTarjetasRepository;
//
//@DomainService(
//        nature = NatureOfService.DOMAIN,
//        repositoryFor = PagosEfectivoMenu.class
//)
//@DomainServiceLayout(
//        named = "Tipo de pago",
//        menuOrder = "10"
//)
//public class TipoPagoMenu {
//	
//	public static class CreateDomainEvent extends ActionDomainEvent<TipoPagoMenu> {}
//    @Action(domainEvent = CreateDomainEvent.class)
//    @MemberOrder(sequence = "1.2")
//    public DetalleTipoPagos crear(
//    		@ParameterLayout(named="Tipo de Pago") final TipoPagos tipoPago,
//    		@ParameterLayout(named="Tipo de Tarjeta") final TiposTarjetas tipoTarjeta,
//    		@ParameterLayout(named="Banco") final Bancos banco,
//    		@Parameter(optionality=Optionality.OPTIONAL) 
//    		@ParameterLayout(named="N° de tarjeta") final long tarjetaDeCreditoNumero,
//            @ParameterLayout(named="Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento,
//            @ParameterLayout(named="Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento,
//            @ParameterLayout(named="CBU") final BigInteger debitoAutomaticoCbu){
//    	DetalleTipoPagos a = null;
//    	if (tipoPago.toString()=="Efectivo"){
//        	List<DetalleTipoPagos> listaEfectivo = listarEfectivo();
//            if(listaEfectivo.isEmpty())
//            {
//            	a = pagosEfectivoRepository.crear();
//            }
//            else
//            {
//            	a = listaEfectivo.get(0);
//            }
//    	}
//    	
//    	if (tipoPago.toString()=="TarjetaDeCrédito"){
//			a = tarjetasDeCreditoRepository.crear(tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
//    	}
//    	
//    	if (tipoPago.toString()=="DébitoAutomatico"){
//    		a = debitosAutomaticosRepository.crear(banco, debitoAutomaticoCbu);
//    	}
//    	
//    	return a;
//    }
//
//    @Property(
//            editing = Editing.DISABLED, editingDisabledReason=" "
//    )
//    @MemberOrder(sequence="1.1")
//    @ActionLayout(named="General")
//    public void titulo(){}
//    
//    public List<TiposTarjetas> choices1Crear(){
//    	return tipoTarjetasRepository.listarActivos();
//    }
//    
//    public List<Bancos> choices2Crear(){
//    	return bancoRepository.listarActivos();
//    }
//    
//    public Collection<Integer> choices4Crear(){
//    	ArrayList<Integer> numbers = new ArrayList<Integer>();
//    	for (int i = 1; i <= 12 ; i++){
//    		numbers.add(i);
//    	}
//    	return numbers;
//    }
//    
//    public Collection<Integer> choices5Crear(){
//    	ArrayList<Integer> numbers = new ArrayList<Integer>();
//    	Calendar hoy= Calendar.getInstance(); 
//    	int año= hoy.get(Calendar.YEAR); 
//    	int añoposterios = año + 30;
//    	for (int i = año; i <= añoposterios; i++)
//    	{
//    	   numbers.add(i);
//    	}
//    	return numbers;
//    }
//    
//    public List<DetalleTipoPagos> listarActivos(){
//      	 return repositoryService.allMatches(
//                   new QueryDefault<>(
//                           DetalleTipoPagos.class,
//                           "listarActivos"));
//    }
//    
//	public List<DetalleTipoPagos> listarEfectivo(){
//    	List<DetalleTipoPagos> listaEfectivo = listarActivos();
//    	Iterator<DetalleTipoPagos> it = listaEfectivo.iterator();
//    	while (it.hasNext()) {
//    	    DetalleTipoPagos lista = it.next();
//    	    if (!lista.tipoPagoNombre.equals("Efectivo")) {
//    	    	it.remove();
//    	    }
//    	}
//		return listaEfectivo;
//    }
//
//    @Inject 
//    PagosEfectivoRepository pagosEfectivoRepository;
//    
//    @Inject
//    TarjetasDeCreditoRepository tarjetasDeCreditoRepository;
//    
//    @Inject
//    DebitosAutomaticosRepository debitosAutomaticosRepository;
//
//    @Inject
//    TiposTarjetasRepository tipoTarjetasRepository;
//    
//    @Inject
//    BancosRepository bancoRepository;
//    
//    @javax.inject.Inject
//    RepositoryService repositoryService;
//    
//}
