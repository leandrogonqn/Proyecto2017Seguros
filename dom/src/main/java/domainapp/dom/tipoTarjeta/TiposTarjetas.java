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
package domainapp.dom.tipoTarjeta;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
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
         column="tipoTarjetaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTarjeta "
                        + "WHERE tipoTarjetaNombre.toLowerCase().indexOf(:tipoTarjetaNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTarjeta "
                        + "WHERE tipoTarjetaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.tipoTarjeta "
                        + "WHERE tipoTarjetaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="tipoTarjeta_tipoTarjetaNombre_UNQ", members = {"tipoTarjetaNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class TiposTarjetas implements Comparable<TiposTarjetas> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getTipoTarjetaNombre());
    }
    //endregion

    public String cssClass(){
    	return (getTipoTarjetaActivo()==true)? "activo":"inactivo";
    }
    
    //region > constructor
    public TiposTarjetas(final String tipoTarjetaNombre) {
    	setTipoTarjetaNombre(tipoTarjetaNombre);
    	this.tipoTarjetaActivo = true;	
    }
    //endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String tipoTarjetaNombre;

    public String getTipoTarjetaNombre() {
		return tipoTarjetaNombre;
	}

	public void setTipoTarjetaNombre(String tipoTarjetaNombre) {
		this.tipoTarjetaNombre = tipoTarjetaNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean tipoTarjetaActivo;

	public boolean getTipoTarjetaActivo() {
		return tipoTarjetaActivo;
	}

	public void setTipoTarjetaActivo(boolean tipoTarjetaActivo) {
		this.tipoTarjetaActivo = tipoTarjetaActivo;
	}

    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TiposTarjetas> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarTipoTarjeta() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoTarjetaActivo(false);
    }
    
	public TiposTarjetas actualizarNombre(@ParameterLayout(named="Nombre") final String tipoTarjetaNombre){
		setTipoTarjetaNombre(tipoTarjetaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getTipoTarjetaNombre();
	}
	
	public TiposTarjetas actualizarActivo(@ParameterLayout(named="Activo") final boolean tipoTarjetaActivo){
		setTipoTarjetaActivo(tipoTarjetaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getTipoTarjetaActivo();
	}


    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tipoTarjetaNombre");
    }
    @Override
    public int compareTo(final TiposTarjetas other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    //endregion

}
