package domainapp.dom.compania;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
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
public class Compania implements Comparable<Compania> {
	
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
    	return (getCompaniaActivo()==true)? "activo":"inactivo";
    }
    
    public static final int NAME_LENGTH = 200;
    // Constructor
    public Compania(String companiaNombre, String companiaDireccion, String companiaTelefono) {
		setCompaniaNombre(companiaNombre);
		setCompaniaDireccion(companiaDireccion);
		setCompaniaTelefono(companiaTelefono);
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
	
	@javax.jdo.annotations.Column(length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Direccion")
	private String companiaDireccion;
	
	public String getCompaniaDireccion() {
		return companiaDireccion;
	}

	public void setCompaniaDireccion(String companiaDireccion) {
		this.companiaDireccion = companiaDireccion;
	}	

	@javax.jdo.annotations.Column(length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Telefono")
	private String companiaTelefono;
	
	public String getCompaniaTelefono() {
		return companiaTelefono;
	}

	public void setCompaniaTelefono(String companiaTelefono) {
		this.companiaTelefono = companiaTelefono;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean companiaActivo;

	public boolean getCompaniaActivo() {
		return companiaActivo;
	}

	public void setCompaniaActivo(boolean companiaActivo) {
		this.companiaActivo = companiaActivo;
	}
	
    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Compania> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarCompania() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setCompaniaActivo(false);
    }
    
	public Compania actualizarCompaniaNombre(@ParameterLayout(named="Nombre") final String companiaNombre){
		setCompaniaNombre(companiaNombre);
		return this;
	}
	
	public String default0ActualizarCompaniaNombre(){
		return getCompaniaNombre();
	}

	public Compania actualizarCompaniaDireccion(@ParameterLayout(named="Direccion") final String companiaDireccion){
		setCompaniaDireccion(companiaDireccion);
		return this;
	}
	
	public String default0ActualizarCompaniaDireccion(){
		return getCompaniaDireccion();
	}
	
	public Compania actualizarCompaniaTelefono(@ParameterLayout(named="Telefono") final String companiaTelefono){
		setCompaniaTelefono(companiaTelefono);
		return this;
	}
	
	public String default0ActualizarCompaniaTelefono(){
		return getCompaniaTelefono();
	}
	
	public Compania actualizarCompaniaActivo(@ParameterLayout(named="Activo") final boolean companiaActivo){
		setCompaniaActivo(companiaActivo);
		return this;
	}

	public boolean default0ActualizarCompaniaActivo(){
		return getCompaniaActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "companiaNombre");
    }
    @Override
    public int compareTo(final Compania other) {
        return ObjectContracts.compare(this, other, "companiaNombre");
    }

    //endregion
    
    //acciones
	    @ActionLayout(named="Listar Todas las Compañias")
	  @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @MemberOrder(sequence = "2")
	    public List<Compania> listar() {
	        return companiaRepository.listar();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Activos")
	    @MemberOrder(sequence = "3")
	    public List<Compania> listarActivos() {
	        return companiaRepository.listarActivos();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Inactivos")
	    @MemberOrder(sequence = "4")
	    public List<Compania> listarInactivos() {
	        return companiaRepository.listarInactivos();
	    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    CompaniaRepository companiaRepository;

    //endregion
}
