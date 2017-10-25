/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.pacinetes.dom.poliza;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;

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
import org.apache.isis.applib.annotation.Where;

import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.polizaautomotor.PolizaAutomotor;
import com.pacinetes.dom.polizaautomotor.PolizaAutomotoresRepository;
import com.pacinetes.dom.siniestro.Siniestro;
import com.pacinetes.dom.siniestro.SiniestroRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCobertura;
import com.pacinetes.dom.vehiculo.Vehiculo;


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
                        + "WHERE polizaCliente == :polizaCliente"),
        @javax.jdo.annotations.Query(
                name = "buscarPorCompania", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Polizas "
                        + "WHERE polizaCompania == :polizaCompania")
})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP, column="Polizas")
public abstract class Poliza implements Comparable<Poliza>{
    
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
	private Persona polizaCliente;

	public Persona getPolizaCliente() {
		return polizaCliente;
	}

	public void setPolizasCliente(Persona polizaCliente) {
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
	
	//Siniestro
	@Column(allowsNull = "true")
    @Property(
            editing = Editing.DISABLED
    )
	@Join  
	@PropertyLayout(named="Siniestros")
	protected List<Siniestro> polizaSiniestro; 
	
	public List<Siniestro> getPolizaSiniestro() {
		return polizaSiniestro;
	}

	public void setPolizaSiniestro(List<Siniestro> polizaSiniestro) {
		this.polizaSiniestro = polizaSiniestro;
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

	@ActionLayout(named="Actualizar Estado de las Poliza",hidden=Where.EVERYWHERE)
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
    
    @Override
    public int compareTo(Poliza o) {
        if (polizaFechaVencimiento.before(o.polizaFechaVencimiento) ) {
            return -1;
        }
        if (polizaFechaVencimiento.after(o.polizaFechaVencimiento)) {
            return 1;
        }	
        return 0;
    }
    
	@ActionLayout(named="Agregar Siniestro")
    public Poliza agregarSiniestro(
    	@ParameterLayout(named = "Descripcion") final String siniestroDescripcion,
    	@ParameterLayout(named = "Fecha del Siniestro") final Date siniestroFecha) {
    		this.getPolizaSiniestro().add(siniestroRepository.crear(siniestroDescripcion, this, siniestroFecha));
    		this.setPolizaSiniestro(this.getPolizaSiniestro());
    		return this;
	}
	
    public Poliza quitarSiniestro(@ParameterLayout(named="Siniestro") Siniestro siniestro) {
    	Iterator<Siniestro> it = getPolizaSiniestro().iterator();
    	while (it.hasNext()) {
    		Siniestro lista = it.next();
    		if (lista.equals(siniestro))
    			it.remove();
    	}
    	return this;
    }
    
    public List<Siniestro> choices0QuitarSiniestro(){
    	return getPolizaSiniestro();
    }

    @ActionLayout(hidden=Where.EVERYWHERE)
	public int cantidadDeSiniestrosPorCliente(Persona persona) {
		// TODO Auto-generated method stub
		
		int cant = 0;
		if (this.getPolizaCliente() == persona){
			cant = this.getPolizaSiniestro().size();
		}
		
		return cant;
	}
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	public long contarCantidadDiasHastaVencimiento(Compania compania) {
		// TODO Auto-generated method stub
		long cant = 0;
		Calendar a= Calendar.getInstance();
		Date hoy = a.getTime();
		
		if (this.getPolizaCompania()== compania){
			cant = getDifferenceDays(hoy, this.getPolizaFechaVencimiento());
		}
		return cant;
	}
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	public long contarCantidadDiasHastaVencimiento() {
		long cant = 0;
		Calendar a= Calendar.getInstance();
		Date hoy = a.getTime();
		
		cant = getDifferenceDays(hoy, this.getPolizaFechaVencimiento());
		
		return cant;
	}
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	private long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
    
    @Inject 
	PolizaRepository polizasRepository;
	
	@Inject
	SiniestroRepository siniestroRepository;
}