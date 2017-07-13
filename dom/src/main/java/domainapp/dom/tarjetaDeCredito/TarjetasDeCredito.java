package domainapp.dom.tarjetaDeCredito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.debitoAutomatico.DebitosAutomaticos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "DetalleTipoPagos"
)
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
public class TarjetasDeCredito extends DetalleTipoPagos implements Comparable<TarjetasDeCredito> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", "Tarjeta de Credito N° :" + getTarjetaDeCreditoNumero());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public TarjetasDeCredito(long tarjetaDeCreditoNumero, int tarjetaDeCreditoMesVencimiento, int tarjetaDeCreditoAnioVencimiento, float tipoPagoImporte) {
    	setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
    	setTarjetaDeCreditoMesVencimiento(tarjetaDeCreditoMesVencimiento);
    	setTarjetaDeCreditoAnioVencimiento(tarjetaDeCreditoAnioVencimiento);
    	setTipoPagoImporte(tipoPagoImporte);
		this.tipoPagoActivo = true;
	}

	@javax.jdo.annotations.Column
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="N° de Tarjeta")
	private long tarjetaDeCreditoNumero;
	
    public long getTarjetaDeCreditoNumero() {
		return tarjetaDeCreditoNumero;
	}
	public void setTarjetaDeCreditoNumero(long tarjetaDeCreditoNumero) {
		this.tarjetaDeCreditoNumero = tarjetaDeCreditoNumero;
	}
	
	@javax.jdo.annotations.Column
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Mes de vencimiento")
	private int tarjetaDeCreditoMesVencimiento;
	
    public int getTarjetaDeCreditoMesVencimiento() {
		return tarjetaDeCreditoMesVencimiento;
	}
	public void setTarjetaDeCreditoMesVencimiento(int tarjetaDeCreditoMesVencimiento) {
		this.tarjetaDeCreditoMesVencimiento = tarjetaDeCreditoMesVencimiento;
	}
	
	@javax.jdo.annotations.Column
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Año de vencimiento")
	private int tarjetaDeCreditoAnioVencimiento;
	
    public int getTarjetaDeCreditoAnioVencimiento() {
		return tarjetaDeCreditoAnioVencimiento;
	}
	public void setTarjetaDeCreditoAnioVencimiento(int tarjetaDeCreditoAnioVencimiento) {
		this.tarjetaDeCreditoAnioVencimiento = tarjetaDeCreditoAnioVencimiento;
	}

    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TarjetasDeCredito> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarTarjetaDeCredito() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoPagoActivo(false);
    }
    
	public TarjetasDeCredito actualizarMesVencimiento(@ParameterLayout(named="Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento){
		setTarjetaDeCreditoMesVencimiento(tarjetaDeCreditoMesVencimiento);
		return this;
	}
	
	public int default0ActualizarMesVencimiento(){
		return getTarjetaDeCreditoMesVencimiento();
	}
	
    public Collection<Integer> choices0ActualizarMesVencimiento(){
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	for (int i = 1; i <= 12 ; i++){
    		numbers.add(i);
    	}
    	return numbers;
    }
	
	public TarjetasDeCredito actualizarAnioVencimiento(@ParameterLayout(named="Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento){
		setTarjetaDeCreditoAnioVencimiento(tarjetaDeCreditoAnioVencimiento);
		return this;
	}
	
	public int default0ActualizarAnioVencimiento(){
		return getTarjetaDeCreditoAnioVencimiento();
	}
	
    public Collection<Integer> choices0ActualizarAnioVencimiento(){
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
	
	public TarjetasDeCredito actualizarNumero(@ParameterLayout(named="N° de tarjeta") final long tarjetaDeCreditoNumero){
		setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
		return this;
	}
	
	public long default0ActualizarNumero(){
		return getTarjetaDeCreditoNumero();
	}
	
	public TarjetasDeCredito actualizarImporte(@ParameterLayout(named="Importe") final float tipoPagoImporte){
		setTipoPagoImporte(tipoPagoImporte);
		return this;
	}

	public float default0ActualizarImporte(){
		return getTipoPagoImporte();
	}
	
	public TarjetasDeCredito actualizarActivo(@ParameterLayout(named="Activo") final boolean tarjetaDeCreditoActivo){
		setTipoPagoActivo(tarjetaDeCreditoActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getTipoPagoActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tarjetaDeCreditoNumero");
    }
    @Override
    public int compareTo(final TarjetasDeCredito other) {
        return ObjectContracts.compare(this, other, "tarjetaDeCreditoNumero");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;


    //endregion
}
