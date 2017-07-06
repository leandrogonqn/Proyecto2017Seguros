package domainapp.dom.marca;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
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
        table = "Marcas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="marcaId")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
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
public class Marcas implements Comparable<Marcas> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getMarcasNombre());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public Marcas(String marcaNombre) {
		setMarcasNombre(marcaNombre);
		this.marcaActivo = true;
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
    public static class DeleteDomainEvent extends ActionDomainEvent<Marcas> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarMarca() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setMarcaActivo(false);
    }
    
	public Marcas actualizarNombre(@ParameterLayout(named="Nombre") final String marcaNombre){
		setMarcasNombre(marcaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getMarcasNombre();
	}
	
	public Marcas actualizarActivo(@ParameterLayout(named="Activo") final boolean marcaActivo){
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
    public int compareTo(final Marcas other) {
        return ObjectContracts.compare(this, other, "marcasNombre");
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
