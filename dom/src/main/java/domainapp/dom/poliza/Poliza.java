package domainapp.dom.poliza;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.estado.Estado;
import domainapp.dom.polizaAutomotor.PolizaAutomotor;
import domainapp.dom.polizaAutomotor.PolizaAutomotoresRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Polizas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="polizaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNumeroPoliza", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Polizas "
                        + "WHERE polizaNumero.toLowerCase().indexOf(:polizaNumero) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarPorEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Polizas "
                        + "WHERE polizaEstado == :polizaEstado"),
        @javax.jdo.annotations.Query(
                name = "buscarPorCliente", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Polizas "
                        + "WHERE polizaCliente == :polizaCliente")
})
@javax.jdo.annotations.Unique(name="Polizas_polizaNumero_UNQ", members = {"polizaNumero"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP, column="Polizas")
public abstract class Poliza{
    
	public static final int NAME_LENGTH = 200;
	
    public String cssClass(){
    	
		String a = null;
		if (this.getPolizaEstado()==Estado.vigente) {
			if (this.getPolizaRenovacion() == null) {
				Calendar hoyMasTreintaDias = Calendar.getInstance();
				hoyMasTreintaDias.add(hoyMasTreintaDias.DATE, 30);
				Date hoyMasTreintaDiasDate = hoyMasTreintaDias.getTime();

				if (hoyMasTreintaDiasDate.before(this.getPolizaFechaVencimiento())) {
					a = "vigenteSinRenovacionConVencimientoMayor30Dias";
				} else {
					Calendar hoyMasQuinceDias = Calendar.getInstance();
					hoyMasQuinceDias.add(hoyMasQuinceDias.DATE, 15);
					Date hoyMasQuinceDiasDate = hoyMasQuinceDias.getTime();
					if (hoyMasQuinceDiasDate.before(this.getPolizaFechaVencimiento())) {
						a = "vigenteSinRenovacionConVencimientoMenor30Dias";
					} else {
						a = "vigenteSinRenovacionConVencimientoMenor15Dias";
					}
				}
			} else {
				a = "vigenteConRenovacion";
			}
		}
		else {
			a = getPolizaEstado().toString();  
		}
    	
		return a;
    }
	
	//Poliza Numero
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Numero de Poliza")
	private String polizaNumero;
	
    public String getPolizaNumero() {
		return polizaNumero;
	}

	public void setPolizaNumero(String polizaNumero) {
		this.polizaNumero = polizaNumero;
	}
	
	//Clientes
	@Column(allowsNull = "false", name="clienteId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Cliente")
	private Cliente polizaCliente;

	public Cliente getPolizaCliente() {
		return polizaCliente;
	}

	public void setPolizasCliente(Cliente polizaCliente) {
		this.polizaCliente = polizaCliente;
	}
	
	//Companias
	@Column(allowsNull = "false", name="companiaId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Compañia")
	private Compania polizaCompania;

	public Compania getPolizaCompania() {
		return polizaCompania;
	}

	public void setPolizasCompania(Compania polizaCompania) {
		this.polizaCompania = polizaCompania;
	}

	//Fecha Emision
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Fecha de Emision")
	private Date polizaFechaEmision;
	
	public Date getPolizaFechaEmision() {
		return polizaFechaEmision;
	}

	public void setPolizaFechaEmision(Date polizaFechaEmision) {
		this.polizaFechaEmision = polizaFechaEmision;
	}

	//Fecha Vigencia
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Fecha de Vigencia")
	private Date polizaFechaVigencia;
	
	public Date getPolizaFechaVigencia() {
		return polizaFechaVigencia;
	}

	public void setPolizaFechaVigencia(Date polizaFechaVigencia) {
		this.polizaFechaVigencia = polizaFechaVigencia;
	}
	
	//Fecha Vencimiento
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Fecha de Vencimiento")
	private Date polizaFechaVencimiento;
	
	public Date getPolizaFechaVencimiento() {
		return polizaFechaVencimiento;
	}

	public void setPolizaFechaVencimiento(Date polizaFechaVencimiento) {
		this.polizaFechaVencimiento = polizaFechaVencimiento;
	}
	
	//TipoPago
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Tipo de Pago")
	private TipoPago polizaTipoDePago;

	public TipoPago getPolizaTipoDePago() {
		return polizaTipoDePago;
	}

	public void setPolizaTipoDePago(TipoPago polizaTipoDePago) {
		this.polizaTipoDePago = polizaTipoDePago;
	}
	
	//Detalle de Pago
	@Column(allowsNull = "true")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Detalle Pago")
	private DetalleTipoPago polizaPago;

	public DetalleTipoPago getPolizaPago() {
		return polizaPago;
	}

	public void setPolizaPago(DetalleTipoPago polizaPago) {
		this.polizaPago = polizaPago;
	}
	
	//Fecha Baja
	@Column
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Fecha de Baja")
	private Date polizaFechaBaja;
	
	public Date getPolizaFechaBaja() {
		return polizaFechaBaja;
	}

	public void setPolizaFechaBaja(Date polizaFechaBaja) {
		this.polizaFechaBaja = polizaFechaBaja;
	}

	//Motivo Baja
	@Column(length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Motivo de la Baja")
	private String polizaMotivoBaja;
	
	public String getPolizaMotivoBaja() {
		return polizaMotivoBaja;
	}

	public void setPolizaMotivoBaja(String polizaMotivoBaja) {
		this.polizaMotivoBaja = polizaMotivoBaja;
	}

	//Importe
	@Column
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Importe Total")
	private double polizaImporteTotal; 
	
	public double getPolizaImporteTotal() {
		return polizaImporteTotal;
	}

	public void setPolizaImporteTotal(double polizaImporteTotal) {
		this.polizaImporteTotal = polizaImporteTotal;
	}
	
	//Renovacion
	@Column(allowsNull = "true")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Renovacion")
	protected Poliza polizaRenovacion; 
	
	public Poliza getPolizaRenovacion() {
		return polizaRenovacion;
	}

	public void setPolizaRenovacion(Poliza polizaRenovacion) {
		this.polizaRenovacion = polizaRenovacion;
	}	
	
	//Estado
	@Column
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Estado")
	protected Estado polizaEstado; 
	
	public Estado getPolizaEstado() {
		return polizaEstado;
	}

	public void setPolizaEstado(Estado polizaEstado) {
		this.polizaEstado = polizaEstado;
	}	
	
    //endregion

	//acciones
	
	@Action(
			invokeOn=InvokeOn.OBJECT_AND_COLLECTION
			)
	@ActionLayout(named="Actualizar Estado de las Polizas")
	public Poliza actualizarPoliza(){
		polizaEstado.actualizarEstado(this);
		return this;
	}
	
	@Action(
			invokeOn=InvokeOn.OBJECT_ONLY
			)
	@ActionLayout(named="Anular Poliza")
	public Poliza anulacion(
			@ParameterLayout(named="Fecha de la Baja") final Date polizaFechaBaja,
			@ParameterLayout(named="Motivo de la Baja") final String polizaMotivoBaja){
		polizaEstado.anulacion(this, polizaFechaBaja, polizaMotivoBaja);
		return this;
	}
	
    public Date default0Anulacion() {
    	Date hoy = new Date();
    	return hoy;
    }
	
	@Inject 
	PolizaRepository polizasRepository;
	
}