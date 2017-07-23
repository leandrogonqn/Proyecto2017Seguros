package domainapp.dom.compania;

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
        table = "Companias"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="companiaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Companias "
                        + "WHERE companiaNombre.toLowerCase().indexOf(:companiaNombre) >= 0 "),

        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Companias "
                        + "WHERE companiaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Companias "
                        + "WHERE companiaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Companias_companiaNombre_UNQ", members = {"companiaNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Companias implements Comparable<Companias> {
	
	//region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getCompaniaNombre());
    }
    //endregion
    
    public String iconName(){
    	String a;
    	
    	a = getCompaniaNombre();
    	
    	return a;
    }

    public String cssClass(){
    	return (isCompaniaActivo()==true)? "activo":"inactivo";
    }
    
    public static final int NAME_LENGTH = 200;
    // Constructor
    public Companias(String companiaNombre) {
		setCompaniaNombre(companiaNombre);
		setCompaniaActivo(true);
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
	private String companiaNombre;
	
	public String getCompaniaNombre() {
		return companiaNombre;
	}

	public void setCompaniaNombre(String companiaNombre) {
		this.companiaNombre = companiaNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean companiaActivo;

	public boolean isCompaniaActivo() {
		return companiaActivo;
	}

	public void setCompaniaActivo(boolean companiaActivo) {
		this.companiaActivo = companiaActivo;
	}
	
    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Companias> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarMarca() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setCompaniaActivo(false);
    }
    
	public Companias actualizarNombre(@ParameterLayout(named="Nombre") final String companiaNombre){
		setCompaniaNombre(companiaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getCompaniaNombre();
	}
	
	public Companias actualizarActivo(@ParameterLayout(named="Activo") final boolean companiaActivo){
		setCompaniaActivo(companiaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return isCompaniaActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "companiaNombre");
    }
    @Override
    public int compareTo(final Companias other) {
        return ObjectContracts.compare(this, other, "companiaNombre");
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
