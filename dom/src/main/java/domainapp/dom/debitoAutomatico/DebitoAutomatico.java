package domainapp.dom.debitoAutomatico;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
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

import domainapp.dom.banco.Banco;
import domainapp.dom.banco.BancoRepository;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.tarjetaDeCredito.TarjetaDeCredito;

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
@Discriminator(value="Debito Automatico")
public class DebitoAutomatico extends DetalleTipoPago implements Comparable<DebitoAutomatico> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name","Debito Automatico - Titular: " + getTipoPagoTitular() + " - CBU:" + getDebitoAutomaticoCbu());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public DebitoAutomatico() {
    	this.tipoPagoNombre = "Debito Automatico";
		this.tipoPagoActivo = true;
	}
    
    public DebitoAutomatico(
    		String tipoPagoTitular,
    		Banco tipoPagoBanco, 
    		BigInteger debitoAutomaticoCbu) {
    	this.tipoPagoNombre = "Debito Automatico";
    	setTipoPagoTitular(tipoPagoTitular);
    	setTipoPagoBanco(tipoPagoBanco); 
    	setDebitoAutomaticoCbu(debitoAutomaticoCbu);
		this.tipoPagoActivo = true;
	}

	@javax.jdo.annotations.Column
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="CBU")
	private BigInteger debitoAutomaticoCbu;
	
    public BigInteger getDebitoAutomaticoCbu() {
		return debitoAutomaticoCbu;
	}
	public void setDebitoAutomaticoCbu(BigInteger debitoAutomaticoCbu) {
		this.debitoAutomaticoCbu = debitoAutomaticoCbu;
	}

    //endregion
	
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<DebitoAutomatico> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarDebitoAutomatico() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoPagoActivo(false);
    }
    
	public DebitoAutomatico actualizarTipoPagoTitular(@ParameterLayout(named="Titular") final String tipoPagoTitular){
		setTipoPagoTitular(tipoPagoTitular);
		return this;
	}
	
	public String default0ActualizarTipoPagoTitular(){
		return getTipoPagoTitular();
	}
	
    public DebitoAutomatico actualizarTipoPagoBanco(@ParameterLayout(named="Banco") final Banco tipoPagoBanco){
    	setTipoPagoBanco(tipoPagoBanco);
    	return this;
    }
    
    public Banco default0ActualizarTipoPagoBanco(){
    	return getTipoPagoBanco();
    }
    
    public List<Banco> choices0ActualizarTipoPagoBanco(){
    	return debitoAutomaticoBancosRepository.listarActivos();
    }
    
	public DebitoAutomatico actualizarCbu(@ParameterLayout(named="CBU") final BigInteger debitoAutomaticoCbu){
		setDebitoAutomaticoCbu(debitoAutomaticoCbu);
		return this;
	}
	
	public BigInteger default0ActualizarCbu(){
		return getDebitoAutomaticoCbu();
	}
	
	public DebitoAutomatico actualizarActivo(@ParameterLayout(named="Activo") final boolean debitoAutomaticoActivo){
		setTipoPagoActivo(debitoAutomaticoActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getTipoPagoActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "debitoAutomaticoCbu");
    }
    @Override
    public int compareTo(final DebitoAutomatico other) {
        return ObjectContracts.compare(this, other, "debitoAutomaticoCbu");
    }

    //endregion

    //region > injected dependencies
    
	@javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    BancoRepository debitoAutomaticoBancosRepository;


    //endregion
}