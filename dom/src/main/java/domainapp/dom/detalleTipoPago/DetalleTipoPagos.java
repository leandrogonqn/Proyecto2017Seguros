package domainapp.dom.detalleTipoPago;



import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "DetalleTipoPagos"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="detalleTipoPagoId")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public abstract class DetalleTipoPagos {
	
	public static final int NAME_LENGTH = 50;
	
	@Column(allowsNull="false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Importe")
    protected float tipoPagoImporte;
	
    public float getTipoPagoImporte() {
    	return tipoPagoImporte;
    	}
    
    public void setTipoPagoImporte(final float tipoPagoImporte) {
    	this.tipoPagoImporte = tipoPagoImporte;
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
    

}
