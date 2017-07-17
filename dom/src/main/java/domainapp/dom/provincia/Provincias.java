package domainapp.dom.provincia;

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
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoVehiculo.TipoVehiculo;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Provincias"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="provinciaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Provincias "
                        + "WHERE provinciasNombre.toLowerCase().indexOf(:provinciasNombre) >= 0 "),

        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Provincias "
                        + "WHERE provinciaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Provincias "
                        + "WHERE provinciaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Provincias_provinciasNombre_UNQ", members = {"provinciasNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Provincias implements Comparable<Provincias> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getProvinciasNombre());
    }
    //endregion
    


    public static final int NAME_LENGTH = 200;
    // Constructor
    public Provincias(String provinciaNombre) {
    	setProvinciasNombre(provinciaNombre);
		this.provinciaActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
	private String provinciasNombre;
	
	
	public String getProvinciasNombre() {
		return provinciasNombre;
	}

	public void setProvinciasNombre(String provinciasNombre) {
		this.provinciasNombre = provinciasNombre;
	}



	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean provinciaActivo;

	public boolean getProvinciaActivo() {
		return provinciaActivo;
	}

	public void setProvinciaActivo(boolean provinciaActivo) {
		this.provinciaActivo = provinciaActivo;
	}
   
	
    //endregion

	//region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Provincias> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarProvincia() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setProvinciaActivo(false);
    }
    
	public Provincias actualizarNombre(@ParameterLayout(named="Nombre") final String provinciaNombre){
		setProvinciasNombre(provinciaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getProvinciasNombre();
	}
	
	public Provincias actualizarActivo(@ParameterLayout(named="Activo") final boolean provinciaActivo){
		setProvinciaActivo(provinciaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getProvinciaActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "provinciasNombre");
    }
    @Override
    public int compareTo(final Provincias other) {
        return ObjectContracts.compare(this, other, "provinciasNombre");
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
