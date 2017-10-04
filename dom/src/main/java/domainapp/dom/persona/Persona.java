package domainapp.dom.persona;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.localidad.Localidad;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(
	        name = "listarActivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM domainapp.dom.simple.Clientes "
	                + "WHERE personaActivo == true "),
	@javax.jdo.annotations.Query(
	        name = "listarInactivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM domainapp.dom.simple.Clientes "
	                + "WHERE personaActivo == false ")
})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="personaId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Unique(name="Persona_peronaCuitCuil_UNQ", members = {"personaCuitCuil"})
public abstract class Persona {
	
    public String cssClass(){
    	return (getPersonaActivo()==true)? "activo":"inactivo";
    }
	
	public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", name="localidadId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Localidad")
    private Localidad personaLocalidad;


	public Localidad getPersonaLocalidad() {
		return personaLocalidad;
	}

	public void setPersonaLocalidad(Localidad personaLocalidad) {
		this.personaLocalidad = personaLocalidad;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Direccion")
    private String personaDireccion;

    public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}	

    @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Telefono")
    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	

    @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Mail")
    private String personaMail;

    public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Cuit/Cuil")
    private String personaCuitCuil;
    public String getPersonaCuitCuil() {
		return personaCuitCuil;
	}
	public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
    private boolean personaActivo;
 
    public boolean getPersonaActivo() {
		return personaActivo;
	}
	public void setPersonaActivo(boolean personaActivo) {
		this.personaActivo = personaActivo;
	}	
	
	@Action(invokeOn = InvokeOn.COLLECTION_ONLY)
	@ActionLayout(named = "Listar Todos los clientes")
	@MemberOrder(sequence = "2")
	public List<Persona> listarPagos() {
		return personaRepository.listar();
	}

	@MemberOrder(sequence = "1.2")
	@Action(invokeOn = InvokeOn.COLLECTION_ONLY)
	@ActionLayout(named = "Listar clientes Activos")
	public List<Persona> listarActivos() {
		return personaRepository.listarActivos();
	}

	@MemberOrder(sequence = "1.2")
	@Action(invokeOn = InvokeOn.COLLECTION_ONLY)
	@ActionLayout(named = "Listar clientes Inactivos")
	public List<Persona> listarInactivos() {
		return personaRepository.listarInactivos();
	}
	
	@Inject
	PersonaRepository personaRepository;

}
