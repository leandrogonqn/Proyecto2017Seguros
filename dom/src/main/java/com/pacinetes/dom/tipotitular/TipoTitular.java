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
package com.pacinetes.dom.tipotitular;

import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "TipoTitulares")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "tipoTitularId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.tipoTitulares "
				+ "WHERE tipoTitularNombre.toLowerCase().indexOf(:tipoTitularNombre) >= 0 "),
		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.tipoTitulares " + "WHERE tipoTitularActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.tipoTitulares " + "WHERE tipoTitularActivo == false ") })
@javax.jdo.annotations.Unique(name = "tipoTitulares_tipoTitularNombre_UNQ", members = { "tipoTitularNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class TipoTitular implements Comparable<TipoTitular> {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Tipo de titular: {name}", "name", getTipoTitularNombre());
	}
	// endregion

	// region > constructor
	public TipoTitular(final String tipoTitularNombre) {
		setTipoTitularNombre(tipoTitularNombre);
		setTipoTitularActivo(true);
	}
	// endregion

	public String cssClass() {
		return (isTipoTitularActivo() == true) ? "activo" : "inactivo";
	}

	// region > name (read-only property)
	public static final int NAME_LENGTH = 40;

	@Column(allowsNull = "true", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED, optionality = Optionality.OPTIONAL)
	@PropertyLayout(named = "Nombre")
	private String tipoTitularNombre;

	public String getTipoTitularNombre() {
		return tipoTitularNombre;
	}

	public void setTipoTitularNombre(String tipoTitularNombre) {
		this.tipoTitularNombre = tipoTitularNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean tipoTitularActivo;

	public boolean isTipoTitularActivo() {
		return tipoTitularActivo;
	}

	public void setTipoTitularActivo(boolean tipoTitularActivo) {
		this.tipoTitularActivo = tipoTitularActivo;
	}
	// endregion

	// region > delete (action)

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarTipoTitular() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setTipoTitularActivo(false);
	}

	public TipoTitular actualizarNombre(@ParameterLayout(named = "Nombre") final String tipoTitularNombre) {
		setTipoTitularNombre(tipoTitularNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getTipoTitularNombre();
	}

	public TipoTitular actualizarActivo(@ParameterLayout(named = "Activo") final boolean tipoTitularActivo) {
		setTipoTitularActivo(tipoTitularActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return isTipoTitularActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getTipoTitularNombre();
	}

	@Override
	public int compareTo(final TipoTitular other) {
		return this.tipoTitularNombre.compareTo(other.tipoTitularNombre);
	}

	// endregion

	// acciones
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Tipos de Titulares")
	@MemberOrder(sequence = "2")
	public List<TipoTitular> listar() {
		return tipoTitularesRepository.listar();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Tipos de Titulares Activos")
	@MemberOrder(sequence = "3")
	public List<TipoTitular> listarActivos() {
		return tipoTitularesRepository.listarActivos();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Tipos de Titulares Inactivos")
	@MemberOrder(sequence = "4")
	public List<TipoTitular> listarInactivos() {
		return tipoTitularesRepository.listarInactivos();
	}

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	TipoTitularRepository tipoTitularesRepository;

	// endregion
}
