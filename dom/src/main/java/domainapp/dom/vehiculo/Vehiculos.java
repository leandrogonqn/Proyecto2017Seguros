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
                        + "WHERE dominio.indexOf(:dominio) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Vehiculos "
                        + "WHERE activo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Vehiculos "
                        + "WHERE activo == false ") 
})
@javax.jdo.annotations.Unique(name="Vehiculos_dominio_UNQ", members = {"dominio"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Vehiculos implements Comparable<Vehiculos> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Vechiculo: {dominio}", "dominio", getDominio());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public Vehiculos(String dominio, int anio, String numeroMotor, String numeroChasis) {
		super();
		this.dominio = dominio;
		this.anio = anio;
		this.numeroMotor = numeroMotor;
		this.numeroChasis = numeroChasis;
		this.activo = true;
	}



	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String dominio;
	
    public String getDominio() {
        return dominio;
    }
    public void setDominio(final String dominio) {
        this.dominio = dominio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false")
    private int anio;
	
    public int getAnio() {
        return anio;
    }
    public void setAnio(final int anio) {
        this.anio = anio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String numeroMotor;
	
    public String getNumeroMotor() {
        return numeroMotor;
    }
    public void setNumeroMotor(final String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String numeroChasis;
	
    public String getNumeroChasis() {
        return numeroChasis;
    }
    public void setNumeroChasis(final String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }	
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean activo;
    @Property(
            editing = Editing.DISABLED
    )
    public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
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
        setActivo(false);
    }
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "dominio");
    }
    @Override
    public int compareTo(final Vehiculos other) {
        return ObjectContracts.compare(this, other, "dominio");
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
