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
package com.pacinetes.dom.vehiculo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import com.pacinetes.dom.modelo.Modelo;
import com.pacinetes.dom.modelo.ModeloRepository;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Vehiculos"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="vehiculoId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorDominio", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.Vehiculos "
                        + "WHERE vehiculoDominio.toLowerCase().indexOf(:vehiculoDominio) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.Vehiculos "
                        + "WHERE vehiculoActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.Vehiculos "
                        + "WHERE vehiculoActivo == false ") 
})
@javax.jdo.annotations.Unique(name="Vehiculos_vehiculoDominio_UNQ", members = {"vehiculoDominio"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Vehiculo implements Comparable<Vehiculo> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Vechiculo: {vehiculoDominio}", "vehiculoDominio", this.getVehiculoDominio()+ " " + this.getVehiculoModelo().getModeloMarcas().getMarcasNombre() + " " + this.getVehiculoModelo().getModeloNombre() + " " + this.getVehiculoAnio());
    }
    //endregion

    public String cssClass(){
    	return (getVehiculoActivo()==true)? "activo":"inactivo";
    }
    
    public static final int NAME_LENGTH = 200;
    // Constructor
    public Vehiculo(String vehiculoDominio, int vehiculoAnio, String vehiculoNumeroMotor, String vehiculoNumeroChasis,Modelo vehiculoModelo) {
		super();
		this.vehiculoDominio = vehiculoDominio;
		this.vehiculoAnio = vehiculoAnio;
		this.vehiculoNumeroMotor = vehiculoNumeroMotor;
		this.vehiculoNumeroChasis = vehiculoNumeroChasis;
		setVehiculoModelo(vehiculoModelo);
		this.vehiculoActivo = true;
	}


    @javax.jdo.annotations.Column(allowsNull = "false", name="modeloId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Modelo")
    private Modelo vehiculoModelo;

	public Modelo getVehiculoModelo() {
		return vehiculoModelo;
	}
	public void setVehiculoModelo(Modelo vehiculoModelo) {
		this.vehiculoModelo = vehiculoModelo;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Dominio")
	private String vehiculoDominio;
	
    public String getVehiculoDominio() {
        return vehiculoDominio;
    }
    
    public void setVehiculoDominio(final String vehiculoDominio) {
        this.vehiculoDominio = vehiculoDominio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Año")
	private int vehiculoAnio;
	
    public int getVehiculoAnio() {
        return vehiculoAnio;
    }
    
    public void setVehiculoAnio(final int vehiculoAnio) {
        this.vehiculoAnio = vehiculoAnio;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Numero de Motor")
	private String vehiculoNumeroMotor;
	
    public String getVehiculoNumeroMotor() {
        return vehiculoNumeroMotor;
    }
    
    public void setVehiculoNumeroMotor(final String vehiculoNumeroMotor) {
        this.vehiculoNumeroMotor = vehiculoNumeroMotor;
    }
    
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Numero de Chasis")
	private String vehiculoNumeroChasis;
	
    public String getVehiculoNumeroChasis() {
        return vehiculoNumeroChasis;
    }
    
    public void setVehiculoNumeroChasis(final String vehiculoNumeroChasis) {
        this.vehiculoNumeroChasis = vehiculoNumeroChasis;
    }	
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
    private boolean vehiculoActivo;

    public boolean getVehiculoActivo() {
		return vehiculoActivo;
	}

    public void setVehiculoActivo(boolean vehiculoActivo) {
		this.vehiculoActivo = vehiculoActivo;
	}	
	
    //endregion


    //region > delete (action)
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarVehiculo() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setVehiculoActivo(false);
    }
    
    public Vehiculo actualizarModelo(@ParameterLayout(named="Modelo") final Modelo nombreModelo) {
        setVehiculoModelo(nombreModelo);
        return this;
    }
    
    public List<Modelo> choices0ActualizarModelo(){
    	return modeloRepository.listarActivos();
    }
      
    public Modelo default0ActualizarModelo() {
    	return getVehiculoModelo();
    }
    
	public Vehiculo actualizarDominio(@ParameterLayout(named="Dominio") final String vehiculoDominio){
		setVehiculoDominio(vehiculoDominio);
		return this;
	}
	
	public String default0ActualizarDominio(){
		return getVehiculoDominio();
	}
	
	public Vehiculo actualizarAnio(@ParameterLayout(named="Año") final int modeloAnio){
		setVehiculoAnio(modeloAnio);
		return this;
	}

	public int default0ActualizarAnio(){
		return getVehiculoAnio();
	}	
	
    public Collection<Integer> choices0ActualizarAnio(){
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	Calendar hoy= Calendar.getInstance(); 
    	int año= hoy.get(Calendar.YEAR); 
    	for (int i = 1980; i <= año; i++)
    	{
    	   numbers.add(i);
    	}
    	return numbers;
    }
	
	public Vehiculo actualizarNumeroMotor(@ParameterLayout(named="Numero de Motor") final String vehiculoNumeroMotor){
		setVehiculoNumeroMotor(vehiculoNumeroMotor);
		return this;
	}
	
	public String default0ActualizarNumeroMotor(){
		return getVehiculoNumeroMotor();
	}
	
	public Vehiculo actualizarNumeroChasis(@ParameterLayout(named="Numero de Chasis") final String vehiculoNumeroChasis){
		setVehiculoNumeroChasis(vehiculoNumeroChasis);
		return this;
	}
	
	public String default0ActualizarNumeroChasis(){
		return getVehiculoNumeroChasis();
	}
	
	public Vehiculo actualizarActivo(@ParameterLayout(named="Activo") final boolean vehiculoActivo){
		setVehiculoActivo(vehiculoActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getVehiculoActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return getVehiculoDominio();
    }
    @Override
    public int compareTo(final Vehiculo other) {
        return this.vehiculoDominio.compareTo(other.vehiculoDominio);
    }

    //endregion
    
    //acciones
  	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
    @ActionLayout(named="Listar Vehiculos")
    @MemberOrder(sequence = "2")
    public List<Vehiculo> listar() {
        return vehiculosRepository.listar();
    }
    
    @Action(invokeOn=InvokeOn.OBJECT_ONLY)
    @ActionLayout(named="Listar Vehiculos Activos")
    @MemberOrder(sequence = "3")
    public List<Vehiculo> listarActivos() {
        return vehiculosRepository.listarActivos();
    }
    
    @Action(invokeOn=InvokeOn.OBJECT_ONLY)
    @ActionLayout(named="Listar Vehiculos Inactivos")
    @MemberOrder(sequence = "4")
    public List<Vehiculo> listarInactivos() {
        return vehiculosRepository.listarInactivos();
    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    @javax.inject.Inject
    ModeloRepository modeloRepository;

    @Inject
    VehiculoRepository vehiculosRepository;
    //endregion
}
