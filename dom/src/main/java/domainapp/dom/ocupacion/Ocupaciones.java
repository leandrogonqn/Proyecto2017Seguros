package domainapp.dom.ocupacion;

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


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Ocupaciones"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="ocupacionId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionNombre.toLowerCase().indexOf(:ocupacionNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionActivo == false ") 
})
@javax.jdo.annotations.Unique(name="ocupaciones_ocupacionNombre_UNQ", members = {"ocupacionNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Ocupaciones implements Comparable<Ocupaciones>{

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo: {name}", "name", getOcupacionNombre());
    }
    //endregion

    
    //region > constructor
    public Ocupaciones(final String ocupacionNombre, final String ocupacionDescripcion) {
    	setOcupacionNombre(ocupacionNombre);
    	setOcupacionDescripcion(ocupacionDescripcion);
    	setOcupacionActivo(true);
    }
    //endregion

	//region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String ocupacionNombre;
	
    public String getOcupacionNombre() {
		return ocupacionNombre;
	}

	public void setOcupacionNombre(String ocupacionNombre) {
		this.ocupacionNombre = ocupacionNombre;
	}
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Descripcion")
	private String ocupacionDescripcion;
	
	public String getOcupacionDescripcion() {
		return ocupacionDescripcion;
	}

	public void setOcupacionDescripcion(String ocupacionDescripcion) {
		this.ocupacionDescripcion = ocupacionDescripcion;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean ocupacionActivo;
	
	public boolean isOcupacionActivo() {
		return ocupacionActivo;
	}

	public void setOcupacionActivo(boolean ocupacionActivo) {
		this.ocupacionActivo = ocupacionActivo;
	}

    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Ocupaciones> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    
    public void borrarOcupacion() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setOcupacionActivo(false);
    }
    
	public Ocupaciones actualizarNombre(@ParameterLayout(named="Nombre") final String ocupacionNombre){
		setOcupacionNombre(ocupacionNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getOcupacionNombre();
	}
	
	public Ocupaciones actualizarDescripcion(@ParameterLayout(named="Descripcion") final String ocupacionDescripcion){
		setOcupacionDescripcion(ocupacionDescripcion);
		return this;
	}

	public String default0ActualizarDescripcion(){
		return getOcupacionDescripcion();
	}
	
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "ocupacionNombre");
    }
    @Override
    public int compareTo(final Ocupaciones other) {
        return ObjectContracts.compare(this, other, "name");
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
