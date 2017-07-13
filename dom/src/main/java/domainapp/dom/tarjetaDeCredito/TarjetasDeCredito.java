package domainapp.dom.tarjetaDeCredito;

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
        return TranslatableString.tr("{name}", "name", getTarjetaDeCreditoNumero());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public TarjetasDeCredito(int tarjetaDeCreditoNumero, float tipoPagoImporte) {
    	setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
    	setTipoPagoImporte(tipoPagoImporte);
		this.tipoPagoActivo = true;
	}

	@javax.jdo.annotations.Column
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="N° de Tarjeta")
	private int tarjetaDeCreditoNumero;
	
    public int getTarjetaDeCreditoNumero() {
		return tarjetaDeCreditoNumero;
	}
	public void setTarjetaDeCreditoNumero(int tarjetaDeCreditoNumero) {
		this.tarjetaDeCreditoNumero = tarjetaDeCreditoNumero;
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
    
	public TarjetasDeCredito actualizarNumero(@ParameterLayout(named="N° de tarjeta") final int tarjetaDeCreditoNumero){
		setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
		return this;
	}
	
	public int default0ActualizarNumero(){
		return getTarjetaDeCreditoNumero();
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
