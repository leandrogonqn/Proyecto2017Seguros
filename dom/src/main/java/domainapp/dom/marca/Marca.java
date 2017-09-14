package domainapp.dom.marca;

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

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoVehiculo.TipoVehiculo;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Marcas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="marcaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Marcas "
                        + "WHERE marcasNombre.toLowerCase().indexOf(:marcasNombre) >= 0 "),

        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Marcas "
                        + "WHERE marcaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Marcas "
                        + "WHERE marcaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Marcas_marcasNombre_UNQ", members = {"marcasNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Marca implements Comparable<Marca> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getMarcasNombre());
    }
    //endregion
    
    public String iconName(){
    	String a;
    	
    	a = getMarcasNombre();
    	
    	return a;
    }

    public String cssClass(){
    	return (getMarcaActivo()==true)? "activo":"inactivo";
    }
    
    public static final int NAME_LENGTH = 200;
    // Constructor
    public Marca(String marcaNombre) {
		setMarcasNombre(marcaNombre);
		setMarcaActivo(true);
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
	private String marcasNombre;
	
    public String getMarcasNombre() {
		return marcasNombre;
	}
	public void setMarcasNombre(String marcasNombre) {
		this.marcasNombre = marcasNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean marcaActivo;

    public boolean getMarcaActivo() {
		return marcaActivo;
	}
	public void setMarcaActivo(boolean marcaActivo) {
		this.marcaActivo = marcaActivo;
	}	
	
    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Marca> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarMarca() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setMarcaActivo(false);
    }
    
	public Marca actualizarNombre(@ParameterLayout(named="Nombre") final String marcaNombre){
		setMarcasNombre(marcaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getMarcasNombre();
	}
	
	public Marca actualizarActivo(@ParameterLayout(named="Activo") final boolean marcaActivo){
		setMarcaActivo(marcaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getMarcaActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "marcasNombre");
    }
    @Override
    public int compareTo(final Marca other) {
        return ObjectContracts.compare(this, other, "marcasNombre");
    }

    //endregion
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar todas las Marcas")
	    @MemberOrder(sequence = "2")
	    public List<Marca> listar() {
	        return marcasRepository.listar();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Marcas Activas")
	    @MemberOrder(sequence = "3")
	    public List<Marca> listarActivos() {
	        return marcasRepository.listarActivos();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Marcas Inactivas")
	    @MemberOrder(sequence = "4")
	    public List<Marca> listarInactivos() {
	        return marcasRepository.listarInactivos();
	    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    MarcaRepository marcasRepository;


    //endregion
}
