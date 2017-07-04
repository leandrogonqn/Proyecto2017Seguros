package domainapp.dom.modelo;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.marca.Marcas;
import domainapp.dom.marca.MarcasRepository;
import domainapp.dom.tipoVehiculo.TipoVehiculo;
import domainapp.dom.tipoVehiculo.TipoVehiculoRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Modelos"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="modeloId")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Modelos "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Modelos "
                        + "WHERE activo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Modelos "
                        + "WHERE activo == false ") 
})
@javax.jdo.annotations.Unique(name="Modelos_nombre_UNQ", members = {"nombre","marcas"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED,
        bounded=true
)
public class Modelos implements Comparable<Modelos> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Modelo: {nombre}", "nombre", getNombre());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public Modelos(String nombre, TipoVehiculo tipoVehiculo, Marcas marcas) {
		super();
		
		setNombre(nombre);
		setTipoVehiculo(tipoVehiculo);
		setMarcas(marcas);
		this.activo = true;
	}
    
    @javax.jdo.annotations.Column(allowsNull = "true", name="marcaId")
    private Marcas marcas;
   

	public Marcas getMarcas() {
		return marcas;
	}
	public void setMarcas(Marcas marcas) {
		this.marcas = marcas;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", name="tipoVehiculoId")
	private TipoVehiculo tipoVehiculo;
	
	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String nombre;
	
    public String getNombre() {
        return nombre;
    }
    public void setNombre(final String nombre) {
        this.nombre = nombre;
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
    public static class DeleteDomainEvent extends ActionDomainEvent<Modelos> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarModelo() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setActivo(false);
    }
    
    public TipoVehiculo actualizarTipoVehiculo(@ParameterLayout(named="Tipo Vehiculo") final TipoVehiculo name) {
        setTipoVehiculo(name);
        return name;
    }
    
    public List<TipoVehiculo> choices0ActualizarTipoVehiculo(){
    	return tipoVehiculoRepository.listarActivos();
    }
      
    public TipoVehiculo default0ActualizarTipoVehiculo() {
    	return getTipoVehiculo();
    }
    
    public Marcas actualizarMarca(@ParameterLayout(named="Marca") final Marcas name) {
        setMarcas(name);
        return name;
    }
    
    public List<Marcas> choices0ActualizarMarca(){
    	return marcaRepository.listarActivos();
    }
      
    public Marcas default0ActualizarMarca() {
    	return getMarcas();
    }
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "nombre");
    }
    @Override
    public int compareTo(final Modelos other) {
        return ObjectContracts.compare(this, other, "nombre");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @javax.inject.Inject
    TipoVehiculoRepository tipoVehiculoRepository;
    
    @javax.inject.Inject
    MarcasRepository marcaRepository;


    //endregion
}
