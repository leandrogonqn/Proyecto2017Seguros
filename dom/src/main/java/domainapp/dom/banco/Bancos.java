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
package domainapp.dom.banco;

import java.util.List;

import javax.inject.Inject;
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
         column="bancoId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.banco "
                        + "WHERE bancoNombre.toLowerCase().indexOf(:bancoNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.banco "
                        + "WHERE bancoActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.banco "
                        + "WHERE bancoActivo == false ") 
})
@javax.jdo.annotations.Unique(name="banco_bancoNombre_UNQ", members = {"bancoNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Bancos implements Comparable<Bancos> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getBancoNombre());
    }
    //endregion

    public String cssClass(){
    	return (getBancoActivo()==true)? "activo":"inactivo";
    }
    
    //region > constructor
    public Bancos(final String bancoNombre) {
    	setBancoNombre(bancoNombre);
    	this.bancoActivo = true;	
    }
    //endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String bancoNombre;

    public String getBancoNombre() {
		return bancoNombre;
	}

	public void setBancoNombre(String bancoNombre) {
		this.bancoNombre = bancoNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean bancoActivo;

	public boolean getBancoActivo() {
		return bancoActivo;
	}

	public void setBancoActivo(boolean bancoActivo) {
		this.bancoActivo = bancoActivo;
	}

    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Bancos> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarBanco() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setBancoActivo(false);
    }
    
	public Bancos actualizarNombre(@ParameterLayout(named="Nombre") final String bancoNombre){
		setBancoNombre(bancoNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getBancoNombre();
	}
	
	public Bancos actualizarActivo(@ParameterLayout(named="Activo") final boolean bancoActivo){
		setBancoActivo(bancoActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getBancoActivo();
	}


    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "bancoNombre");
    }
    @Override
    public int compareTo(final Bancos other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Todos los Bancos")
    @MemberOrder(sequence = "2")
    public List<Bancos> listar() {
        return bancoRepository.listar();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Bancos Activos")
    @MemberOrder(sequence = "3")
    public List<Bancos> listarActivos() {
        return bancoRepository.listarActivos();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Bancos Inactivos")
    @MemberOrder(sequence = "4")
    public List<Bancos> listarInactivos() {
        return bancoRepository.listarInactivos();
    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    BancosRepository bancoRepository;

    //endregion

}
