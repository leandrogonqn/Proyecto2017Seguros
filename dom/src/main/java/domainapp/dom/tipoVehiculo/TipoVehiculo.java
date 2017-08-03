/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.tipoVehiculo;

import java.util.List;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.marca.Marcas;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tipoVehiculoId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoVehiculo "
                        + "WHERE tipoVehiculoNombre.toLowerCase().indexOf(:tipoVehiculoNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoVehiculo "
                        + "WHERE tipoVehiculoActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoVehiculo "
                        + "WHERE tipoVehiculoActivo == false ") 
})
@javax.jdo.annotations.Unique(name="tipoVehiculoject_tipoVehiculoNombre_UNQ", members = {"tipoVehiculoNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class TipoVehiculo implements Comparable<TipoVehiculo> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo: {name}", "name", getTipoVehiculoNombre());
    }
    //endregion

    public String cssClass(){
    	return (getTipoVehiculoActivo()==true)? "activo":"inactivo";
    }
    
    //region > constructor
    public TipoVehiculo(final String tipoVehiculoNombre) {
    	setTipoVehiculoNombre(tipoVehiculoNombre);
    	this.tipoVehiculoActivo = true;	
    }
    //endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String tipoVehiculoNombre;

    public String getTipoVehiculoNombre() {
		return tipoVehiculoNombre;
	}

	public void setTipoVehiculoNombre(String tipoVehiculoNombre) {
		this.tipoVehiculoNombre = tipoVehiculoNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean tipoVehiculoActivo;

	public boolean getTipoVehiculoActivo() {
		return tipoVehiculoActivo;
	}

	public void setTipoVehiculoActivo(boolean tipoVehiculoActivo) {
		this.tipoVehiculoActivo = tipoVehiculoActivo;
	}

    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TipoVehiculo> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarTipoVehiculo() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoVehiculoActivo(false);
    }
    
	public TipoVehiculo actualizarNombre(@ParameterLayout(named="Nombre") final String tipoVehiculoNombre){
		setTipoVehiculoNombre(tipoVehiculoNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getTipoVehiculoNombre();
	}
	
	public TipoVehiculo actualizarActivo(@ParameterLayout(named="Activo") final boolean tipoVehiculoActivo){
		setTipoVehiculoActivo(tipoVehiculoActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getTipoVehiculoActivo();
	}


    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tipoVehiculoNombre");
    }
    @Override
    public int compareTo(final TipoVehiculo other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Vehiculos")
    @MemberOrder(sequence = "2")
    public List<TipoVehiculo> listar() {
        return tipoVehiculoRepository.listar();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Vehiculos Activos")
    @MemberOrder(sequence = "3")
    public List<TipoVehiculo> listarActivos() {
        return tipoVehiculoRepository.listarActivos();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Vehiculos Activos")
    @MemberOrder(sequence = "4")
    public List<TipoVehiculo> listarInactivos() {
        return tipoVehiculoRepository.listarInactivos();
    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    TipoVehiculoRepository tipoVehiculoRepository;

    //endregion

}
