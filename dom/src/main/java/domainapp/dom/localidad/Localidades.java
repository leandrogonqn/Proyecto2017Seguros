package domainapp.dom.localidad;

import java.util.List;

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
import domainapp.dom.marca.Marcas;
import domainapp.dom.modelo.Modelos;
import domainapp.dom.provincia.Provincias;
import domainapp.dom.provincia.ProvinciasRepository;
import domainapp.dom.tipoVehiculo.TipoVehiculo;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Localidades"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="localidadId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Localidades "
                        + "WHERE localidadesNombre.toLowerCase().indexOf(:localidadesNombre) >= 0 "),

        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Localidades "
                        + "WHERE localidadActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Localidades "
                        + "WHERE localidadActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Localidades_localidadesNombre_UNQ", members = {"localidadesNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Localidades implements Comparable<Localidades> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getLocalidadesNombre()+" - "+this.getLocalidadProvincia().getProvinciasNombre());
    }
    //endregion
    


    public static final int NAME_LENGTH = 200;
    // Constructor
    public Localidades(String localidadNombre, Provincias localidadProvincia) {
		setLocalidadesNombre(localidadNombre);
		setLocalidadProvincia(localidadProvincia);
		this.localidadActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
	private String localidadesNombre;
	

	public String getLocalidadesNombre() {
		return localidadesNombre;
	}

	public void setLocalidadesNombre(String localidadesNombre) {
		this.localidadesNombre = localidadesNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean localidadActivo;
	
	 @javax.jdo.annotations.Column(allowsNull = "false", name="provinciaId")
	    @Property(
	            editing = Editing.DISABLED
	    )
	    @PropertyLayout(named="Provincia")
	    private Provincias localidadProvincia;

	 
	 public Provincias getLocalidadProvincia() {
			return localidadProvincia;
		}

		public void setLocalidadProvincia(Provincias localidadProvincia) {
			this.localidadProvincia = localidadProvincia;
		}
   
	
    //endregion
    
    

	public boolean getLocalidadActivo() {
		return localidadActivo;
	}

	public void setLocalidadActivo(boolean localidadActivo) {
		this.localidadActivo = localidadActivo;
	}

	//region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Localidades> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarLocalidad() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setLocalidadActivo(false);
    }
    
    public Localidades actualizarProvincia(@ParameterLayout(named="Provincia") final Provincias name) {
        setLocalidadProvincia(name);
        return this;
    }
    
    public List<Provincias> choices0ActualizarProvincia(){
    	return provinciaRepository.listarActivos();
    }
      
    public Provincias default0ActualizarProvincia() {
    	return getLocalidadProvincia();
    }
    
	public Localidades actualizarNombre(@ParameterLayout(named="Nombre") final String localidadNombre){
		setLocalidadesNombre(localidadNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getLocalidadesNombre();
	}
	
	public Localidades actualizarActivo(@ParameterLayout(named="Activo") final boolean localidadActivo){
		setLocalidadActivo(localidadActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getLocalidadActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "localidadesNombre");
    }
    @Override
    public int compareTo(final Localidades other) {
        return ObjectContracts.compare(this, other, "localidadesNombre");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    ProvinciasRepository provinciaRepository;


    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;


    //endregion
}
