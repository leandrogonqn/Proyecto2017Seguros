package domainapp.dom.poliza;

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

import domainapp.dom.cliente.Clientes;
import domainapp.dom.compania.Companias;
import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.estado.Estado;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotores;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotoresRepository;
import domainapp.dom.tiposDeCoberturas.TiposDeCoberturas;
import domainapp.dom.vehiculo.Vehiculos;


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
public abstract class Polizas{
    
	public static final int NAME_LENGTH = 200;
	
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
	private Clientes polizaCliente;

	public Clientes getPolizaCliente() {
		return polizaCliente;
	}

	public void setPolizasCliente(Clientes polizaCliente) {
		this.polizaCliente = polizaCliente;
	}
	
	//Companias
	@Column(allowsNull = "false", name="companiaId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Compañia")
	private Companias polizaCompania;

	public Companias getPolizaCompania() {
		return polizaCompania;
	}

	public void setPolizasCompania(Companias polizaCompania) {
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
	
	//Fecha Vencimiento Pago
	@Column
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Fecha Vencimiento del Pago")
	private Date polizaFechaVencimientoPago;

	public Date getPolizaFechaVencimientoPago() {
		return polizaFechaVencimientoPago;
	}

	public void setPolizaFechaVencimientoPago(Date polizaFechaVencimientoPago) {
		this.polizaFechaVencimientoPago = polizaFechaVencimientoPago;
	}
	
	//Pago
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Pago")
	private DetalleTipoPagos polizaPago;

	public DetalleTipoPagos getPolizaPago() {
		return polizaPago;
	}

	public void setPolizaPago(DetalleTipoPagos polizaPago) {
		this.polizaPago = polizaPago;
	}
	
	//AlertaVencimientoPago
	@Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Alerta Vencimiento del Pago")
	private boolean polizaAlertaVencimientoPago;

	public boolean getPolizaAlertaVencimientoPago() {
		return polizaAlertaVencimientoPago;
	}

	public void setPolizaAlertaVencimientoPago(boolean polizaAlertaVencimientoPago) {
		this.polizaAlertaVencimientoPago = polizaAlertaVencimientoPago;
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
	protected Polizas polizaRenovacion; 
	
	public Polizas getPolizaRenovacion() {
		return polizaRenovacion;
	}

	public void setPolizaRenovacion(Polizas polizaRenovacion) {
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
	public Polizas actualizarPoliza(){
		polizaEstado.actualizarEstado(this);
		return this;
	}
	
	@Action(
			invokeOn=InvokeOn.OBJECT_ONLY
			)
	@ActionLayout(named="Anular Poliza")
	public Polizas anulacion(){
		polizaEstado.anulacion(this);
		return this;
	}
	
	@Inject 
	PolizasRepository polizasRepository;
	
    @Inject
    RiesgoAutomotoresRepository riesgoAutomotoresRepository;
	
}