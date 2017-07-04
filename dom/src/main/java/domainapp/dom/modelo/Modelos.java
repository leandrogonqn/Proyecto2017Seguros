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
                        + "WHERE modeloNombre.toLowerCase().indexOf(:modeloNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Modelos "
                        + "WHERE modeloActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Modelos "
                        + "WHERE modeloActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Modelos_modeloNombre_UNQ", members = {"modeloNombre","modeloMarcas"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Modelos implements Comparable<Modelos> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{modeloNombre}", "modeloNombre", this.getModeloMarcas().getMarcasNombre()+" | "+this.getModeloNombre()+" | "+this.getModeloTipoVehiculo().getTipoVehiculoNombre());
    }
    //endregion

    public static final int NAME_LENGTH = 200;
    // Constructor
    public Modelos(String modeloNombre, TipoVehiculo modeloTipoVehiculo, Marcas modeloMarcas) {
		super();
		
		setModeloNombre(modeloNombre);
		setModeloTipoVehiculo(modeloTipoVehiculo);
		setModeloMarcas(modeloMarcas);
		this.modeloActivo = true;
	}
    
    @javax.jdo.annotations.Column(allowsNull = "false", name="marcaId")
    @Property(
    		editing = Editing.DISABLED
    		)
    private Marcas modeloMarcas;
   

	public Marcas getModeloMarcas() {
		return modeloMarcas;
	}
	public void setModeloMarcas(Marcas modeloMarcas) {
		this.modeloMarcas = modeloMarcas;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", name="tipoVehiculoId")
	@Property(
	editing = Editing.DISABLED
	)
	private TipoVehiculo modeloTipoVehiculo;
	
	public TipoVehiculo getModeloTipoVehiculo() {
		return modeloTipoVehiculo;
	}
	public void setModeloTipoVehiculo(TipoVehiculo modeloTipoVehiculo) {
		this.modeloTipoVehiculo = modeloTipoVehiculo;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	
    private String modeloNombre;
	
    public String getModeloNombre() {
        return modeloNombre;
    }
    public void setModeloNombre(final String modeloNombre) {
        this.modeloNombre = modeloNombre;
    }
    
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean modeloActivo;
//    @Property(
//            editing = Editing.DISABLED
//    )
    public boolean getModeloActivo() {
		return modeloActivo;
	}
	public void setModeloActivo(boolean modeloActivo) {
		this.modeloActivo = modeloActivo;
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
        setModeloActivo(false);
    }
    
    public Modelos actualizarTipoVehiculo(@ParameterLayout(named="Tipo Vehiculo") final TipoVehiculo name) {
        setModeloTipoVehiculo(name);
        return this;
    }
    
    public List<TipoVehiculo> choices0ActualizarTipoVehiculo(){
    	return tipoVehiculoRepository.listarActivos();
    }
      
    public TipoVehiculo default0ActualizarTipoVehiculo() {
    	return getModeloTipoVehiculo();
    }
    
    public Modelos actualizarMarca(@ParameterLayout(named="Marca") final Marcas name) {
        setModeloMarcas(name);
        return this;
    }
    
    public List<Marcas> choices0ActualizarMarca(){
    	return marcaRepository.listarActivos();
    }
      
    public Marcas default0ActualizarMarca() {
    	return getModeloMarcas();
    }
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "modeloNombre");
    }
    @Override
    public int compareTo(final Modelos other) {
        return ObjectContracts.compare(this, other, "modeloNombre");
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
