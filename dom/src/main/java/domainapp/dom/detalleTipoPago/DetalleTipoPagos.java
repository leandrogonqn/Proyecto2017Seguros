package domainapp.dom.detalleTipoPago;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

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
            name = "listarActivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPagos "
                    + "WHERE tipoPagoActivo == true ")
})
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP, column="tipoDePagoNombre")
public abstract class DetalleTipoPagos {
	
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
    
}
