package domainapp.dom.poliza;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
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

import domainapp.dom.cliente.Clientes;
import domainapp.dom.tipoVehiculo.TipoVehiculo;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Polizas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="polizaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNumeroPoliza", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Polizas "
                        + "WHERE polizaNumero.toLowerCase().indexOf(:polizaNumero) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Polizas_polizaNumero_UNQ", members = {"polizaNumero"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Polizas implements Comparable<Polizas> {
	
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getPolizaNumero());
    }
    //endregion


    public String getPolizaNumero() {
		return polizaNumero;
	}

	public void setPolizaNumero(String polizaNumero) {
		this.polizaNumero = polizaNumero;
	}

	public Date getPolizaFechaEmision() {
		return polizaFechaEmision;
	}

	public void setPolizaFechaEmision(Date polizaFechaEmision) {
		this.polizaFechaEmision = polizaFechaEmision;
	}

	public Date getPolizaFechaVigencia() {
		return polizaFechaVigencia;
	}

	public void setPolizaFechaVigencia(Date polizaFechaVigencia) {
		this.polizaFechaVigencia = polizaFechaVigencia;
	}

	public Date getPolizaFechaVencimiento() {
		return polizaFechaVencimiento;
	}

	public void setPolizaFechaVencimiento(Date polizaFechaVencimiento) {
		this.polizaFechaVencimiento = polizaFechaVencimiento;
	}

	public Date getPolizaFechaVencimientoPago() {
		return polizaFechaVencimientoPago;
	}

	public void setPolizaFechaVencimientoPago(Date polizaFechaVencimientoPago) {
		this.polizaFechaVencimientoPago = polizaFechaVencimientoPago;
	}

	public Date getPolizaFechaBaja() {
		return polizaFechaBaja;
	}

	public void setPolizaFechaBaja(Date polizaFechaBaja) {
		this.polizaFechaBaja = polizaFechaBaja;
	}

	public String getPolizaMotivoBaja() {
		return polizaMotivoBaja;
	}

	public void setPolizaMotivoBaja(String polizaMotivoBaja) {
		this.polizaMotivoBaja = polizaMotivoBaja;
	}

	public double getPolizaPrecioTotal() {
		return polizaPrecioTotal;
	}

	public void setPolizaPrecioTotal(double polizaPrecioTotal) {
		this.polizaPrecioTotal = polizaPrecioTotal;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", name="clienteId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Cliente")
	private Clientes cliente;

	public Clientes getCliente() {
		return cliente;
	}


	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public static final int NAME_LENGTH = 200;
    // Constructor

    
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="polizaNumero")
	private String polizaNumero;

	public Polizas(String polizaNumero, Date polizaFechaEmision, Date polizaFechaVigencia, Date polizaFechaVencimiento,
			Date polizaFechaVencimientoPago, double polizaPrecioTotal, Clientes cliente) {
		this.polizaNumero = polizaNumero;
		this.polizaFechaEmision = polizaFechaEmision;
		this.polizaFechaVigencia = polizaFechaVigencia;
		this.polizaFechaVencimiento = polizaFechaVencimiento;
		this.polizaFechaVencimientoPago = polizaFechaVencimientoPago;
		this.polizaPrecioTotal = polizaPrecioTotal;
		this.cliente = cliente;
	}


	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="polizaFechaEmision")
	private Date polizaFechaEmision;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	private Date polizaFechaVigencia;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	private Date polizaFechaVencimiento;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	private Date polizaFechaVencimientoPago;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	private Date polizaFechaBaja;
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
	private String polizaMotivoBaja;
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	
	private double polizaPrecioTotal; 
	
    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Polizas> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )

    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "polizaNumero");
    }
    @Override
    public int compareTo(final Polizas other) {
        return ObjectContracts.compare(this, other, "polizaNumero");
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
