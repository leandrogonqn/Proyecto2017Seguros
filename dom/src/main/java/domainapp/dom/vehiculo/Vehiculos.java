package domainapp.dom.vehiculo;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.modelo.Modelos;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Vehiculos"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="vechiculoId")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorDominio", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Vehiculos "
                        + "WHERE vehiculoDominio.toLowerCase().indexOf(:vehiculoDominio) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Vehiculos "
                        + "WHERE vehiculoActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Vehiculos "
                        + "WHERE vehiculoActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Vehiculos_vehiculoDominio_UNQ", members = {"vehiculoDominio"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED,
        bounded = true
)
public class Vehiculos implements Comparable<Vehiculos> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Vechiculo: {vehiculoDominio}", "vehiculoDominio", getVehiculoDominio());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public Vehiculos(String vehiculoDominio, int vehiculoAnio, String vehiculoNumeroMotor, String vehiculoNumeroChasis,Modelos vehiculoModelo) {
		super();
		this.vehiculoDominio = vehiculoDominio;
		this.vehiculoAnio = vehiculoAnio;
		this.vehiculoNumeroMotor = vehiculoNumeroMotor;
		this.vehiculoNumeroChasis = vehiculoNumeroChasis;
		setVehiculoModelo(vehiculoModelo);
		this.vehiculoActivo = true;
	}


    @javax.jdo.annotations.Column(allowsNull = "false", name="modeloId")
    private Modelos vehiculoModelo;

	public Modelos getVehiculoModelo() {
		return vehiculoModelo;
	}
	public void setVehiculoModelo(Modelos vehiculoModelo) {
		this.vehiculoModelo = vehiculoModelo;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String vehiculoDominio;
	
    public String getVehiculoDominio() {
        return vehiculoDominio;
    }
    public void setVehiculoDominio(final String vehiculoDominio) {
        this.vehiculoDominio = vehiculoDominio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false")
    private int vehiculoAnio;
	
    public int getVehiculoAnio() {
        return vehiculoAnio;
    }
    public void setVehiculoAnio(final int vehiculoAnio) {
        this.vehiculoAnio = vehiculoAnio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String vehiculoNumeroMotor;
	
    public String getVehiculoNumeroMotor() {
        return vehiculoNumeroMotor;
    }
    public void setVehiculoNumeroMotor(final String vehiculoNumeroMotor) {
        this.vehiculoNumeroMotor = vehiculoNumeroMotor;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String vehiculoNumeroChasis;
	
    public String getVehiculoNumeroChasis() {
        return vehiculoNumeroChasis;
    }
    public void setVehiculoNumeroChasis(final String vehiculoNumeroChasis) {
        this.vehiculoNumeroChasis = vehiculoNumeroChasis;
    }	
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean vehiculoActivo;
//    @Property(
//            editing = Editing.DISABLED
//    )
    public boolean getVehiculoActivo() {
		return vehiculoActivo;
	}
	public void setVehiculoActivo(boolean vehiculoActivo) {
		this.vehiculoActivo = vehiculoActivo;
	}	
	
    //endregion


    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Vehiculos> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarVechiculo() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setVehiculoActivo(false);
    }
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "vehiculoDominio");
    }
    @Override
    public int compareTo(final Vehiculos other) {
        return ObjectContracts.compare(this, other, "vehiculoDominio");
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
