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
package com.pacinetes.dom.provincia;

import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
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

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Provincias")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "provinciaId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Provincias "
				+ "WHERE provinciasNombre.toLowerCase().indexOf(:provinciasNombre) >= 0 "),

		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Provincias " + "WHERE provinciaActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Provincias " + "WHERE provinciaActivo == false ") })
@javax.jdo.annotations.Unique(name = "Provincias_provinciasNombre_UNQ", members = { "provinciasNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Provincia implements Comparable<Provincia> {
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getProvinciasNombre());
	}
	// endregion

	public String cssClass() {
		return (getProvinciaActivo() == true) ? "activo" : "inactivo";
	}

	public static final int NAME_LENGTH = 200;

	// Constructor
	public Provincia(String provinciaNombre) {
		setProvinciasNombre(provinciaNombre);
		this.provinciaActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String provinciasNombre;

	public String getProvinciasNombre() {
		return provinciasNombre;
	}

	public void setProvinciasNombre(String provinciasNombre) {
		this.provinciasNombre = provinciasNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean provinciaActivo;

	public boolean getProvinciaActivo() {
		return provinciaActivo;
	}

	public void setProvinciaActivo(boolean provinciaActivo) {
		this.provinciaActivo = provinciaActivo;
	}

	// endregion

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarProvincia() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setProvinciaActivo(false);
	}

	public Provincia actualizarNombre(@ParameterLayout(named = "Nombre") final String provinciaNombre) {
		setProvinciasNombre(provinciaNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getProvinciasNombre();
	}

	public Provincia actualizarActivo(@ParameterLayout(named = "Activo") final boolean provinciaActivo) {
		setProvinciaActivo(provinciaActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return getProvinciaActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getProvinciasNombre();
	}

	@Override
	public int compareTo(final Provincia provincia) {
		return this.provinciasNombre.compareTo(provincia.provinciasNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las Provincias")
	@MemberOrder(sequence = "2")
	public List<Provincia> listar() {
		return provinciasRepository.listar();
	}

	@ActionLayout(named = "Listar Provincia Activas")
	@MemberOrder(sequence = "3")
	public List<Provincia> listarActivos() {
		return provinciasRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Provincias Inactivas")
	@MemberOrder(sequence = "4")
	public List<Provincia> listarInactivos() {
		return provinciasRepository.listarInactivos();
	}

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	ProvinciaRepository provinciasRepository;

	// endregion
}
