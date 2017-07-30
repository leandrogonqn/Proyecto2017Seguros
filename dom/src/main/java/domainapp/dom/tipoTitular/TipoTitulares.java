package domainapp.dom.tipoTitular;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
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
        table = "TipoTitulares"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tipoTitularId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTitulares "
                        + "WHERE tipoTitularNombre.toLowerCase().indexOf(:tipoTitularNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTitulares "
                        + "WHERE tipoTitularActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTitulares "
                        + "WHERE tipoTitularActivo == false ") 
})
@javax.jdo.annotations.Unique(name="tipoTitulares_tipoTitularNombre_UNQ", members = {"tipoTitularNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class TipoTitulares implements Comparable<TipoTitulares>{
	
    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo: {name}", "name", getTipoTitularNombre());
    }
    //endregion

    
    //region > constructor
    public TipoTitulares(final String tipoTitularNombre) {
    	setTipoTitularNombre(tipoTitularNombre);
    	setTipoTitularActivo(true);
    }
    //endregion

	//region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(editing = Editing.DISABLED,optionality=Optionality.OPTIONAL)
    @PropertyLayout(named="Nombre")
    private String tipoTitularNombre;

	public String getTipoTitularNombre() {
		return tipoTitularNombre;
	}

	public void setTipoTitularNombre(String tipoTitularNombre) {
		this.tipoTitularNombre = tipoTitularNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named="Activo")
	private boolean tipoTitularActivo;
	
	public boolean isTipoTitularActivo() {
		return tipoTitularActivo;
	}

	public void setTipoTitularActivo(boolean tipoTitularActivo) {
		this.tipoTitularActivo = tipoTitularActivo;
	}
    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TipoTitulares> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    
    public void borrarTipoTitular() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoTitularActivo(false);
    }
    
	public TipoTitulares actualizarNombre(@ParameterLayout(named="Nombre") final String tipoTitularNombre){
		setTipoTitularNombre(tipoTitularNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getTipoTitularNombre();
	}
	
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tipoTitularNombre");
    }
    @Override
    public int compareTo(final TipoTitulares other) {
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