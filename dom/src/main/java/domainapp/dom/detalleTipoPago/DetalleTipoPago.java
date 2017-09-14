package domainapp.dom.detalleTipoPago;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "DetalleTipoPagos"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="detalleTipoPagoId")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "buscarPorTitular", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPago "
                    + "WHERE tipoPagoTitular.toLowerCase().indexOf(:tipoPagoTitular) >= 0 "),
    @javax.jdo.annotations.Query(
            name = "listarActivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPagos "
                    + "WHERE tipoPagoActivo == true "),
    @javax.jdo.annotations.Query(
            name = "listarInactivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPagos "
                    + "WHERE tipoPagoActivo == false ")})
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP, column="tipoDePagoNombre")
public abstract class DetalleTipoPago {
	
    public String cssClass(){
    	return (getTipoPagoActivo()==true)? "activo":"inactivo";
    }
	
    @Column(allowsNull="false")
    @Property(
    		editing=Editing.DISABLED
	)
    @PropertyLayout(named="Activo")
    protected boolean tipoPagoActivo;
    
	public boolean getTipoPagoActivo() {
		return tipoPagoActivo;
	}

	public void setTipoPagoActivo(boolean tipoPagoActivo) {
		this.tipoPagoActivo = tipoPagoActivo;
	}
	
    @Column(name="tipoDePagoNombre")
    @Property(
    		editing=Editing.DISABLED
	)
    @PropertyLayout(named="Nombre")
    @NotPersistent
	protected String tipoPagoNombre;
    
	public String getTipoPagoNombre() {
		return tipoPagoNombre;
	}

	public void setTipoPagoNombre(String tipoPagoNombre) {
		this.tipoPagoNombre = tipoPagoNombre;
	}
	
	@Column
	@Property(
			editing=Editing.DISABLED
	)
	@PropertyLayout(named="Titular")
	protected String tipoPagoTitular;
	    
	public String getTipoPagoTitular() {
		return tipoPagoTitular;
	}

	public void setTipoPagoTitular(String tipoPagoTitular) {
		this.tipoPagoTitular = tipoPagoTitular;
	}
	
	@Column(name="bancoId")
	@Property(
			editing=Editing.DISABLED
			)
	@PropertyLayout(named="Banco")
	private Banco tipoPagoBanco;
	
	public Banco getTipoPagoBanco() {
		return tipoPagoBanco;
	}
	public void setTipoPagoBanco(Banco tipoPagoBanco) {
		this.tipoPagoBanco = tipoPagoBanco;
	}
	
	//acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Todos los Pagos")
    @MemberOrder(sequence = "2")
    public List<DetalleTipoPago> listarPagos() {
        return detalleTipoPagosRepository.listar();
    }

    @MemberOrder(sequence="1.2")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Pagos Activos")
	public List<DetalleTipoPago> listarActivos(){
		 return detalleTipoPagosRepository.listarActivos();
	  }
    
    @MemberOrder(sequence="1.2")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Pagos Inactivos")
	public List<DetalleTipoPago> listarInactivos(){
		 return detalleTipoPagosRepository.listarInactivos();
	  }
    
    @Inject
    DetalleTipoPagoRepository detalleTipoPagosRepository;
	
}
