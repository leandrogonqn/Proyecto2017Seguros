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
package com.pacinetes.dom.tipovivienda;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
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
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "TiposVivienda"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tipoViviendaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.tipoVivienda "
                        + "WHERE tipoViviendaNombre.toLowerCase().indexOf(:tipoViviendaNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.tipoVivienda "
                        + "WHERE tipoViviendaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.tipoVivienda "
                        + "WHERE tipoViviendaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="tiposVivienda_tipoViviendaNombre_UNQ", members = {"tipoViviendaNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class TipoVivienda implements Comparable<TipoVivienda>{

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo: {name}", "name", getTipoViviendaNombre());
    }
    //endregion

    
    //region > constructor
    public TipoVivienda(final String tipoViviendaNombre) {
    	setTipoViviendaNombre(tipoViviendaNombre);
    	setTipoViviendaActivo(true);
    }
    //endregion

	//region > name (read-only property)
    public static final int NAME_LENGTH = 40;
    
    public String cssClass(){
    	return (isTipoViviendaActivo()==true)? "activo":"inactivo";
    }

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named="Nombre")
    private String tipoViviendaNombre;
	
	public void setTipoViviendaNombre(String tipoViviendaNombre) {
		this.tipoViviendaNombre = tipoViviendaNombre;
	}

    public String getTipoViviendaNombre() {
		return tipoViviendaNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named="Activo")
	private boolean tipoViviendaActivo;
	
	public boolean isTipoViviendaActivo(){
		return tipoViviendaActivo;
	}


	public void setTipoViviendaActivo(boolean tipoViviendaActivo) {
		this.tipoViviendaActivo = tipoViviendaActivo;
	}
    //endregion

	//region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TipoVivienda> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    
    public void borrarTipoVivienda() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoViviendaActivo(false);
    }
    
	public TipoVivienda actualizarNombre(@ParameterLayout(named="Nombre") final String tipoViviendaNombre){
		setTipoViviendaNombre(tipoViviendaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getTipoViviendaNombre();
	}
	
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tipoViviendaNombre");
    }
    @Override
    public int compareTo(final TipoVivienda other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Viviendas")
    @MemberOrder(sequence = "2")
    public List<TipoVivienda> listar() {
        return tiposViviendaRepository.listar();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Viviendas Activos")
    @MemberOrder(sequence = "3")
    public List<TipoVivienda> listarActivos() {
        return tiposViviendaRepository.listarActivos();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Tipos de Viviendas Inactivos")
    @MemberOrder(sequence = "4")
    public List<TipoVivienda> listarInactivos() {
        return tiposViviendaRepository.listarInactivos();
    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    TipoViviendaRepository tiposViviendaRepository;

    //endregion
}
